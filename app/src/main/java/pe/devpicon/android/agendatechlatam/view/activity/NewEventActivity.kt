package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_new_event.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.utils.loadImage
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.presenter.NewEventPresenterImpl
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventView

class NewEventActivity : AppCompatActivity(), NewEventView, View.OnClickListener {

    var localImageUri: String = ""
    val presenter: NewEventPresenter by lazy { NewEventPresenterImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        presenter.setView(this@NewEventActivity)

        initializeCountrySpinner()
        initializeEventTypeSpinner()
        initializeSaveButton()
        initializeImageCover()

    }

    override fun showImage(uri: Uri?) {
        uri?.let {
            localImageUri = uri.toString()
            img_new_event_image.loadImage(localImageUri)
        }
    }

    override fun checkSelfPermission(permission: String): Int {

        return ContextCompat.checkSelfPermission(
                this@NewEventActivity,
                permission
        )

    }

    override fun requestRequiredPermissions(permissions: Array<out String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
                this@NewEventActivity,
                permissions,
                requestCode
        )
    }


    override fun onClick(v: View?) {

        v?.let {
            when (v.id) {
                R.id.img_new_event_image -> {
                    presenter.onImageClicked()
                }
                R.id.btn_new_event_save -> {
                    onSaveModel()
                }
                else -> Log.d(javaClass.simpleName, "Ningun evento clickable: ${v.id}")
            }
        }
    }

    override fun onSaveModel() {
        val model = EventModel(
                edt_new_event_name.text.toString(),
                spn_new_event_type.selectedItem.toString(),
                edt_new_event_date.text.toString(),
                edt_new_event_city.text.toString(),
                spn_new_event_country.selectedItem.toString(),
                localImageUri,
                edt_new_event_url.text.toString()
        )

        presenter.onSaveButtonClick(model)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.onActivityResult(requestCode, resultCode, data)

    }


    private fun initializeImageCover() {
        img_new_event_image.setOnClickListener(this)
    }

    override fun showCountries(countryArray: MutableList<String>) {

        val arrayAdapter = initializeAdapter(countryArray)
        spn_new_event_country.adapter = arrayAdapter

    }

    override fun showEventTypes(eventTypes: MutableList<String>) {
        val arrayAdapter = initializeAdapter(eventTypes)
        spn_new_event_type.adapter = arrayAdapter
    }

    private fun initializeSaveButton() {

        btn_new_event_save.setOnClickListener(this)
    }

    override fun goToMainActivity() {
        var intent = Intent(this@NewEventActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initializeEventTypeSpinner() {
        presenter.onInitEventTypeSpinner()
    }

    private fun initializeCountrySpinner() {
        presenter.onInitCountrySpinner()

    }

    private fun initializeAdapter(elements: MutableList<String>): ArrayAdapter<String> {
        val arrayAdapter = ArrayAdapter<String>(this@NewEventActivity, android.R.layout
                .simple_spinner_item, elements)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return arrayAdapter
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        presenter.onPermissionsResult(requestCode, permissions, grantResults)
    }


}
