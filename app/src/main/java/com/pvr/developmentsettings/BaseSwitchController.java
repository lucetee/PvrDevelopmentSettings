package com.pvr.developmentsettings;

import android.content.Context;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

public abstract class BaseSwitchController extends BaseController {

    protected SwitchPreference mPreference;


    public BaseSwitchController(Context context) {
        super(context);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            mPreference = (SwitchPreference) screen.findPreference(getPreferenceKey());
            mPreference.setChecked(shouldBeChecked());
        }
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateCheckState();
    }

    protected void updateCheckState() {
        if (isAvailable()) {
            if (mPreference != null) {
                mPreference.setChecked(shouldBeChecked());
            }
        }
    }

    protected abstract boolean shouldBeChecked();
}
