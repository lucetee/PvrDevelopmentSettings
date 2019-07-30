package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class CombinationKeyController extends BaseSwitchController {


    private static final String KEY = "mul_key_action";
    private static final String PROP_Mul_KEY_ACTION = "persist.pvr.mulkey.enable";


    public CombinationKeyController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_Mul_KEY_ACTION, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_Mul_KEY_ACTION,"1").equals("1");
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
