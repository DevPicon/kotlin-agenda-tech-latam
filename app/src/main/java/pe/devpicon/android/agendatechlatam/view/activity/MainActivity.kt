package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.ResultCodes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter.OnItemClickListener
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.presenter.MainPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainView

class MainActivity : AppCompatActivity(), MainView {

    //var adapter: EventAdapter? = null
    val adapter: EventAdapter by lazy { initializeAdapter() }
    var presenter: MainPresenter? = null


    override fun showLoading() {
        progress_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_loading.visibility = View.GONE
    }

    override fun showEmptyListMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEvents(eventList: List<Event>) {
        Log.d(javaClass.simpleName, "Ingreso a showEvents")
        adapter.items = eventList
        adapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAdapter()
        initializeList()
        initializePresenter()
        initializeFab()
        presenter?.getEvents()

    }

    fun initializeFab() {

        fab_new_event.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            if (auth.currentUser == null) {

                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(listOf(
                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()

                        ))
                        .build(), NewEventActivity.RC_SIGN_IN)

            } else {

                goToNewEventActivity()

            }
        }


    }

    private fun goToNewEventActivity() {
        var intent = Intent(this@MainActivity, NewEventActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NewEventActivity.RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == ResultCodes.OK) {
                goToNewEventActivity()
                finish()
                return
            } else {

                if (response == null) {
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }
        }
    }

    private fun showSnackbar(messageId: Int) {
        val snackbar = Snackbar.make(main_coordinator_layout, getString(messageId), Snackbar
                .LENGTH_SHORT)
        snackbar.show()

    }


    private fun initializePresenter() {
        presenter = MainPresenter()
        presenter?.setView(this)
    }

    private fun initializeList() {
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    /* private fun initializeAdapter() {
        adapter = EventAdapter()
    }*/

    private fun initializeAdapter() = EventAdapter(itemClick = object : OnItemClickListener {
        override fun invoke(event: Event) {

            var intent = Intent(this@MainActivity, EventDetailActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)

        }

    })
}
