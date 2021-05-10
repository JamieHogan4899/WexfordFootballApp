package ie.wit.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.adapters.MainAdapter
import ie.wit.adapters.TeamListener
import ie.wit.models.TeamModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_report.view.*
import org.jetbrains.anko.info

class ViewAllFragment : ReportFragment(),
    TeamListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_report, container, false)
        activity?.title = getString(R.string.list)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        setSwipeRefresh()

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ViewAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }

       override fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllUsersTeams()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllUsersTeams()
    }

    fun getAllUsersTeams() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading All Teams")
        val teamsList = ArrayList<TeamModel>()
        app.database.child("teams")
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

                        teamsList.add(team!!)
                        root.recyclerView.adapter =
                            MainAdapter(teamsList, this@ViewAllFragment)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("teams").removeEventListener(this)
                    }
                }
            })
    }







    }
