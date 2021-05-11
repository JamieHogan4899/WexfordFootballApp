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


class MainAdapter(private var teams: ArrayList<TeamModel>, private val listener: TeamListener, reportall: Boolean
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
    val reportAll = reportall


    //note no listener so you cant click in the list screen
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team, listener, reportAll)

    }

    override fun getItemCount(): Int = teams.size

    //show added teams
    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(team: TeamModel, listener: TeamListener, reportAll: Boolean) {
            itemView.tag = team
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, team.image))
            itemView.listName.text = team.name
            itemView.teamLocation.text = team.location
            itemView.teamAmount.text = team.amount.toString()
            if(team.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)
            if(!reportAll)
                itemView.setOnClickListener { listener.onTeamClick(team) }



        }
    }



}