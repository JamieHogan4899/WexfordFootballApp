package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import ie.wit.R
import ie.wit.fragments.ViewAllFragment
import ie.wit.helpers.readImageFromPath
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.card_register.view.*


interface TeamListener {
    fun onTeamClick(team: TeamModel)
}


class MainAdapter(options: FirebaseRecyclerOptions<TeamModel>,
                  private val listener: TeamListener?)

    : FirebaseRecyclerAdapter<TeamModel, MainAdapter.TeamViewHolder>(options) {

    //info on the cards
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: TeamModel, listener: TeamListener) {
            with(team) {
                itemView.tag = team
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, team.image))
                itemView.listName.text = team.name
                itemView.teamLocation.text = team.location
                itemView.teamAmount.text = team.amount.toString()

                //set settings for the view all teams
                if (listener is ViewAllFragment)
                    ; // Do Nothing, Don't Allow 'Clickable' Rows
                else
                    itemView.setOnClickListener { listener.onTeamClick(team) }

                if (team.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {

        return TeamViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_register, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int, model: TeamModel) {
        holder.bind(model,listener!!)
    }







}