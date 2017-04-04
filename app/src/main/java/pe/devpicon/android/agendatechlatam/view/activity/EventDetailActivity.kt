package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_event_detail.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.utils.loadImage
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.presenter.EventDetailPresenterImpl
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.EventDetailView

class EventDetailActivity : AppCompatActivity(), EventDetailView, View.OnClickListener {

    //val ui by lazy { EventDetailActivityUI() }

    val presenter: EventDetailPresenter by lazy {
        EventDetailPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        //ui.setContentView(this)

        setupTransition()

        presenter.setView(this)
        setupToolbar()
        initializeContent()
        initializeRSVPButton()

    }

    private fun setupTransition() {
        supportPostponeEnterTransition()

    }

    private fun initializeRSVPButton() {
        btn_rsvp.setOnClickListener(this@EventDetailActivity)
    }

    private fun initializeContent() {
        presenter.getContentFromIntent(intent)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(app_bar)

    }

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                R.id.btn_rsvp -> {
                    presenter.onRSVPButtonClicked()
                }
            }
        }
    }

    override fun openURL(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d(javaClass.simpleName, "No se pudo resolver el intent")
        }
    }

    override fun showEvent(event: Event) {
        with(event) {
            //ui.textViewEventName.text = name
            //ui.image.loadImage(imageUrl)

            txt_detail_event_name.text = name
            txt_detail_event_date.text = date
            txt_detail_event_city.text = city
            txt_detail_event_country.text = country
            supportActionBar?.title = name
            txt_detail_event_type.text = type
            img_event_detail_cover.loadImage(imageUrl)
            img_event_detail_cover.viewTreeObserver.addOnPreDrawListener(
                    object : ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            img_event_detail_cover.viewTreeObserver.removeOnPreDrawListener(this)
                            supportStartPostponedEnterTransition()
                            return true
                        }
                    }
            )
        }
    }
}
