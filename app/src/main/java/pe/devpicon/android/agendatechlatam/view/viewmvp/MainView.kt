package pe.devpicon.android.agendatechlatam.view.viewmvp

import android.content.Context
import android.content.Intent
import pe.devpicon.android.agendatechlatam.view.model.Event

/**
 * Created by armando on 3/9/17.
 */
interface MainView {
    fun showEmptyListMessage()
    fun hideEmptyListMessage()
    fun showEvents(eventList: List<Event>)
    fun hideEvents()
    fun showLoading()
    fun hideLoading()
    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun goToEventDetailActivity(event: Event)
    fun goToNewEventActivity()
    fun finish()
    fun showSnackbar(stringResourceId: Int)
    fun getContext(): Context
    fun showNotConnectedMessage()
    fun hideNotConnectedMessage()
}

interface MainPresenter {
    fun getEvents();
    fun onFabClicked();
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}