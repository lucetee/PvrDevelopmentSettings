package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class PermissionPromptController extends BaseSwitchController {

    private static final String KEY = "display_permissions_prompt";
    private static final String PROP_PERMS_AUTO_GRANT = "persist.pvrpermission.autogrant";

    public PermissionPromptController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_PERMS_AUTO_GRANT, mPreference.isChecked() ? "0" : "1");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_PERMS_AUTO_GRANT, "0").equals("0");
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
