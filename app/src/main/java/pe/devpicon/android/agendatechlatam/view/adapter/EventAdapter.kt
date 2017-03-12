package pe.devpicon.android.agendatechlatam.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.model.Event

/**
 * Created by armando on 3/10/17.
 */
class EventAdapter(var items: List<Event> = mutableListOf(), val itemClick: OnItemClickListener) : RecyclerView
.Adapter<EventViewHolder>() {
    override fun onBindViewHolder(holder: EventViewHolder?, position: Int) {
        holder?.let { holder.bindEvent(items.get(position)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        operator fun invoke(event:Event)
    }
}