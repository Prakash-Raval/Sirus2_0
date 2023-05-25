package com.example.sirus20

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginScreen {

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Test
    fun checkValidation() {
        /*
        calling activity scenario and launch method
        * */

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
        Thread.sleep(5000)
        /*
        calling skip button from onBoarding fragment
        */
        onView(withId(R.id.skip_btn)).perform(click())
        /*
         * login button click from introduction screen
         * */
        Thread.sleep(2000)
        onView(withId(R.id.btnLogin)).perform(click())
        /*
         * filling edittext for email and password empty
         * */
        onView(withId(R.id.edtSignUpName)).perform(typeText(""))
        onView(withId(R.id.edtLoginPassword)).perform(typeText(""))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnLoginPage)).perform(click())
        /*
         * filling edittext for email and password incorrect
         * */
        onView(withId(R.id.edtSignUpName)).perform(clearText(), typeText("fgh.com"))
        onView(withId(R.id.edtLoginPassword)).perform(clearText(), typeText("123"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnLoginPage)).perform(click())
        /*
        * filling edittext for email and password invalid
        */
        onView(withId(R.id.edtSignUpName)).perform(clearText(), typeText("abc123@gmail.com"))
        onView(withId(R.id.edtLoginPassword)).perform(clearText(), typeText("brain123"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnLoginPage)).perform(click())
        Thread.sleep(4000)
        /*
         * filling edittext for email and password valid
         */
        onView(withId(R.id.edtSignUpName)).perform(clearText(), typeText("v1@gmail.com"))
        onView(withId(R.id.edtLoginPassword)).perform(clearText(), typeText("brain@123"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnLoginPage)).perform(click())

    }
}
