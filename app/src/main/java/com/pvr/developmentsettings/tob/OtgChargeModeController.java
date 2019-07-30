package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class OtgChargeModeController extends BaseSwitchController {

    private static final String KEY = "otg_charge_mode";
    private static final String PROP_OTG_CHARGE_MODE = "persist.pvr.otgmode";

    public OtgChargeModeController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_OTG_CHARGE_MODE, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    public String getPreferenceKey() {
        return KEY;
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_OTG_CHARGE_MODE,"0").equals("1");
    }
}
