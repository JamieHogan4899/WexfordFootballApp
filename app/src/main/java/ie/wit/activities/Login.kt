package ie.wit.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import ie.wit.R
import ie.wit.main.FootballApp
import ie.wit.utils.hideLoader
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class Login : AppCompatActivity(), View.OnClickListener, AnkoLogger {

    lateinit var app: FootballApp

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as FootballApp

        checkSignedIn()
    }

    private fun checkSignedIn() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            //get user
            app.currentUser = FirebaseAuth.getInstance().currentUser!!
            //set what database(had to hardcode due to error)
            app.database = FirebaseDatabase.getInstance("https://assignment-2-fe885-default-rtdb.europe-west1.firebasedatabase.app/").reference
            //set storage location
            app.storage = FirebaseStorage.getInstance().reference
            startActivity<Home>()
        }
        else
        // not signed in
            createSignInIntent()
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        val customLayout = AuthMethodPickerLayout
            .Builder(R.layout.login)
            .setGoogleButtonId(R.id.googleSignInButton)
            .setEmailButtonId(R.id.emailSignInButton)
            .setPhoneButtonId(R.id.phoneSignInButton)
            .build()

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false,true) // true,true for Smart Lock
                .setTheme(R.style.FirebaseLoginTheme)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            123)
        // [END auth_fui_create_intent]

    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // if they have to sign in 1st
                app.currentUser = FirebaseAuth.getInstance().currentUser!!
                app.database = FirebaseDatabase.getInstance("https://assignment-2-fe885-default-rtdb.europe-west1.firebasedatabase.app/").reference
                //test what database it using
                info(app.database)
                app.storage = FirebaseStorage.getInstance().reference

                startActivity<Home>()
                // ...
            } else {

                startActivity<Login>()
            }
        }
    }
    // [END auth_fui_result]

    private fun updateUI(user: FirebaseUser?) {
        app.storage = FirebaseStorage.getInstance().reference
        }





    override fun onClick(v: View) { createSignInIntent() }

}
