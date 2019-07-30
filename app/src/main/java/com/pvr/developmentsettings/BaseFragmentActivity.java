package com.pvr.developmentsettings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.util.Log;

public class BaseFragmentActivity extends FragmentActivity implements PreferenceFragment.OnPreferenceStartFragmentCallback {

    private static final String TAG = "BaseFragmentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Who passed EXTRA_SHOW_FRAGMENT to here??See Utils.startWithFragment()
        String fragmentName = getIntent().getStringExtra(Constants.EXTRA_SHOW_FRAGMENT);
        if (fragmentName != null) {
            launchSettingsFragment(fragmentName);
        } else {
            launchSettingsFragment(TopLevelSettings.class.getName());
        }
    }

    public void launchSettingsFragment(String fragmentName) {
        Fragment f = Fragment.instantiate(this, fragmentName);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, f);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        startPreferencePanel(caller, pref.getFragment());
        return true;
    }

    public void startPreferencePanel(PreferenceFragment caller, String fragmentClass) {
        Utils.startWithFragment(this, fragmentClass);
    }
}
