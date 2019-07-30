package com.pvr.developmentsettings.tob;

import android.content.Context;
import android.support.v7.preference.Preference;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

public class EnableWfdSinkDeviceSpeakerController extends BaseSwitchController {

    private static final String KEY = "enable_wfd_sink_device_speaker";
    private static final String PROP_ENABLE_WFD_SINK_DEVICE_SPEAKER = "persist.pvr.outproxy.enable";

    public EnableWfdSinkDeviceSpeakerController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY.equals(preference.getKey())) {
            Utils.setProperty(PROP_ENABLE_WFD_SINK_DEVICE_SPEAKER, mPreference.isChecked() ? "true" : "false");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    protected boolean shouldBeChecked() {
        return Utils.getProperty(PROP_ENABLE_WFD_SINK_DEVICE_SPEAKER, "true").equals("true");
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
