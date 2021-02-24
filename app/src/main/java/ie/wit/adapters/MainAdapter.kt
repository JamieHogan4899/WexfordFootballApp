package ie.wit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.R
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.card_donation.view.*

class MainAdapter constructor(private var teams: List<TeamModel>)
    : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_donation,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = teams[holder.adapterPosition]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = teams.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(donation: TeamModel) {
            itemView.paymentamount.text = donation.amount.toString()
            itemView.paymentmethod.text = donation.paymentmethod
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}