package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import ie.wit.R
import ie.wit.main.FootballApp
import ie.wit.models.TeamModel
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import org.jetbrains.anko.toast


class AddFragment : Fragment() {

    lateinit var app: FootballApp
    var teams = TeamModel()


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





            //test
            println(teams.name)
            println(teams.location)
            println(teams.amount)

        }
    }
}
