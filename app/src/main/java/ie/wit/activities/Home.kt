package ie.wit.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.AboutUsFragment
import ie.wit.fragments.AddFragment

import ie.wit.fragments.ReportFragment
import ie.wit.fragments.ViewAllFragment
import ie.wit.main.FootballApp
import ie.wit.utils.uploadImageView
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Home : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {


    lateinit var ft: FragmentTransaction
    lateinit var app: FootballApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        app = application as FootballApp
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(
                view, "",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }

        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(app.currentUser.email != null)
            navView.getHeaderView(0).nav_header_email.text = app.currentUser.email
        else
            navView.getHeaderView(0).nav_header_email.text = ""

        if (app.currentUser?.photoUrl != null) {
            navView.getHeaderView(0).nav_header_email.text = app.currentUser?.displayName

            Picasso.get().load(app.currentUser?.photoUrl)
                .resize(180, 180)
                .transform(CropCircleTransformation())
                .into(navView.getHeaderView(0).imageView, object : Callback {
                    override fun onSuccess() {
                        // Drawable is ready
                        uploadImageView(app, navView.getHeaderView(0).imageView)
                    }

                    override fun onError(e: Exception) {}
                }
                )
        }



        ft = supportFragmentManager.beginTransaction()

        val fragment = AddFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()
    }
    //Set what clicking on each fragment does
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_AddTeam -> navigateTo(AddFragment.newInstance())
            R.id.nav_control-> navigateTo(ReportFragment.newInstance())
            R.id.nav_aboutus-> navigateTo(AboutUsFragment.newInstance())
            R.id.nav_list -> navigateTo(ViewAllFragment.newInstance())
            R.id.nav_sign_out -> signOut()

            else -> toast("Coming Soon")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //Back Button
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
    //handaling change of fragment
    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener { startActivity<Login>() }
        finish()
    }
}

