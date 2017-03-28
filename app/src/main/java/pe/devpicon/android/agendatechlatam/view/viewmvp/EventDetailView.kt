package pe.devpicon.android.agendatechlatam.view.viewmvp

import android.content.Intent
import pe.devpicon.android.agendatechlatam.view.model.Event

/**
 * Created by armando on 3/17/17.
 */
interface EventDetailView {
    fun showEvent(event: Event)
    fun openURL(url: String)
}

interface EventDetailPresenter {
    fun setView(detailView: EventDetailView)
    fun getContentFromIntent(intent: Intent)
    fun onRSVPButtonClicked()
}