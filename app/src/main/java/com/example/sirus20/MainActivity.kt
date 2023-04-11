package com.example.sirus20

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.sirus20.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    /*
    * variables
    * */
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pNavController: NavController
    private lateinit var pDrawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        setUpNavigation()

    }

    /*
        * handling onBackPress() method with drawer
        * */
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    pDrawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    when (pNavController.currentDestination?.id) {
                        R.id.dashBoardFragment -> {
                            showAppClosingDialog()
                        }
                        R.id.onBoardingFragment -> {
                            showAppClosingDialog()
                        }
                        R.id.introductionFragment2 -> {
                            showAppClosingDialog()
                        }
                        R.id.loginFragment -> {
                            pNavController.navigateUp()
                        }
                        else -> {
                            pNavController.navigateUp()
                        }
                    }
                }
            }
        }


    /*
   * close app dialog
   * */
    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.app_name)
            .setMessage("Do you really want to close the app?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    /*
    * set up navigation graph and nav controller
    * */
    private fun setUpNavigation() {
        pDrawerLayout = binding.drawerLayout
        val pNavigationView = binding.navView
        pNavController = findNavController(R.id.nav_host_fragment)
        /*
        * setOf() contains top level destination
        * */
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.dashBoardFragment), pDrawerLayout)
        pNavigationView.setupWithNavController(pNavController)
        pNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    hideDrawer()
                }
                R.id.onBoardingFragment -> {
                    hideDrawer()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.on_board_back)
                }

                R.id.loginFragment -> {
                    hideDrawer()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_yellow)
                }
                R.id.signUPFragment -> {
                    hideDrawer()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_yellow)
                }

                R.id.dashBoardFragment -> {
                    showDrawer()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                }
                else -> {
                    hideDrawer()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                }

            }
        }
    }

    //close drawer(unlock)
    private fun hideDrawer() {
        pDrawerLayout.closeDrawer(GravityCompat.START)
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    //open drawer (lock)
    private fun showDrawer() {
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            pDrawerLayout.openDrawer(GravityCompat.START)
            animateDrawer()

        } else {
            pDrawerLayout.closeDrawer(GravityCompat.START)
            //statusBar()
            animateDrawer()
        }
    }

    /*
    * handle the back navigation
    * if appBarConfiguration has id
    * else
    * direct back navigate
    * */
    override fun onSupportNavigateUp(): Boolean {
        return pNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun animateDrawer() {
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val endScale = 0.7F
                // Scale the View based on current slide offset
                val diffScaledOffset: Float = slideOffset * (1 - endScale)
                val offsetScale: Float = 1 - diffScaledOffset
                binding.inFragment.scaleX = offsetScale
                binding.inFragment.scaleY = offsetScale
                // Translate the View, accounting for the scaled width
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = binding.inFragment.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                binding.inFragment.translationX = xTranslation
            }
        })
    }

}