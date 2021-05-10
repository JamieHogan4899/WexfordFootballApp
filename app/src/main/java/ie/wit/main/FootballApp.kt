package ie.wit.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.wit.models.TeamJSONStore
//import ie.wit.models.TeamMemStore
import ie.wit.models.TeamModel
import ie.wit.models.TeamStore

class FootballApp : Application() {


    lateinit var database: DatabaseReference
    var teams = ArrayList<TeamModel>()
    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate() {
        super.onCreate()
        Log.v("App","Jamies App started")
    }
}