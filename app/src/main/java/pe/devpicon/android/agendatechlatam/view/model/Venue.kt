package pe.devpicon.android.agendatechlatam.view.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by armando on 3/9/17.
 */
data class Venue(val name: String, val address: String, var longitude: String = "0", var latitude:
String = "0") : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Venue> = object : Parcelable.Creator<Venue> {
            override fun newArray(size: Int): Array<Venue?> = arrayOfNulls(size)

            override fun createFromParcel(source: Parcel): Venue = Venue(source)

        }

    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeString(name)
            dest.writeString(address)
            dest.writeString(longitude)
            dest.writeString(latitude)
        }
    }

    override fun describeContents(): Int = 0
}