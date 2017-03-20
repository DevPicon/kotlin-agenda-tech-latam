package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter
import pe.devpicon.android.agendatechlatam.view.adapter.EventAdapter.OnItemClickListener
import pe.devpicon.android.agendatechlatam.view.model.Event
import pe.devpicon.android.agendatechlatam.view.presenter.MainPresenterImpl
import pe.devpicon.android.agendatechlatam.view.viewmvp.MainView

class MainActivity : AppCompatActivity(), MainView {

    val adapter: EventAdapter by lazy { initializeAdapter() }
    var presenter: MainPresenterImpl? = null

    override fun hideEmptyListMessage() {
        txt_empty_list.visibility = View.GONE
    }

    override fun hideEvents() {
        recycler_view.visibility = View.GONE
    }

    override fun hideNotConnectedMessage() {
        txt_not_connected.visibility = View.GONE
    }

    override fun showNotConnectedMessage() {
        txt_not_connected.visibility = View.VISIBLE
    }

    override fun getContext(): Context {
        return this;
    }

    override fun showLoading() {
        progress_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_loading.visibility = View.GONE
    }

    override fun showEmptyListMessage() {
        txt_empty_list.visibility = View.VISIBLE
    }

    override fun showEvents(eventList: List<Event>) {

        Log.d(javaClass.simpleName, "Ingreso a showEvents")
        adapter.items = eventList
        adapter.notifyDataSetChanged()


        txt_empty_list.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAdapter()
        initializeList()
        initializePresenter()
        initializeFab()
        presenter?.getEvents()

    }

    fun initializeFab() {

        fab_new_event.setOnClickListener {
            presenter?.onFabClicked()
        }


    }

    override fun goToNewEventActivity() {
        var intent = Intent(this@MainActivity, NewEventActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter?.onActivityResult(requestCode, resultCode, data)

    }

    override fun showSnackbar(stringResourceId: Int) {
        val snackbar = Snackbar.make(main_coordinator_layout, getString(stringResourceId), Snackbar
                .LENGTH_SHORT)
        snackbar.show()

    }


    private fun initializePresenter() {
        presenter = MainPresenterImpl()
        presenter?.setView(this)
    }

    private fun initializeList() {
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun initializeAdapter() = EventAdapter(itemClick = object : OnItemClickListener {
        override fun invoke(event: Event) {

            goToEventDetailActivity(event)

        }

    })

    override fun goToEventDetailActivity(event: Event) {
        var intent = Intent(this@MainActivity, EventDetailActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }
}
