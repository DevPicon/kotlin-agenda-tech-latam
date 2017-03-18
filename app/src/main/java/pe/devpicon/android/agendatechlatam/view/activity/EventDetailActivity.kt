package pe.devpicon.android.agendatechlatam.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event_detail.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.utils.loadImage
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.presenter.EventDetailPresenterImpl
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailView

class EventDetailActivity : AppCompatActivity(), EventDetailView {
    override fun showEvent(event: Event) {
        with(event) {
            txt_detail_event_name.text = name
            txt_detail_event_date.text = date
            txt_detail_event_city.text = city
            txt_detail_event_country.text = country
            supportActionBar?.title = name
            img_event_detail_cover.loadImage(imageUrl)
        }
    }

    val presenter: EventDetailPresenter by lazy {
        EventDetailPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setupToolbar()
        initializeContent()

        presenter.setView(this)


    }

    private fun initializeContent() {
        presenter.getContentFromIntent(intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(app_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this@EventDetailActivity,
                R.drawable.arrow_left))
    }
}
