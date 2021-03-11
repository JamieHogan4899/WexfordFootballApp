package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.EditAdapter
import ie.wit.main.FootballApp
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.fragment_edit.view.*


interface TeamListener {
    fun onTeamClick(team: TeamModel)
}

class EditFragment : Fragment(), TeamListener {

    lateinit var app: FootballApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp


    }
    //load in list of teams and allow for click onto team
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_edit, container, false)




        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView.adapter = EditAdapter(app.teamsStore.findAll()) { item ->



            app.theChoosenTeam = item  //Siobhan Added
            val fragment = EditDetailsFragment()
            //   fragment.setArguments(bundle)
            val ft = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.homeFrame, fragment);
            fragmentTransaction?.addToBackStack(null);
            fragmentTransaction?.commit();
        }


        return root

    }

    companion object {
        @JvmStatic
        fun newInstance(addFragment: AddFragment.Companion) =
            EditFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onTeamClick(team: TeamModel) {

    }



}






