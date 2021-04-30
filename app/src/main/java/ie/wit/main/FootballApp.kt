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

    lateinit var teamsStore: TeamStore
    lateinit var theChoosenTeam: TeamModel  //global
    lateinit var database: DatabaseReference

    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate() {
        super.onCreate()
        //teamsStore = TeamMemStore()
        theChoosenTeam = TeamModel()
        //teamsStore = TeamJSONStore(applicationContext)


        Log.v("App","Jamies App started")
    }
}