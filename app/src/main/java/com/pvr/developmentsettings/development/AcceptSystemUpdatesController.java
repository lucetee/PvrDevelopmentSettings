package com.pvr.developmentsettings.development;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class AcceptSystemUpdatesController extends BaseSwitchController {

    private static final String TAG = "AcceptSystemUpdatesController";
    private static final String ACCEPT_SYSTEM_UPDATES_KEY = "accept_system_updates";
    private static final String PROP_ACCEPT_SYSTEM_UPDATES = "persist.accept.systemupdates";

    public AcceptSystemUpdatesController(Context context) {
        super(context);
    }

    protected boolean shouldBeChecked() {
        return !Utils.getProperty(PROP_ACCEPT_SYSTEM_UPDATES, "1").equals("0");
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (ACCEPT_SYSTEM_UPDATES_KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_ACCEPT_SYSTEM_UPDATES, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return ACCEPT_SYSTEM_UPDATES_KEY;
    }
}
