package com.pvr.developmentsettings.deviceinfo;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.Utils;

public class HardwareVersionController extends BaseController {

    private static final String HARDWARE_VERSION_KEY = "hardware_version";
    private static final String HARDWARE_VERSION_FILE_NAME = "/sys/bus/platform/drivers/hw_version/soc:hw_version/version";

    public HardwareVersionController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return HARDWARE_VERSION_KEY;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(getHardwareVersion());
    }

    private String getHardwareVersion() {
        char[] hardwareVersion = new char[10];
        Utils.readFile(HARDWARE_VERSION_FILE_NAME, hardwareVersion);
        return String.valueOf(hardwareVersion);
    }
}
