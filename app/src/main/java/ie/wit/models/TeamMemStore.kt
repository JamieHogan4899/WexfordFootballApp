package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class TeamMemStore : TeamStore {

        val donations = ArrayList<TeamModel>()

        override fun findAll(): List<TeamModel> {
            return donations
        }

        override fun findById(id:Long) : TeamModel? {
            val foundDonation: TeamModel? = donations.find { it.id == id }
            return foundDonation
        }

        override fun create(donation: TeamModel) {
            donation.id = getId()
            donations.add(donation)
            logAll()
        }

        fun logAll() {
            Log.v("Donate","** Donations List **")
            donations.forEach { Log.v("Donate","${it}") }
        }
    }
