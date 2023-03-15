package com.newstore.favqs.e2e

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newstore.favqs.R
import com.newstore.favqs.ui.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ViewQuotesAfterLogin {

    @Test
    fun loginAndViewHome() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Enter login form data on the UI using Espresso
        onView(ViewMatchers.withId(R.id.email_et)).perform(ViewActions.typeText("test@rayn.com"))
        onView(ViewMatchers.withId(R.id.password_et)).perform(
            ViewActions.typeText("123456789"), closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click())

        Thread.sleep(5000)

        // Verify that the Home screen toolbar is shown
        onView(ViewMatchers.withId(R.id.toolbar)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        activityScenario.close()
    }

    @Test
    fun loginAndViewQuotesListing() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Enter login form data on the UI using Espresso
        onView(ViewMatchers.withId(R.id.email_et)).perform(ViewActions.typeText("test@rayn.com"))
        onView(ViewMatchers.withId(R.id.password_et)).perform(
            ViewActions.typeText("123456789"), closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click())

        Thread.sleep(5000)

        // Verify that the Home screen toolbar is shown
        onView(ViewMatchers.withId(R.id.toolbar)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        onView(ViewMatchers.withContentDescription(R.string.listing)).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.quote_rv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        activityScenario.close()
    }
}