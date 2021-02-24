package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.TeamMemStore
import ie.wit.models.TeamStore

class FootballApp : Application() {

    lateinit var teamsStore: TeamStore

    override fun onCreate() {
        super.onCreate()
        teamsStore = TeamMemStore()
        Log.v("Donate","Donation App started")
    }
}