package pe.devpicon.android.agendatechlatam.view.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventView

/**
 * Created by armando on 3/17/17.
 */
class NewEventPresenterImpl : NewEventPresenter {
    override fun onInitCountrySpinner() {

        var countryArray = mutableListOf<String>()

        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.getReference("countries");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    snapshot.children.forEach {
                        Log.d(javaClass.simpleName, it.key)
                        val value = it.getValue(String::class.java)
                        value?.let { countryArray.add(it.toString()) }
                    }

                }

                Log.d(javaClass.simpleName, "${countryArray.size}")

                newEventView.showCountries(countryArray)

            }

        })

    }

    override fun onInitEventTypeSpinner() {

        var eventTypes = mutableListOf<String>()

        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.getReference("eventTypes");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    snapshot.children.forEach {
                        Log.d(javaClass.simpleName, it.key)
                        val value = it.getValue(String::class.java)
                        value?.let { eventTypes.add(it.toString()) }
                    }

                }

                Log.d(javaClass.simpleName, "${eventTypes.size}")

                newEventView.showEventTypes(eventTypes)


            }

        })
    }

    override fun onSaveButtonClick(model: EventModel) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val key = database.reference.child("events").push().key


        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: 0

        val eventValues = model.toMap()

        val childUpdates = mapOf<String, Any>(
                "/events/$key" to eventValues,
                "/user-events/$uid/$key" to eventValues
        )

        val updateChildren = database.reference.updateChildren(childUpdates).addOnCompleteListener {

            newEventView.goToMainActivity()
            newEventView.finish()

        }
    }

    lateinit var newEventView: NewEventView

    override fun setView(newEventView: NewEventView) {
        this.newEventView = newEventView
    }

}