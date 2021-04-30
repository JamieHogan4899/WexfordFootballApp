package ie.wit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.helpers.readEditImage
import ie.wit.helpers.readImage
import ie.wit.helpers.showEditImagePicker
import ie.wit.helpers.showImagePicker
import ie.wit.main.FootballApp
//import ie.wit.models.TeamMemStore
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.card_register.view.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_add.view.homePitch
import kotlinx.android.synthetic.main.fragment_add.view.squadPicker
import kotlinx.android.synthetic.main.fragment_add.view.teamName
import kotlinx.android.synthetic.main.fragment_editdetails.*
import kotlinx.android.synthetic.main.fragment_editdetails.view.*


class EditDetailsFragment : Fragment() {

    lateinit var app: FootballApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp

    }



    //load what fragment XML to load, set values for squad picker, button listeners
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var root = inflater.inflate(R.layout.fragment_editdetails, container, false)
        root.editTeamName.setText(app.theChoosenTeam.name)
        root.editHomePitch.setText(app.theChoosenTeam.location)
        //root.editSquadNumber.setText(app.theChoosenTeam.amount) //load back in sqaud picker number

        root.editSquadNumber.minValue = 11
        root.editSquadNumber.maxValue = 28

        setDeleteButtonListener(root)
        setUpdateButtonListener(root)



        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                EditDetailsFragment().apply {
                    arguments = Bundle().apply { }
               }
    }
    //handle delete button
    fun setDeleteButtonListener(layout: View) {
        layout.withdraw.setOnClickListener {
            //remove the team your viewing
            var team = app.theChoosenTeam
            //call delete methood to remove team fro array
            app.teamsStore.delete(team)
            //go back to edit list page
            val fragment = EditFragment()
            val ft = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.homeFrame, fragment);
            fragmentTransaction?.addToBackStack(null);
            fragmentTransaction?.commit();
            //test
             println("team withdrawn")


        }
        }

    fun setUpdateButtonListener(layout: View) {
        layout.updateBtn.setOnClickListener {
            //get whats in the fields and set them as the new value in that id

            var team = app.theChoosenTeam
            team.name = editTeamName.text.toString()
            team.location = editHomePitch.text.toString()
            team.amount = editSquadNumber.value

            //data validation
            if (team.name.isEmpty()||team.location.isEmpty()) {
                Toast.makeText(getActivity(),"Please check all fields are filled in", Toast.LENGTH_SHORT).show();
            }   else {
                //storing update value
                app.teamsStore.update(TeamModel(name = team.name, location = team.location, amount = team.amount, image = team.image))
                //test
                println(team.name)

                //go back when update hit
                val fragment = EditFragment()
                val ft = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.homeFrame, fragment);
                fragmentTransaction?.addToBackStack(null);
                fragmentTransaction?.commit();
                println("Updated")
            }

        }


    }



}