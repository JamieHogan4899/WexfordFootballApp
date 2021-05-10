package ie.wit.fragments


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import ie.wit.helpers.readImage
import ie.wit.helpers.showImagePicker
import ie.wit.main.FootballApp
import ie.wit.models.TeamModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class AddFragment : Fragment(),AnkoLogger {

    lateinit var app: FootballApp
    var teams = TeamModel()
    val IMAGE_REQUEST = 1
    lateinit var loader : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp


    }

    //set Values for Number Picker, what fragment view to open and listen for buttons
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_add, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_add)

        root.squadPicker.minValue = 11
        root.squadPicker.maxValue = 28

        setAddButtonListener(root)
        setAddImageListen(root)


        return root;


    }


    companion object {
        @JvmStatic
        fun newInstance() =
                AddFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

    //add button action
    fun setAddButtonListener(layout: View) {
        layout.addBtn.setOnClickListener {
            //get whats in the filed and set it as Team
            teams.name = teamName.text.toString()
            teams.location = homePitch.text.toString()
            teams.amount = squadPicker.value
            //teams.image = addLogo.toString() //causing images not to display, path or read issue?

            //data validation
            if (teams.name.isEmpty()|| teams.location.isEmpty()) {
                Toast.makeText(getActivity(),"Please check all fields are filled in", Toast.LENGTH_SHORT).show();
                //toast("please add a Team Name")
            } else {

                //creates and adds it into the array
                //app.teamsStore.create(TeamModel(name = teams.name, location = teams.location, amount = teams.amount, image =



                //addTeam
               writeNewTeam(TeamModel(name = teams.name, location = teams.location, amount = teams.amount, image = teams.image,
                    email = app.auth.currentUser?.email))



                //test
                println(teams.uid)
                println(teams.name)
                println(teams.amount)
            }

        }
    }

    //handle add crest button
    fun setAddImageListen(layout: View) {
        layout.addLogo.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    //take in the image and get the path
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    teams.image = data.getData().toString()
                    teamLogo.setImageBitmap(readImage(this, resultCode, data))

                }
            }


        }

    }




    fun writeNewTeam(team: TeamModel) {

        showLoader(loader, "Adding Team to Firebase")
        info("Firebase DB Reference " + app.database)
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("teams").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        team.uid = key
        val teamValues = team.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/teams/$key"] = teamValues
        childUpdates["/user-teams/$uid/$key"] = teamValues
        Log.d (TAG, childUpdates.toString())


                app.database.updateChildren(childUpdates)
        hideLoader(loader)

        println(childUpdates)
    }




}

