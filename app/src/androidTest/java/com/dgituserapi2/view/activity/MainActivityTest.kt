package com.dgituserapi2.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dgituserapi2.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityTest{
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkLoading(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }
}