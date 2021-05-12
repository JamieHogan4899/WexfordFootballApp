package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.wit.R
import ie.wit.main.FootballApp
//import ie.wit.models.TeamMemStore
import ie.wit.models.TeamModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_editdetails.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info



class EditDetailsFragment : Fragment(), AnkoLogger {

    lateinit var app: FootballApp
    var editTeam: TeamModel? = null
    lateinit var loader : AlertDialog
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp

        arguments?.let {
            editTeam = it.getParcelable("editteam")
        }
    }

    //load what fragment XML to load, set values for squad picker, button listeners
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //testing
        info(editTeam)

        root = inflater.inflate(R.layout.fragment_editdetails, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)
        //set squad picker values
        root.editSquadNumber.minValue = 11
        root.editSquadNumber.maxValue = 28


        root.editTeamName.setText(editTeam!!.name)
        root.editHomePitch.setText(editTeam!!.location)

        root.updateBtn.setOnClickListener {
            showLoader(loader, "Updating Team on Firebase server...")
            updateTeamData()
            updateTeam(editTeam!!.uid, editTeam!!)
            updateUserTeam(app.currentUser!!.uid,
                    editTeam!!.uid, editTeam!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(team: TeamModel) =
                EditDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("editteam", team)
                    }
               }
    }

   fun updateTeamData() {
        editTeam!!.name = root.editTeamName.text.toString()
        editTeam!!.location = root.editHomePitch.text.toString()
        editTeam!!.amount = root.editSquadNumber.value
    }
  
    //get the team we want to update on server
    fun updateUserTeam(userId: String, uid: String?, team: TeamModel) {
        app.database.child("user-teams").child(userId).child(uid!!)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.ref.setValue(team)
                                activity!!.supportFragmentManager.beginTransaction()
                                        .replace(R.id.homeFrame, ReportFragment.newInstance())
                                        .addToBackStack(null)
                                        .commit()
                                hideLoader(loader)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                info("Firebase Team error : ${error.message}")
                            }
                        })
    }
    //update not delete
    fun updateTeam(uid: String?, team: TeamModel) {
        app.database.child("teams").child(uid!!)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.ref.setValue(team)
                                hideLoader(loader)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                info("Firebase Team error : ${error.message}")
                            }
                        })
    }

}


