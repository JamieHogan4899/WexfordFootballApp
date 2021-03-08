package ie.wit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.R
import ie.wit.main.FootballApp


class EditDetailsFragment : Fragment() {

    lateinit var app: FootballApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FootballApp


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_editdetails, container, false)










        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                EditDetailsFragment().apply {
                    arguments = Bundle().apply { }
                }
    }

}