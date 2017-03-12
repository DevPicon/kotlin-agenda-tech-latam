package pe.devpicon.android.agendatechlatam.view.presenter

import pe.devpicon.android.agendatechlatam.view.model.Event
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

        mainView.showLoading()
        val eventList = arrayListOf<Event>(
                Event(1, "DevFest Lima 2017", "evento", "16/09/2017", "Lima", "Peru"),
                Event(2, "Droidcon Santo Domingo 2017", "conferencia", "03/03/2017", "Santo " +
                        "Domingo",
                        "República Dominicana"),
                Event(3, "DevFest Cochabamba 2017", "evento", "16/09/2017", "Cochabamba",
                        "Bolivia"),
                Event(4, "DevFest La Paz 2017", "evento", "16/09/2017", "La Paz", "Bolivia")
        )

        mainView.showEvents(eventList)
        mainView.hideLoading()


    }

}