package com.pvr.developmentsettings.power;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class ScreenOffDelayController extends PowerPolicyController {

    private static final String TAG = "ScreenOffDelayController";
    private static final String SCREEN_OFF_DELAY_KEY = "screen_off_delay";

    public ScreenOffDelayController(Context context) {
        super(context);
        setDataSource(R.array.screen_off_delay_entries, R.array.screen_off_delay_values);
        setMaxSelectableValue(Integer.valueOf(getSystemSleepTimeout()).intValue());
        setCheckedValue(Integer.valueOf(getScreenOffTimeout()).intValue());
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (SCREEN_OFF_DELAY_KEY.equals(preference.getKey())) {
            setMaxSelectableValue(Integer.valueOf(getSystemSleepTimeout()).intValue());
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    public void onDialogConfirmButtonClicked(Object value) {
        writeScreenOffTimeout(value);
        setCheckedValue(Integer.valueOf(String.valueOf(value)).intValue());
    }


    private void writeScreenOffTimeout(Object value) {
        setScreenOffTimeout(value.toString());
    }


    private void setScreenOffTimeout(String value) {
        int sleepMode = Integer.valueOf(Utils.getProperty(PROP_PSENSOR_SLEEP_MODE, "-1")).intValue();
        if (sleepMode == PICOVR_PSENSOR_SLEEP_MODE_DEFAULT || sleepMode == PICOVR_PSENSOR_ONLY_SLEEP_MODE) {
            Utils.setProperty(PROP_PSENSOR_SCREEN_OFF_DELAY, value);
        } else if (sleepMode == PICOVR_PSENSOR_SLEEP_MODE_HMD) {
            Utils.setProperty(PROP_HMD_SCREEN_OFF_DELAY, value);
        }
    }

    @Override
    protected void updateSummary() {
        Log.v(TAG, "updateSummary...");
        String delay = getScreenOffTimeout();
        if (!TextUtils.isEmpty(delay)) {
            for (int i = 0; i < mEntryValues.length; i++) {
                if (delay.equalsIgnoreCase(mEntryValues[i])) {
                    mPreference.setSummary(mEntries[i]);
                    return;
                }
            }
        }
    }

    private String getScreenOffTimeout() {
        int sleepMode = Integer.valueOf(Utils.getProperty(PROP_PSENSOR_SLEEP_MODE, "-1")).intValue();
        if (sleepMode == PICOVR_PSENSOR_SLEEP_MODE_DEFAULT || sleepMode == PICOVR_PSENSOR_ONLY_SLEEP_MODE) {
            return Utils.getProperty(PROP_PSENSOR_SCREEN_OFF_DELAY, "10");
        } else if (sleepMode == PICOVR_PSENSOR_SLEEP_MODE_HMD) {
            return Utils.getProperty(PROP_HMD_SCREEN_OFF_DELAY, "0");
        } else {
            return "0";
        }
    }


    private String getSystemSleepTimeout() {
        return Utils.getProperty(PROP_PSENSOR_SYSTEM_SLEEP_DELAY, "300");
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return SCREEN_OFF_DELAY_KEY;
    }
}
