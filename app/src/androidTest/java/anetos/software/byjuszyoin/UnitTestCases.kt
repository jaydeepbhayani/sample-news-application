package anetos.software.byjuszyoin

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import anetos.software.byjuszyoin.ui.home.HomeActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UnitTestCases {
    val TAG = javaClass.simpleName

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("anetos.software.byjuszyoin", appContext.packageName)
    }

    @get:Rule
    val mActivityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testSample() {
        //Thread.sleep(2000)

        if (getRVcount() > 0) {
            Log.e(TAG, getRVcount().toString())
            onView(withId(R.id.rvHeadlines))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        } else {
            Log.e(TAG, getRVcount().toString())
        }
    }

    private fun getRVcount(): Int {
        val recyclerView =
            mActivityRule.activity.findViewById(R.id.rvHeadlines) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }

}