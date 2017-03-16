package pe.devpicon.android.agendatechlatam.view.presenter

import android.util.Log
import com.google.firebase.database.*
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainView

/**
 * Created by armando on 3/10/17.
 */
class MainPresenter {

    lateinit var mainView: MainView

    fun setView(mainView: MainView) {
        this.mainView = mainView
    }

    fun getEvents() {
        Log.d(javaClass.simpleName, "Entró a getEvents")

        mainView.showLoading()

        var eventList = mutableListOf<Event>()


        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.getReference("events");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    snapshot.children.forEach {
                        Log.d(javaClass.simpleName, it.key)
                        val value = it.getValue(EventModel::class.java)
                        value?.let { eventList.add(mapIntoEvent(it)) }
                    }

                }


                Log.d(javaClass.simpleName, "${eventList.size}")

                mainView.showEvents(eventList)
                mainView.hideLoading()

            }

        })


        /*val eventList = arrayListOf<Event>(
                Event(1, "DevFest Lima 2017", "evento", "16/09/2017", "Lima", "Peru"),
                Event(2, "Droidcon Santo Domingo 2017", "conferencia", "03/03/2017", "Santo " +
                        "Domingo",
                        "República Dominicana"),
                Event(3, "DevFest Cochabamba 2017", "evento", "16/09/2017", "Cochabamba",
                        "Bolivia"),
                Event(4, "DevFest La Paz 2017", "evento", "16/09/2017", "La Paz", "Bolivia")
        )*/



    }

    private fun mapIntoEvent(value: EventModel): Event {
        Log.d(javaClass.simpleName, "Ingreso a mapIntoEvent")
        return Event(0, value.name, value.type, value.date, value.city, value.country)
    }


}