package pe.devpicon.android.agendatechlatam.view.model

/**
 * Created by armando on 3/14/17.
 */
class EventModel{
    var name: String = ""
    var type: String = ""
    var date: String = ""
    var city: String = ""
    var country: String = ""

    constructor(){

    }

    constructor(name: String, type: String, date: String, city: String, country: String){
        this.name = name
        this.type = type
        this.date = date
        this.city = city
        this.country = country


    }

    fun toMap() = mapOf<String, String>(
            "name" to name,
            "type" to type,
            "date" to date,
            "city" to city,
            "country" to country
    )
}
