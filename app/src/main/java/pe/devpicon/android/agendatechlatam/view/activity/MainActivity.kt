package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter.OnItemClickListener
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.presenter.MainPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainView

class MainActivity : AppCompatActivity(), MainView {

    //var adapter: EventAdapter? = null
    val adapter: EventAdapter by lazy { initializeAdapter() }
    var presenter: MainPresenter? = null


    override fun showLoading() {
        progress_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_loading.visibility = View.GONE
    }

    override fun showEmptyListMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEvents(eventList: List<Event>) {
        adapter.items = eventList
        adapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAdapter()
        initializeList()
        initializePresenter()
        presenter?.getEvents()

    }

    private fun initializePresenter() {
        presenter = MainPresenter()
        presenter?.setView(this)
    }

    private fun initializeList() {
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    /* private fun initializeAdapter() {
        adapter = EventAdapter()
    }*/

    private fun initializeAdapter() = EventAdapter(itemClick = object : OnItemClickListener{
        override fun invoke(event: Event) {

            var intent = Intent(this@MainActivity, EventDetailActivity::class.java )
            intent.putExtra("event", event)
            startActivity(intent)

        }

    })
}
