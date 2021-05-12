package ie.wit.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.MainAdapter
import ie.wit.adapters.TeamListener
import ie.wit.main.FootballApp
import ie.wit.models.TeamModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_report.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class ReportFragment : Fragment(), AnkoLogger, TeamListener   {

    lateinit var app: FootballApp
    lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp
    }
    //list teams, not how you cant click into it
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_report, container, false)
        activity?.title = getString(R.string.action_report)
        root.recyclerView.setLayoutManager(LinearLayoutManager(activity!!))

        //get teams from database
        var query = FirebaseDatabase.getInstance("https://assignment-2-fe885-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
            .child("user-teams").child(app.currentUser.uid)
         println(query)

        var options = FirebaseRecyclerOptions.Builder<TeamModel>()
            .setQuery(query, TeamModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = MainAdapter(options, this)




        //handle what happens when swiping to delete and edit
        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as MainAdapter
                deleteTeam((viewHolder.itemView.tag as TeamModel).uid)
                deleteUserTeam(app.currentUser!!.uid,
                    (viewHolder.itemView.tag as TeamModel).uid)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onTeamClick(viewHolder.itemView.tag as TeamModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)


        return root
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    //deleing team for the user
    fun deleteUserTeam(userId: String, uid: String?) {
        app.database.child("user-teams").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Team error : ${error.message}")
                    }
                })
    }

    //deleting by id on database
    fun deleteTeam(uid: String?) {
        app.database.child("teams").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Team error : ${error.message}")
                    }
                })
    }

    //handle what happens when clicking on team
    override fun onTeamClick(team: TeamModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, EditDetailsFragment.newInstance(team))
            .addToBackStack(null)
            .commit()
    }



}
