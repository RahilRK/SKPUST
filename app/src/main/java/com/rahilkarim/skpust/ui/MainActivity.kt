package com.rahilkarim.skpust.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var tag = "MainActivity"
    private var activity = this

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var binding: ActivityMainBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        navController = findNavController(R.id.fragment)
        onClick()
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.feedFragment,
            R.id.peopleFragment,
            R.id.menuFragment
        ))
//        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    fun init() {
        globalClass = GlobalClass.getInstance(activity)
        repository = (activity.application as skpustApplication).repository
    }

    fun onClick() {

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

//            globalClass.log(tag, "onDestinationChanged: "+destination.label);
            show_hideHomeViews(destination.label)
            logout(destination.label)
        }
    }

    fun show_hideHomeViews(label: CharSequence?) {

        if(label == resources.getString(R.string.home) ||
            label == resources.getString(R.string.feed) ||
            label == resources.getString(R.string.people) ||
            label == resources.getString(R.string.matrimony)) {

            binding.bottomNavigationView.visibility = View.VISIBLE
            slideUpBottomNav()
        }
        else if(label == resources.getString(R.string.splashscreen)) {

            binding.bottomNavigationView.visibility = View.GONE
        }
        else if(label == resources.getString(R.string.business_detail) ||
            label == resources.getString(R.string.search_people) ||
            label == resources.getString(R.string.search_business) ||
            label == resources.getString(R.string.people_detail) ||
            label == resources.getString(R.string.feed_detail)||
            label == resources.getString(R.string.profile)||
            label == resources.getString(R.string.history)||
            label == resources.getString(R.string.contactus)||
            label == resources.getString(R.string.matrimony_user_detail)||
            label == resources.getString(R.string.send_feedback)) {

            binding.bottomNavigationView.visibility = View.GONE
            slideDownBottomNav()
        }
    }

    fun slideDownBottomNav() {

        binding.bottomNavigationView.clearAnimation();
        binding.bottomNavigationView
            .animate()
            .translationY(binding.bottomNavigationView.height.toFloat())
            .setDuration(500);
    }

    fun slideUpBottomNav() {

        binding.bottomNavigationView.clearAnimation();
        binding.bottomNavigationView
            .animate()
            .translationY(0F)
            .setDuration(500);
    }

    fun logout(label: CharSequence?) {

        if(label == "Menu") {

            globalClass.clearAllPreference()

            lifecycleScope.launch(Dispatchers.IO) {

                val clearDB = async {
                    repository.clearDB()
                }

                clearDB.await()
                finish()
            }

            globalClass.toastshort("Logout successfully")
        }
    }
}