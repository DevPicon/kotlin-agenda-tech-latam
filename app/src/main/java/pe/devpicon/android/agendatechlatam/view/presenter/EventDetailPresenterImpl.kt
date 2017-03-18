package pe.devpicon.android.agendatechlatam.view.presenter

import android.content.Intent
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailView

/**
 * Created by armando on 3/17/17.
 */
class EventDetailPresenterImpl : EventDetailPresenter {

    lateinit var detailView: EventDetailView

    override fun setView(detailView: EventDetailView) {
        this.detailView = detailView

    }

    override fun getContentFromIntent(intent: Intent) {
        val event = intent.getParcelableExtra<Event>("event")

        detailView.showEvent(event)
    }

}