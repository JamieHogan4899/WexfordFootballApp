package ie.wit.models

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import java.util.*


val JSON_FILE = "teams.json"


fun generateRandomId(): Long {
    return Random().nextLong()
}

class TeamJSONStore : TeamStore, AnkoLogger {

    val context: Context
    var teams = mutableListOf<TeamModel>()

    constructor (context: Context) {
        this.context = context
    }

    override fun findAll(): MutableList<TeamModel> {
        return teams
    }

    override fun findById(id: Long): TeamModel? {
        TODO("Not yet implemented")
    }

    override fun create(team: TeamModel) {
        team.id = generateRandomId()
        teams.add(team)

    }


    override fun update(team: TeamModel) {
        val teamList = findAll() as ArrayList<TeamModel>
        var foundTeam: TeamModel? = teamList.find { p -> p.id == team.id }
        if (foundTeam != null) {
            foundTeam.name = team.name
            foundTeam.location = team.location
            foundTeam.amount = team.amount
            foundTeam.image = team.image

        }

    }

    fun delete(team: TeamModel) {
        teams.remove(team)

    }



}