package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class TeamMemStore : TeamStore {

        val teams = ArrayList<TeamModel>()

        override fun findAll(): List<TeamModel> {
            return teams
        }

        override fun findById(id:Long) : TeamModel? {
            val foundTeam: TeamModel? = teams.find { it.id == id }
            return foundTeam
        }

        override fun create(team: TeamModel) {
            team.id = getId()
            teams.add(team)
            logAll()
        }

    override fun delete(team: TeamModel) {
        teams.remove(team)
    }

        fun logAll() {
            Log.v("Team","** Teams List **")
            teams.forEach { Log.v("Teams","${it}") }
        }


        override fun update(team: TeamModel) {
            var foundTeam: TeamModel? = teams.find { p -> p.id == team.id }
            if (foundTeam != null) {
                foundTeam.name = team.name
                foundTeam.location = team.location
                foundTeam.amount = team.amount
                foundTeam.image = team.image

                logAll()

        }
        }


    }
