package com.example.inquallity.findartwork.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.inquallity.findartwork.R;
import com.example.inquallity.findartwork.fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            final Fragment targetFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_content, targetFragment)
                    .commit();
        }
    }
}