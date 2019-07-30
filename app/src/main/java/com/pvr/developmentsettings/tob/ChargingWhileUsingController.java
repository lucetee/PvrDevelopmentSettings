package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class ChargingWhileUsingController extends BaseSwitchController {

    private static final String KEY = "charging_while_using";
    /**
     * Please see PhoneWindowManager where defines charge policy.
     * For now,there are three policies:
     *     Psensor far-enable,Psensor near-disable
     *     private static final int CHARGE_POLICY_NORMAL = 1;
     *
     *     Psensor near-Battery is less than 40-enable charge limit electricity 500ma,battery is more than 70-disable
     *     Psensor far-no electricity limit
     *     private static final int CHARGE_POLICY_BATTERY = 2;
     *
     *     Always charge at 800ma electricity when psensor is near
     *     private static final int CHARGE_POLICY_ALWAYS = 3;
     */
    private static final String PROP_CHARGE_POLICY = "persist.pvr.charge.policy";

    public ChargingWhileUsingController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            //TODO:The value we set to this property is different according to project.Do we need to change this preference to ListPreference???
            Utils.setProperty(PROP_CHARGE_POLICY, "3");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return !Utils.getProperty(PROP_CHARGE_POLICY, "1").equals("1");
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
