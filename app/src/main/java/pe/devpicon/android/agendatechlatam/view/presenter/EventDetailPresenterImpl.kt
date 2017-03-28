package pe.devpicon.android.agendatechlatam.view.presenter

import android.content.Intent
import android.util.Log
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailView

/**
 * Created by armando on 3/17/17.
 */
class EventDetailPresenterImpl : EventDetailPresenter {

    lateinit var event : Event
    lateinit var detailView: EventDetailView

    override fun onRSVPButtonClicked() {
        detailView.openURL(event.url)
    }

    override fun setView(detailView: EventDetailView) {
        this.detailView = detailView
    }

    override fun getContentFromIntent(intent: Intent) {
        event = intent.getParcelableExtra<Event>("event")
        Log.d(javaClass.simpleName, event.toString())
        detailView.showEvent(event)
    }

}