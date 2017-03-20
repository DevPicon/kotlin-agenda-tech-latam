package pe.devpicon.android.agendatechlatam.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.event_item.view.*
import pe.devpicon.android.agendatechlatam.utils.loadImage
import pe.devpicon.android.agendatechlatam.view.model.Event

/**
 * Created by armando on 3/10/17.
 */
class EventViewHolder(itemView: View, val itemClick: EventAdapter.OnItemClickListener):RecyclerView
.ViewHolder(itemView){
    fun bindEvent(event:Event){
        itemView.txt_item_event_title.text = event.name
        itemView.txt_item_event_country.text = event.country
        itemView.txt_item_event_date.text = event.date
        itemView.img_item_event_image.loadImage(event.imageUrl)
        itemView.setOnClickListener{itemClick(event)}
    }
}