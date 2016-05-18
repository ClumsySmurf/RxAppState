package com.jenzz.appstate;

import android.content.Intent;

import com.jenzz.appstate.dummies.DummyActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import rx.observers.TestSubscriber;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.jenzz.appstate.AppState.BACKGROUND;
import static com.jenzz.appstate.AppState.FOREGROUND;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP)
public class RxAppStateTest {

  private ActivityController<DummyActivity> controller;

  @Test
  public void emitsNothingOnStartup() {
    TestSubscriber<AppState> testSubscriber = new TestSubscriber<>();

    RxAppState.monitor(application).subscribe(testSubscriber);

    testSubscriber.assertNoValues();
    testSubscriber.assertNoTerminalEvent();
  }

  @Test
  public void emitsForegroundOnCreation() {
    TestSubscriber<AppState> testSubscriber = new TestSubscriber<>();

    RxAppState.monitor(application).subscribe(testSubscriber);

    controller = Robolectric.buildActivity(DummyActivity.class)
            .create()
            .start()
            .resume()
            .visible();

    testSubscriber.assertValue(FOREGROUND);
    testSubscriber.assertNoTerminalEvent();
}

  @Test
  public void emitsBackgroundOnPause() {
    TestSubscriber<AppState> testSubscriber = new TestSubscriber<>();

    RxAppState.monitor(application).subscribe(testSubscriber);

    controller = Robolectric.buildActivity(DummyActivity.class)
            .create()
            .start()
            .resume()
            .visible();

    controller.pause().stop();

    testSubscriber.assertValues(FOREGROUND,BACKGROUND);
    testSubscriber.assertNoTerminalEvent();
  }


}