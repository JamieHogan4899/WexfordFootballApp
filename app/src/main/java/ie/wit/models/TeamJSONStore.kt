package ie.wit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.com.wit.helpers.exists
import ie.com.wit.helpers.read
import ie.com.wit.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

//name json file
val JSON_FILE = "teams.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<TeamModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

//list of methods for json to take in

class TeamJSONStore : TeamStore, AnkoLogger {

    val context: Context
    var teams = mutableListOf<TeamModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<TeamModel> {
        return teams
    }

    override fun findById(id: Long): TeamModel? {
        return null
    }

    override fun create(team: TeamModel) {
        team.id = generateRandomId()
        teams.add(team)
        serialize()

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
        serialize()
    }

    override fun delete(team: TeamModel) {
        teams.remove(team)
        serialize()

    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(teams, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        teams = Gson().fromJson(jsonString, listType)
    }


}