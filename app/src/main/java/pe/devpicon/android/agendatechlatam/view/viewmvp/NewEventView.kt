package pe.devpicon.android.agendatechlatam.view.viewmvp

import android.content.Intent
import android.net.Uri
import pe.devpicon.android.agendatechlatam.view.model.EventModel

/**
 * Created by armando on 3/17/17.
 */
interface NewEventView {
    fun goToMainActivity()
    fun finish()
    fun showEventTypes(eventTypes: MutableList<String>)
    fun showCountries(countryArray: MutableList<String>)
    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun checkSelfPermission(permission: String): Int
    fun requestRequiredPermissions(permissions: Array<out String>, requestCode: Int)
    fun showImage(uri: Uri?)
    fun onSaveModel()

}

interface NewEventPresenter {
    fun setView(newEventView: NewEventView)
    fun onSaveButtonClick(model: EventModel)
    fun onInitEventTypeSpinner()
    fun onInitCountrySpinner()
    fun onImageClicked()
    fun onPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}