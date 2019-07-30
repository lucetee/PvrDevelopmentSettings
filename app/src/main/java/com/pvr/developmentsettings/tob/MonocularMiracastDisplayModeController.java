package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class MonocularMiracastDisplayModeController extends BaseSwitchController {

    private static final String KEY = "monocular_miracast_display_mode";
    private static final String PROP_MONOCULAR_MIRACAST_DISPLAY_MODE = "persist.pvr.mono.castmode";

    public MonocularMiracastDisplayModeController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_MONOCULAR_MIRACAST_DISPLAY_MODE, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_MONOCULAR_MIRACAST_DISPLAY_MODE, "0").equals("1");
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY;
    }
}
