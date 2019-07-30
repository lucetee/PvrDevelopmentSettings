package com.pvr.developmentsettings.development;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.PowerManager;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.Utils;

public class IntelligentAwakenController extends BaseController implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private static final String TAG = "IntelligentAwakenController";
    private static final String INTELLIGENT_AWAKEN_KEY = "wake_up_mode";
    private static final String PROP_WAKE_UP_MODE = "persist.psensor.sleepmode";

    private AlertDialog mWakeUpDialog;

    private Preference mPreference;

    public IntelligentAwakenController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (INTELLIGENT_AWAKEN_KEY.equals(preference.getKey())) {
            showConfirmationDialog();
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            mPreference = (SwitchPreference) screen.findPreference(INTELLIGENT_AWAKEN_KEY);
            ((SwitchPreference) mPreference).setChecked(isIntelligentAwakenEnabled());
        }
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        updateCheckState(isIntelligentAwakenEnabled());
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return INTELLIGENT_AWAKEN_KEY;
    }

    private boolean isIntelligentAwakenEnabled() {
        return Utils.getProperty(PROP_WAKE_UP_MODE, "2").equals("2");
    }

    private void updateCheckState(boolean enabled) {
        if (isAvailable()) {
            if (mPreference != null) {
                ((SwitchPreference) mPreference).setChecked(enabled);
            }
        }
    }

    private void showConfirmationDialog() {
        if (mWakeUpDialog != null) dismissDialogs();
        mWakeUpDialog = new AlertDialog.Builder(mContext).setMessage(
                mContext.getResources().getString(R.string.pico_intelligent_awaken_warning_message))
                .setTitle(R.string.pico_intelligent_awaken_warning_title)
                .setPositiveButton(android.R.string.yes, this)
                .setNegativeButton(android.R.string.no, this)
                .show();
        mWakeUpDialog.setOnDismissListener(this);
    }

    @Override
    public void dismissDialogs() {
        if (mWakeUpDialog != null) {
            mWakeUpDialog.dismiss();
            mWakeUpDialog = null;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialog == mWakeUpDialog) {
            String oldValue = Utils.getProperty(PROP_WAKE_UP_MODE, "2");
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Utils.setProperty(PROP_WAKE_UP_MODE, oldValue.equals("2") ? "3" : "2");
                try {
                    PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
                    powerManager.reboot("reboot from switch wakeup mode");
                } catch (Exception e) {
                    Utils.setProperty(PROP_WAKE_UP_MODE, oldValue);
                    Log.i(TAG, "reboot from switch wakeup mode exception "+ e);
                }
            } else {
                updateCheckState(isIntelligentAwakenEnabled());
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        updateCheckState(isIntelligentAwakenEnabled());
        mWakeUpDialog = null;
    }
}
