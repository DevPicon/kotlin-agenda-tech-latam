package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_new_event.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.presenter.NewEventPresenterImpl
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventView

class NewEventActivity : AppCompatActivity(), NewEventView {

    val presenter: NewEventPresenter by lazy { NewEventPresenterImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        presenter.setView(this@NewEventActivity)

        initializeCountrySpinner()
        initializeEventTypeSpinner()
        initializeSaveButton()

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

        btn_new_event_save.setOnClickListener {

            val model = EventModel(
                    edt_new_event_name.text.toString(),
                    spn_new_event_type.selectedItem.toString(),
                    edt_new_event_date.text.toString(),
                    edt_new_event_city.text.toString(),
                    spn_new_event_country.selectedItem.toString()
            )

            presenter.onSaveButtonClick(model)
        }
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


}
