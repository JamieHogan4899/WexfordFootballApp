package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.helpers.readImageFromPath
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.card_register.view.*

interface TeamListener {
    fun onTeamClick(team: TeamModel)
}


class MainAdapter(private var teams: ArrayList<TeamModel>, private val listener: TeamListener
                  )
    : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_register,
                parent,

                false
            )
        )
    }

    //note no listener so you cant click in the list screen
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team, listener)

    }

    override fun getItemCount(): Int = teams.size

    //show added teams
    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: TeamModel, listener: TeamListener) {
            itemView.tag = team
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, team.image))
            itemView.listName.text = team.name
            itemView.teamLocation.text = team.location
            itemView.teamAmount.text = team.amount.toString()
            itemView.setOnClickListener { listener.onTeamClick(team) }



        }
    }



}