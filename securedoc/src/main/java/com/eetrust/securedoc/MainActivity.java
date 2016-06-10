package com.eetrust.securedoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_home);
        tab_layout=(RadioGroup)findViewById(R.id.tab_layout);
        tab_layout.check(R.id.tab_first);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new FirstFragment()).commit();
    }
}
