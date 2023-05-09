package com.example.fireb

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fireb.Models.WeatherModel
import com.example.fireb.Utilities.ApiUtilities
import com.example.fireb.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miProfile -> {
                    val intent = Intent(applicationContext, Profile::class.java)
                    startActivity(intent)
                }

                R.id.miPayment -> {
                    val intent = Intent(applicationContext, Payment::class.java)
                    startActivity(intent)
                }

                R.id.miNews -> {
                    val intent = Intent(this, NewsUpdate::class.java)
                    startActivity(intent)
                }

                R.id.miChat -> {
                    val intent = Intent(applicationContext, Chat::class.java)
                    startActivity(intent)
                }

                R.id.miEmergency -> {
                    val intent = Intent(applicationContext, Instruction::class.java)
                    startActivity(intent)
                }

                R.id.emergencyCall -> {
                    val intent = Intent(applicationContext, Emergency_Call::class.java)
                    startActivity(intent)
                }

                R.id.miSignOut -> {
                    auth.signOut()
                    startActivity(Intent(applicationContext, SignUp::class.java))
                }
            }
            true
        }
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onContextItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            super.onBackPressed()
            return
        } else {
            toast = Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT)
            toast.show()
        }
        backPressedTime = System.currentTimeMillis()

    }


}
