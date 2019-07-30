package com.pvr.developmentsettings.debug;

import android.content.Context;
import android.content.Intent;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class SystemStatusController extends BaseSwitchController {

    private static final String SYSTEM_STATUS_KEY = "pvr_debug_sysstatus";
    private static final String PROP_PVR_DEBUG_SYSSTATUS = "persist.pvr.debug.sysstatus";

    public SystemStatusController(Context context) {
        super(context);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_PVR_DEBUG_SYSSTATUS,"0").equals("1");
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (SYSTEM_STATUS_KEY.equals(preference.getKey())) {
            //mPreference.isChecked returns the state we are going to.It's ok to use here.
            boolean value = mPreference.isChecked();
            Utils.setProperty(PROP_PVR_DEBUG_SYSSTATUS, mPreference.isChecked() ? "1" : "0");
            Intent service = (new Intent()).setClassName("com.android.systemui", "com.android.systemui.LoadPvrSysStatusService");
            if (value) {
                mContext.startService(service);
            } else {
                mContext.stopService(service);
            }
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
        return SYSTEM_STATUS_KEY;
    }
}
