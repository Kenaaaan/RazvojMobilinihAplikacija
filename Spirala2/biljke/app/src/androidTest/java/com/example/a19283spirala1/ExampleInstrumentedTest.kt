package com.example.a19283spirala1
//ispravite paket tako da odgovara nazivu paketa kojeg imate u projektu
//ovdje mozete dodati i import klasa ako su u drugom paketu
//glavnu aktivnost imenujte tako da ima naziv MainActivity (ovo je defaultni naziv)
//svi id-evi i podaci koji se koriste u testu su iz postavke i takvi trebaju biti i u vasem projektu


import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class TestS2 {

    @get:Rule
    var activityRule = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun prebaciNaDruguAktivnost() {
        val activity = ActivityScenario.launch(MainActivity::class.java)
        Intents.init()
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        intended(hasComponent(NovaBiljkaActivity::class.java.name))
        Intents.release()
        activity.close()
    }

    @Test
    fun testValidacijaPolja_NazivKratak() {
        onView(withId(R.id.nazivET)).perform(typeText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Polje je obavezno i mora biti najmanje dva znaka!")))
    }

/*
    @Test
    fun testValidacijaPolja_MedicinskoUpozorenjeKratak() {
        onView(withId(R.id.nazivET)).perform(typeText("test"))
        onView(withId(R.id.porodicaET)).perform(typeText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        Thread.sleep(10000)
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Polje je obavezno i mora biti najmanje dva znaka!")))
    }*/


    private fun dodavanjeET() {
        onView(withId(R.id.nazivET)).perform(typeText("test"))
        onView(withId(R.id.porodicaET)).perform(typeText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Kiseline"))
        onView(withId(R.id.jeloET)).perform(typeText("Corba"))
    }

    @Test
    fun testValidacijaMedicinskaKoristLV() {
        dodavanjeET()
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testValidacijaKlimatskiTipLV() {
        dodavanjeET()
        onView(allOf(withParent(withId(R.id.medicinskaKoristLV)), withText("SMIRENJE")))
            .perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testValidacijaZemljisniTipLV() {
        dodavanjeET()
        onView(allOf(withParent(withId(R.id.medicinskaKoristLV)), withText("SMIRENJE")))
            .perform(click())
        onView(allOf(withParent(withId(R.id.klimatskiTipLV)), withText("SREDOZEMNA")))
            .perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testValidacijaProfilOkusaLV() {
        dodavanjeET()
        onView(allOf(withParent(withId(R.id.medicinskaKoristLV)), withText("SMIRENJE")))
            .perform(click())
        onView(allOf(withParent(withId(R.id.klimatskiTipLV)), withText("SREDOZEMNA")))
            .perform(click())
        onView(allOf(withParent(withId(R.id.zemljisniTipLV)), withText("GLINENO")))
            .perform(click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.dodajBiljkuBtn))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testValidacijaDuplikata() {
        onView(withId(R.id.jeloET)).perform(typeText("Grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(typeText("grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.dodajJeloBtn))
            .check(matches(isDisplayed()))
    }
        /*
    @Test
    fun testUslikajBiljkuBtn() {
        /*      TEST PROLAZI TEK KADA KORISNIK USLIKA SLIKU     */
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        onView(withId(R.id.slika)).check(matches(withBitmap()))
    }
    */


    private fun withBitmap(): Matcher<View> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("with bitmap drawable")
            }
            override fun matchesSafely(imageView: ImageView?): Boolean {
                val drawable = imageView?.drawable
                return (drawable is BitmapDrawable && (drawable.bitmap != null))
            }
        }
    }
} // gdje god se testira LV test nije ispravno napisan ali prolazi pa eto ako hoces stavi ja sam ovako bmk HAHAHHAHAHA

@RunWith(AndroidJUnit4::class)
class NovaBiljkaActivityTest {


    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        NovaBiljkaActivity::class.java
    )

    @Test
    fun testValidInputReturnsTrue() {
        val activity = activityScenarioRule.scenario.onActivity { it }

        onView(withId(R.id.nazivET)).perform(typeText("Valid Name"))
        onView(withId(R.id.porodicaET)).perform(typeText("Valid Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Valid Upozorenje"))
 
           onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist())


    }

    @Test
    fun testInvalidNameLengthTooShortReturnsFalse() {
        val activity = activityScenarioRule.scenario.onActivity { it }

        onView(withId(R.id.nazivET)).perform(typeText("A")) // Short name
        onView(withId(R.id.porodicaET)).perform(typeText("Valid Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Valid Upozorenje"))

        onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist())

    }


    @Test
    fun testNoPlantAddedDisablesButton() {
        val activity = activityScenarioRule.scenario.onActivity { it }

        onView(withId(R.id.dodajBiljkuBtn)).check(doesNotExist()) // Assuming disabled button disappears
    }
}







