package pe.devpicon.android.agendatechlatam.view.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import pe.devpicon.android.agendatechlatam.view.model.EventModel
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventPresenter
import pe.devpicon.android.agendatechlatam.view.viewmvp.NewEventView
import java.io.File
import java.text.SimpleDateFormat

/**
 * Created by armando on 3/17/17.
 */
class NewEventPresenterImpl : NewEventPresenter {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val uri = data.data
                    newEventView.showImage(uri)
                }
            }
        }

    }

    override fun onPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    newEventView.onSaveModel()
                } else {
                    requestRequiredPermissions()
                }
            }
        }

    }

    override fun onImageClicked() {


        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        newEventView.startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen " +
                "para el evento"), PICK_IMAGE_REQUEST)


    }

    override fun onInitCountrySpinner() {

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

                newEventView.showCountries(countryArray)

            }

        })

    }

    override fun onInitEventTypeSpinner() {

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

                newEventView.showEventTypes(eventTypes)


            }

        })
    }


    companion object {
        val PICK_IMAGE_REQUEST = 321
        val REQUEST_WRITE_EXTERNAL_STORAGE = 322
    }


    override fun onSaveButtonClick(model: EventModel) {

        if (newEventView.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || newEventView.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED
                ) {
            requestRequiredPermissions()
        } else {
            saveModel(model)

        }
    }

    private fun requestRequiredPermissions() {
        newEventView.requestRequiredPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE)
    }

    private fun saveModel(model: EventModel) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance();
        val key = database.reference.child("events").push().key
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: 0

        Log.d(javaClass.simpleName, "Esta es la uri ${model.imageUri}")


        // Store Image into Firebase Storage
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val file: Uri = Uri.fromFile(File(model.imageUri))
        val imageReference = storage.reference.child("eventImages/${file.lastPathSegment}")
        val uploadTask = imageReference.putFile(Uri.parse(model.imageUri))

        uploadTask.addOnFailureListener { Log.d(javaClass.simpleName, "Ocurrió un error") }
        uploadTask.addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
            override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {

                taskSnapshot?.let {
                    val downloadUrl = taskSnapshot.downloadUrl

                    model.imageUri = downloadUrl.toString()
                    model.date = dateFormat(model.date)

                    val eventValues = model.toMap()
                    val childUpdates = mapOf<String, Any>(
                            "/events/$key" to eventValues,
                            "/user-events/$uid/$key" to eventValues
                    )

                    val updateChildren = database.reference.updateChildren(childUpdates).addOnCompleteListener {

                        newEventView.goToMainActivity()

                    }
                }


            }
        })
    }

    lateinit var newEventView: NewEventView

    override fun setView(newEventView: NewEventView) {
        this.newEventView = newEventView
    }

    private fun dateFormat(date: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val parsedDate = dateFormat.parse(date)
        val dateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat2.format(parsedDate)
    }

}