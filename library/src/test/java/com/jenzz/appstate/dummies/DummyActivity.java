package com.jenzz.appstate.dummies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class DummyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this));
    }
}
