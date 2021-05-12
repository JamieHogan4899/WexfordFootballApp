package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


class FootballApp : Application() {


    lateinit var database: DatabaseReference
    lateinit var currentUser: FirebaseUser
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference




    override fun onCreate() {
        super.onCreate()
        Log.v("App","Jamies App started")
    }
}