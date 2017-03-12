package pe.devpicon.android.agendatechlatam.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event_detail.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.model.Event

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setupToolbar()
        initializeContent()

    }

    private fun initializeContent() {
        val event = intent.getParcelableExtra<Event>("event")

        txt_detail_event_name.text = event.name
        txt_detail_event_date.text = event.date
        txt_detail_event_city.text = event.city
        txt_detail_event_country.text = event.country
        supportActionBar?.title = event.name

    }

    private fun setupToolbar() {
        setSupportActionBar(app_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this@EventDetailActivity,
                R.drawable.arrow_left))
    }
}
