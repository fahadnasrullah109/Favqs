package com.newstore.favqs.integration

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newstore.favqs.R
import com.newstore.favqs.ui.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginSignUpIntegrationTest {

    @Test
    fun testLoginToSignUpNavigation() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Perform actions on the UI using Espresso
        onView(withId(R.id.signup_btn)).perform(click())

        // Verify that the UI reflects the desired state
        onView(withId(R.id.register_text_view)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun testLoginToSignUpNavigationAndNavigateBack() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Perform actions on the UI using Espresso
        onView(withId(R.id.signup_btn)).perform(click())

        // Verify that the UI reflects the desired state
        onView(withId(R.id.register_text_view)).check(matches(isDisplayed()))

        // Go Back
        pressBack()

        // Verify Login shown again
        onView(withId(R.id.login_text_view)).check(matches(isDisplayed()))
        onView(withText(R.string.welcome_message)).check(matches(isDisplayed()))

        activityScenario.close()
    }

}