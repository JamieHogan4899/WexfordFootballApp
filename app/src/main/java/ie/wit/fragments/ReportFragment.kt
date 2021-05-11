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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
    lateinit var loader : AlertDialog

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

        setSwipeRefresh()


        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as MainAdapter
                deleteTeam((viewHolder.itemView.tag as TeamModel).uid)
                deleteUserTeam(app.auth.currentUser!!.uid,
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

    fun deleteTeam(uid: String?) {
        app.database.child("teams").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Teams error : ${error.message}")
                    }
                })
    }

    open fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllTeams(app.auth.currentUser!!.uid)
            }
        })
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
    }

    fun getAllTeams(userId: String?) {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Teams from Firebase")
        var teamList = ArrayList<TeamModel>()
        app.database.child("user-teams").child(userId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Team error : ${error.message}")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        hideLoader(loader)
                        val children = snapshot.children
                        children.forEach {
                            val team = it.
                            getValue<TeamModel>(TeamModel::class.java)

                            teamList.add(team!!)
                            root.recyclerView.adapter =
                                MainAdapter(teamList, this@ReportFragment, true)

                            checkSwipeRefresh()


                            app.database.child("user-teams").child(userId)
                                .removeEventListener(this)
                        }
                    }
                })
    }



    override fun onResume() {
        super.onResume()
        if(this::class == ReportFragment::class)
            getAllTeams(app.auth.currentUser!!.uid)
    }

     override fun onTeamClick(team: TeamModel) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame, EditDetailsFragment.newInstance(team))
                .addToBackStack(null)
                .commit()
    }



}
