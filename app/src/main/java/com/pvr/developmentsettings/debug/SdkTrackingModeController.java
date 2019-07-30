package com.pvr.developmentsettings.debug;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class SdkTrackingModeController extends BaseController implements Preference.OnPreferenceChangeListener {

    private static final String KEY = "pvr_sdk_trackingmode";
    private static final String TAG = "SdkTrackingModeController";
    private static final String PROP_PVR_SDK_TRACKING_MODE = "persist.pvr.sdk.trackingmode";

    private ListPreference mPreference;

    public SdkTrackingModeController(Context context) {
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
                String[] values = mContext.getResources().getStringArray(R.array.pvr_sdk_trackingmode_values);
                String[] entries = mContext.getResources().getStringArray(R.array.pvr_sdk_trackingmode_entries);
                int pvrTrackingMode = Integer.valueOf(Utils.getProperty(PROP_PVR_SDK_TRACKING_MODE,"0")).intValue();
                mPreference.setValue(values[pvrTrackingMode]);
                mPreference.setSummary(entries[pvrTrackingMode]);
            }
        } catch(Exception e){
            Log.e(TAG, "updatePvrSdkTrackingMode catch exception.", e);
        }
    }

    private void updatePvrSdkTrackingMode(Object mode){
        try {
            Utils.setPvrManagerSystemFeatrue(mContext,6, mode.toString());
            updateSummary();
        } catch(Exception e){
            Log.e(TAG, "updatePvrSdkTrackingMode set " + mode.toString() + "catch exception.", e);
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
            updatePvrSdkTrackingMode(newValue);
            return true;
        }
        return false;
    }
}
