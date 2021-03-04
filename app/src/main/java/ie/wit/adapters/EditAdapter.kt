package ie.wit.adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.fragments.AddFragment
import ie.wit.fragments.TeamListener
import ie.wit.helpers.readImageFromPath
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.card_register.view.*

class EditAdapter constructor(private var teams: List<TeamModel>, private val listener: TeamListener )


    : RecyclerView.Adapter<EditAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAdapter.MainHolder {
        return EditAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_register,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EditAdapter.MainHolder, position: Int) {
        val team = teams[holder.adapterPosition]
        holder.bind(team)
    }







    override fun getItemCount(): Int = teams.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(team: TeamModel) {
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, team.image))
            itemView.listName.text = team.name
            itemView.teamLocation.text = team.location
            itemView.teamId.text = team.id.toString()
            }
    }

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(team: TeamModel, listener: TeamListener) {
            itemView.listName.text = team.name
            itemView.teamLocation.text = team.location
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, team.image))
            itemView.listName.text = team.name
            itemView.setOnClickListener { listener.onTeamClick(team) }
        }


    }




}
