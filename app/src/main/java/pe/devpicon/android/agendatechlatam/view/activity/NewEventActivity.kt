package pe.devpicon.android.agendatechlatam.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_new_event.*
import pe.devpicon.android.agendatechlatam.R
import pe.devpicon.android.agendatechlatam.view.model.EventModel

class NewEventActivity : AppCompatActivity() {

    companion object{
        val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)


        initializeCountrySpinner()
        initializeEventTypeSpinner()

        initializeSaveButton()


    }

    private fun initializeSaveButton() {

        btn_new_event_save.setOnClickListener {

            val database: FirebaseDatabase = FirebaseDatabase.getInstance();
            val key = database.reference.child("events").push().key


            val model = EventModel(
                edt_new_event_name.text.toString(),
                spn_new_event_type.selectedItem.toString(),
                edt_new_event_date.text.toString(),
                edt_new_event_city.text.toString(),
                spn_new_event_country.selectedItem.toString()
            )

            val uid = FirebaseAuth.getInstance().currentUser?.uid?:0

            val eventValues = model.toMap()

            val childUpdates = mapOf<String, Any>(
                    "/events/$key" to eventValues,
                    "/user-events/$uid/$key" to eventValues
            )

            val updateChildren = database.reference.updateChildren(childUpdates).addOnCompleteListener {

                var intent = Intent(this@NewEventActivity, MainActivity::class.java)
                startActivity(intent)
                finish()

            }


        }


    }

    private fun initializeEventTypeSpinner() {

        var eventTypes = mutableListOf<String>()

        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.getReference("eventTypes");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    snapshot.children.forEach {
                        Log.d(javaClass.simpleName, it.key)
                        val value = it.getValue(String::class.java)
                        value?.let { eventTypes.add(it.toString()) }
                    }

                }

                Log.d(javaClass.simpleName, "${eventTypes.size}")

                val arrayAdapter = initializeAdapter(eventTypes)
                spn_new_event_type.adapter = arrayAdapter


            }

        })

    }

    private fun initializeCountrySpinner() {


        var countryArray = mutableListOf<String>()

        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val myRef: DatabaseReference = database.getReference("countries");
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    snapshot.children.forEach {
                        Log.d(javaClass.simpleName, it.key)
                        val value = it.getValue(String::class.java)
                        value?.let { countryArray.add(it.toString()) }
                    }

                }

                Log.d(javaClass.simpleName, "${countryArray.size}")

                val arrayAdapter = initializeAdapter(countryArray)
                spn_new_event_country.adapter = arrayAdapter


            }

        })



    }

    private fun initializeAdapter(elements: MutableList<String>): ArrayAdapter<String> {
        val arrayAdapter = ArrayAdapter<String>(this@NewEventActivity, android.R.layout
                .simple_spinner_item, elements)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return arrayAdapter
    }


}
