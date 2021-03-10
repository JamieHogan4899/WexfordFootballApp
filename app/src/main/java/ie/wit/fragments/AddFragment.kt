package ie.wit.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.helpers.readImage
import ie.wit.helpers.showImagePicker
import ie.wit.main.FootballApp
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.nav_header_home.*


class AddFragment : Fragment() {

    lateinit var app: FootballApp
    var teams = TeamModel()
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_add, container, false)
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


    fun setAddButtonListener(layout: View) {
        layout.addBtn.setOnClickListener {

            teams.name = teamName.text.toString()
            teams.location = homePitch.text.toString()
            teams.amount = squadPicker.value


            app.teamsStore.create(TeamModel(name = teams.name, location = teams.location, amount = teams.amount, image =  teams.image))



            //test
            println(teams.id)
            println(teams.name)
            println(teams.amount)


        }
    }
        fun setAddImageListen(layout: View) {
            layout.addLogo.setOnClickListener {
                showImagePicker(this, IMAGE_REQUEST)
            }

        }

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
}

