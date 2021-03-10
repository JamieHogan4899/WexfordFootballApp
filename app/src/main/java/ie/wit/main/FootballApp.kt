package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.TeamJSONStore
import ie.wit.models.TeamMemStore
import ie.wit.models.TeamModel
import ie.wit.models.TeamStore

class FootballApp : Application() {

    lateinit var teamsStore: TeamStore
    lateinit var theChoosenTeam: TeamModel  //Siobhan Added

    override fun onCreate() {
        super.onCreate()
        teamsStore = TeamMemStore()
        theChoosenTeam = TeamModel()  //Siobhan Added
        teamsStore = TeamJSONStore(applicationContext)


        Log.v("App","Jamies App started")
    }
}