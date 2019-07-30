package com.pvr.developmentsettings.debug;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.Utils;

public class DisplayFpsModeController extends BaseController implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "DisplayFpsModeController";
    private static final String KEY = "pvr_display_fps_mode";
    private static String PROP_PVR_DISPLAY_FPS_MODE_OLD = "persist.pvr.displayfps";
    private static String PROP_PVR_DISPLAY_FPS_MODE_NEW = "pvr.display.type";
    private static String PROP_PVR_DISPLAY_FPS_MODE = "1".equals(Utils.getProperty("ro.pvr.display.switchmode", "0")) ? PROP_PVR_DISPLAY_FPS_MODE_NEW : PROP_PVR_DISPLAY_FPS_MODE_OLD;

    private ListPreference mPreference;

    public DisplayFpsModeController(Context context) {
        super(context);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            mPreference = (ListPreference)screen.findPreference(KEY);
        }
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateSummary();
    }

    private void updateSummary() {
        if (mPreference != null) {
            String pvrDisplayFpsMode = Utils.getProperty(PROP_PVR_DISPLAY_FPS_MODE, null);
            mPreference.setValue(pvrDisplayFpsMode);
            mPreference.setSummary(pvrDisplayFpsMode);
        }
    }

    private void updatePvrDisplayFPSMode(Object mode) {
        try {
            Utils.setProperty(PROP_PVR_DISPLAY_FPS_MODE, mode.toString());
            updateSummary();
        } catch(Exception e){
            Log.e(TAG, "updatePvrDisplayFPSMode catch exception.", e);
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (KEY.equals(preference.getKey())) {
            updatePvrDisplayFPSMode(newValue);
        }
        return false;
    }
}
