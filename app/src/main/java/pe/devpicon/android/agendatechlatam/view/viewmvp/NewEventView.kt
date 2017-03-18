package pe.devpicon.android.agendatechlatam.view.viewmvp

import pe.devpicon.android.agendatechlatam.view.model.EventModel

/**
 * Created by armando on 3/17/17.
 */
interface NewEventView {
    fun goToMainActivity()
    fun finish()
    fun showEventTypes(eventTypes: MutableList<String>)
    fun showCountries(countryArray: MutableList<String>)

}

interface NewEventPresenter {
    fun setView(newEventView: NewEventView)
    fun onSaveButtonClick(model: EventModel)
    fun onInitEventTypeSpinner()
    fun onInitCountrySpinner()
}