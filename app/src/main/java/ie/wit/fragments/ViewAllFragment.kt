package ie.wit.fragments


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.adapters.MainAdapter
import ie.wit.adapters.TeamListener
import ie.wit.models.TeamModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.card_register.*
import kotlinx.android.synthetic.main.card_register.view.*
import kotlinx.android.synthetic.main.card_register.view.imagefavourite
import kotlinx.android.synthetic.main.fragment_add.view.*
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

        //where to get teams
        var query = FirebaseDatabase.getInstance("https://assignment-2-fe885-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference.child("teams")

        var options = FirebaseRecyclerOptions.Builder<TeamModel>()
            .setQuery(query, TeamModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = MainAdapter(options, this)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ViewAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    }

