package com.pvr.developmentsettings.debug;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class ShowFpsController extends BaseSwitchController {

    private static final String SHOW_FPS_KEY = "pvr_debug_appfps";
    private static final String PROP_PVR_DEBUG_APPFPS = "persist.pvr.debug.appfps";

    public ShowFpsController(Context context) {
        super(context);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_PVR_DEBUG_APPFPS,"0").equals("1");
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (SHOW_FPS_KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_PVR_DEBUG_APPFPS, mPreference.isChecked() ? "1" : "0");
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
        return SHOW_FPS_KEY;
    }
}
