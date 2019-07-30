package com.pvr.developmentsettings.power;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class SystemSleepDelayController extends PowerPolicyController {

    private static final String TAG = "SystemSleepDelayController";
    private static final String SYSTEM_SLEEP_DELAY_KEY = "system_sleep_delay";

    public SystemSleepDelayController(Context context) {
        super(context);
        setDataSource(R.array.system_sleep_delay_entries, R.array.system_sleep_delay_values);
        setCheckedValue(Integer.valueOf(getSystemSleepTimeout()).intValue());
    }

    @Override
    public void onDialogConfirmButtonClicked(Object value) {
        writeSystemSleepTimeout(value);
        setCheckedValue(Integer.valueOf(String.valueOf(value)).intValue());
    }

    private String getSystemSleepTimeout() {
        return Utils.getProperty(PROP_PSENSOR_SYSTEM_SLEEP_DELAY, "300");
    }

    private void setSystemSleepTimeout(String value) {
        Utils.setProperty(PROP_PSENSOR_SYSTEM_SLEEP_DELAY, value);
    }

    private void writeSystemSleepTimeout(Object value) {
        setSystemSleepTimeout(value.toString());
    }

    protected void updateSummary() {
        Log.v(TAG, "updateSummary...");
        String delay = getSystemSleepTimeout();
        if (!TextUtils.isEmpty(delay)) {
            for (int i = 0; i < mEntryValues.length; i++) {
                if (delay.equalsIgnoreCase(mEntryValues[i])) {
                    mPreference.setSummary(mEntries[i]);
                    return;
                }
            }
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return SYSTEM_SLEEP_DELAY_KEY;
    }
}
