package pe.devpicon.android.agendatechlatam.view.viewmvp

import pe.devpicon.android.agendatechlatam.view.model.Event

/**
 * Created by armando on 3/9/17.
 */
interface MainView {
    fun showEmptyListMessage()
    fun showEvents(eventList: List<Event>)
    fun showLoading()
    fun hideLoading()
}