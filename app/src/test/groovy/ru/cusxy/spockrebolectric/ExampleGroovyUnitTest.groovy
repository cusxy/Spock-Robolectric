package ru.cusxy.spockrobolectric

import android.content.Intent
import android.widget.Button
import androidx.test.filters.SmallTest
import groovy.transform.CompileStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import spock.lang.Specification

@RunWith(RobolectricTestRunner)
@SmallTest
class ExampleGroovyUnitTest extends Specification {

    @Test
    def should_sum() {
        given:
            MainActivity activity

            try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
                controller.setup(); // Moves Activity to RESUMED state
                activity = controller.get();
            }
        when:
            def result = activity.sum(2, 3)
        then:
            result == 5
    }

    @Test
    def clickingLogin_shouldStartLoginActivity() {
        given:
            WelcomeActivity activity
            try (ActivityController<WelcomeActivity> controller = Robolectric.buildActivity(WelcomeActivity.class)) {
                controller.setup(); // Moves Activity to RESUMED state
                activity = controller.get();
            }
        when:
            activity.findViewById(R.id.login).performClick()
            Intent expectedIntent = new Intent(activity, MainActivity.class);
            Intent actual = Shadows.shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        then:
            expectedIntent.getComponent() == actual.getComponent()
    }
}