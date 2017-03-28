package pe.devpicon.android.agendatechlatam.view.presenter

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.ResultCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainView
import java.text.SimpleDateFormat


/**
 * Created by armando on 3/10/17.
 */
class MainPresenterImpl : MainPresenter {

    lateinit var mainView: MainView

    fun setView(mainView: MainView) {
        this.mainView = mainView
    }

    companion object {
        val RC_SIGN_IN = 123
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == ResultCodes.OK) {
                mainView.goToNewEventActivity()
                return
            } else {

                if (response == null) {
                    mainView.showSnackbar(R.string.sign_in_cancelled)
                    return
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    mainView.showSnackbar(R.string.message_not_connected);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    mainView.showSnackbar(R.string.unknown_error);
                    return;
                }
            }
        }
    }


    override fun getEvents() {
        Log.d(javaClass.simpleName, "Entró a getEvents")


        if (isConnected()) {


            mainView.showLoading()

            var eventList = mutableListOf<Event>()


            val database: FirebaseDatabase = FirebaseDatabase.getInstance();
            val myRef: DatabaseReference = database.getReference("events");
            myRef.orderByChild("date").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot?) {
                    snapshot?.let {
                        snapshot.children.forEach {
                            Log.d(javaClass.simpleName, it.key)
                            val key = it.key
                            val value = it.getValue(EventModel::class.java)
                            value?.let { value -> eventList.add(mapIntoEvent(key, value)) }
                        }

                    }

                    Log.d(javaClass.simpleName, "${eventList.size}")

                    if (eventList.size > 0) {
                        mainView.showEvents(eventList)
                        mainView.hideEmptyListMessage()
                        mainView.hideNotConnectedMessage()
                    } else {
                        mainView.showEmptyListMessage()
                        mainView.hideEvents()
                        mainView.hideNotConnectedMessage()
                    }

                    mainView.hideLoading()

                }

            })

        } else {

            mainView.showNotConnectedMessage()
            mainView.hideEmptyListMessage()
            mainView.hideEvents()

        }


    }

    private fun mapIntoEvent(key:String, value: EventModel): Event {
        Log.d(javaClass.simpleName, "Ingreso a mapIntoEvent")
        return Event(key,
                value.name,
                value.type,
                dateFormat(value.date),
                value.city,
                value.country,
                value.imageUri,
                value.url)
    }

    private fun dateFormat(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val parsedDate = dateFormat.parse(date)
        val dateFormat2 = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat2.format(parsedDate)
    }


    override fun onFabClicked() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            showSignInActivity()
        } else {

            Crashlytics.setUserIdentifier(currentUser.uid)
            Crashlytics.setUserEmail(currentUser.email)
            Crashlytics.setUserName(currentUser.displayName)

            mainView.goToNewEventActivity()
        }
    }

    private fun showSignInActivity() {
        mainView.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setProviders(listOf(
                        AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                        AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()

                )).setIsSmartLockEnabled(false)
                .build(), RC_SIGN_IN)
    }

    private fun isConnected(): Boolean {
        val context: Context = mainView.getContext()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return (wifi.isAvailable && wifi.isConnectedOrConnecting) || (mobile.isAvailable && mobile
                .isConnectedOrConnecting)
    }

}