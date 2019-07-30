package com.pvr.developmentsettings.debug;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class PvrServiceTrackingModeController extends BaseController implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "PvrServiceTrackingModeController";
    private static final String KEY = "pvr_service_trackingmode";
    private static final String PROP_PVR_SERVICE_TRACKING_MODE = "persist.pvrservice.trackingmode";

    private ListPreference mPreference;

    public PvrServiceTrackingModeController(Context context) {
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
        try {
            if (mPreference != null) {
                String[] values = mContext.getResources().getStringArray(R.array.pvr_service_trackingmode_values);
                String[] entries = mContext.getResources().getStringArray(R.array.pvr_service_trackingmode_entries);
                int pvrServiceTrackingMode = Integer.valueOf(Utils.getProperty(PROP_PVR_SERVICE_TRACKING_MODE,"0")).intValue();
                mPreference.setValue(values[pvrServiceTrackingMode]);
                mPreference.setSummary(entries[pvrServiceTrackingMode]);
            }
        } catch(Exception e){
            Log.e(TAG, "updatePvrServiceTrackingMode catch exception.", e);
        }
    }

    private void updatePvrServiceTrackingMode(Object mode){
        try {
            Utils.setProperty(PROP_PVR_SERVICE_TRACKING_MODE, mode.toString());
            updateSummary();
        } catch(Exception e){
            Log.e(TAG, "updatePvrServiceTrackingMode set " + mode.toString() + "catch exception.", e);
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
            updatePvrServiceTrackingMode(newValue);
            return true;
        }
        return false;
    }
}
