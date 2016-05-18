package com.jenzz.appstate.internal;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.support.annotation.NonNull;
import com.jenzz.appstate.AppState;
import com.jenzz.appstate.AppStateListener;
import com.jenzz.appstate.internal.adapters.ActivityLifecycleCallbacksAdapter;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.jenzz.appstate.AppState.BACKGROUND;
import static com.jenzz.appstate.AppState.FOREGROUND;

public final class AppStateRecognizer {

  private final ActivityLifecycleCallbacks activityStartedCallback = new ActivityCallback();

  private AppState appState = BACKGROUND;
  private AppStateListener appStateListener;
  private int activeActivities;

  public void start(@NonNull Application app, @NonNull AppStateListener appStateListener) {
    this.appStateListener = appStateListener;

    app.registerActivityLifecycleCallbacks(activityStartedCallback);
  }

  public void stop(@NonNull Application app) {
    app.unregisterActivityLifecycleCallbacks(activityStartedCallback);
  }

  @NonNull
  public AppState getAppState() {
    return appState;
  }

  private class ActivityCallback extends ActivityLifecycleCallbacksAdapter {

    @Override
    public void onActivityStarted(Activity activity) {
      super.onActivityStarted(activity);
      activeActivities++;
      updateState();
    }

    @Override
    public void onActivityStopped(Activity activity) {
      super.onActivityStopped(activity);
      activeActivities--;
      updateState();
    }

    private void updateState(){
      if (activeActivities > 0 && appState != FOREGROUND){
        appState = FOREGROUND;
        appStateListener.onAppDidEnterForeground();
      } else if (activeActivities == 0) {
        appState = BACKGROUND;
        appStateListener.onAppDidEnterBackground();
      }
    }
  }
}
