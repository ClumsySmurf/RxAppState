package com.jenzz.appstate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import rx.observers.TestSubscriber;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.jenzz.appstate.AppState.BACKGROUND;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP)
public class RxAppStateTest {

  @Test
  public void emitsNothingOnStartup() {
    TestSubscriber<AppState> testSubscriber = new TestSubscriber<>();

    RxAppState.monitor(application).subscribe(testSubscriber);

    testSubscriber.assertNoValues();
    testSubscriber.assertNoTerminalEvent();
  }

}