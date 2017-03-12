package pe.devpicon.android.agendatechlatam.view.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by armando on 3/9/17.
 */
data class Event(val id: Int,
                 val name: String,
                 val type: String,
                 val date: String,
                 val city: String,
                 val country: String,
                 val imageUrl: String = "",
                 val url: String = "",
                 val venue: Venue? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event>{
            override fun newArray(size: Int): Array<Event?> = arrayOfNulls<Event>(size)
            override fun createFromParcel(source: Parcel): Event = Event(source)
        }
    }

    constructor(source: Parcel):this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable(Event::class.java.classLoader)
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(id)
            dest.writeString(name)
            dest.writeString(type)
            dest.writeString(date)
            dest.writeString(city)
            dest.writeString(country)
            dest.writeString(imageUrl)
            dest.writeString(url)
            dest.writeParcelable(venue, flags)
        }
    }

    override fun describeContents(): Int = 0

}

