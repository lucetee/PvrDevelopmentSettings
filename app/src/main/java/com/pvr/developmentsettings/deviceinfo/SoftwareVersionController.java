package com.pvr.developmentsettings.deviceinfo;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.text.TextUtils;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class SoftwareVersionController extends BaseController {

    private static final String SOTFWARE_VERSION_KEY = "software_version";
    private static final String PROP_SOFTWARE_VERSION = "ro.pvr.internal.version";
    private static final String PROP_SOFTWARE_VERSION_SUFFIX = "ro.pvr.version.suffix";

    public SoftwareVersionController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return SOTFWARE_VERSION_KEY;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(getSoftWareVersion());
    }

    private String getSoftWareVersion() {
        String summary = Utils.getProperty(PROP_SOFTWARE_VERSION, mContext.getResources().getString(R.string.device_info_default));
        String suffix = Utils.getProperty(PROP_SOFTWARE_VERSION_SUFFIX, "");
        if(!TextUtils.isEmpty(suffix)) {
            summary = Utils.getProperty(PROP_SOFTWARE_VERSION, mContext.getResources().getString(R.string.device_info_default)) + "(" + suffix + ")";
        }
        return summary;
    }
}
