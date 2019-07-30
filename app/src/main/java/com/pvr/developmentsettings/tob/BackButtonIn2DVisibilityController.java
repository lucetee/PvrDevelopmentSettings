package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class BackButtonIn2DVisibilityController extends BaseSwitchController {

    private static final String KEY = "button2d_back_visible";
    private static final String PROP_BUTTON2D_BACK_VISIBLE = "persist.pvr.2dtovr.button_back";

    public BackButtonIn2DVisibilityController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_BUTTON2D_BACK_VISIBLE, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_BUTTON2D_BACK_VISIBLE,"1").equals("1");
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
