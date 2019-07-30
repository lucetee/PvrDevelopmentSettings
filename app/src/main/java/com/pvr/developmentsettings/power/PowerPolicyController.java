package com.pvr.developmentsettings.power;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;

public abstract class PowerPolicyController extends BaseController {

    private static final String TAG = "ScreenOffDelayController";

    protected static final String PROP_PSENSOR_SLEEP_MODE = "persist.psensor.sleepmode";
    protected static final String PROP_PSENSOR_SYSTEM_SLEEP_DELAY = "persist.psensor.sleep.delay";
    protected static final String PROP_PSENSOR_SCREEN_OFF_DELAY = "persist.psensor.screenoff.delay";
    protected static final String PROP_HMD_SCREEN_OFF_DELAY = "persist.hmd.sleep.delay";
    protected static final int PICOVR_PSENSOR_SLEEP_MODE_HMD = 1;
    protected static final int PICOVR_PSENSOR_SLEEP_MODE_DEFAULT = 2;
    protected static final int PICOVR_PSENSOR_ONLY_SLEEP_MODE = 3;

    protected Preference mPreference;
    String[] mEntries, mEntryValues;
    private AlertDialog mDialog;
    private int mMaxSelectableValue = -1;
    private int mCheckedValue;

    public PowerPolicyController(Context context) {
        super(context);
    }

    protected void setDataSource(String[] entries, String[] entryValues) {
        mEntries = entries;
        mEntryValues = entryValues;
    }

    protected void setDataSource(int entriesResId, int entryValuesResId) {
        mEntries = mContext.getResources().getStringArray(entriesResId);
        mEntryValues = mContext.getResources().getStringArray(entryValuesResId);
    }

    protected void setMaxSelectableValue(int maxSelectableValue) {
        mMaxSelectableValue = maxSelectableValue;
    }

    protected void setCheckedValue(int value) {
        mCheckedValue = value;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            mPreference = screen.findPreference(getPreferenceKey());
            updateSummary();
        }
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (isAvailable() && mPreference != null) {
            updateSummary();
        }
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (getPreferenceKey().equals(preference.getKey())) {
            showSelectionDialog();
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    private void showSelectionDialog() {
        dismissDialogs();
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.pvr_dialog_activity, null);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
        RadioButton[] mRadioButtonArray = new RadioButton[mEntries.length];
        for (int i = 0; i < mEntries.length; i++) {
            mRadioButtonArray[i] = new RadioButton(mContext);
            mRadioButtonArray[i].setText(mEntries[i]);
            mRadioButtonArray[i].setCompoundDrawablePadding(500);
            radioGroup.addView(mRadioButtonArray[i], i);
        }

        if (mMaxSelectableValue != -1) {
            for (int i = 0; i < mEntryValues.length; i++) {
                if (Integer.valueOf(mEntryValues[i]).intValue() > mMaxSelectableValue) {
                    mRadioButtonArray[i].setEnabled(false);
                }
            }
        }

        int checkedIndex = 0;
        for (int i = 0; i < mEntryValues.length; i++) {
            if (mCheckedValue == Integer.valueOf(mEntryValues[i]).intValue()) {
                checkedIndex = i;
            }
        }
        Log.v(TAG, "checkedIndex:" + checkedIndex);
        radioGroup.check(mRadioButtonArray[checkedIndex].getId());
        mDialog = new AlertDialog.Builder(mContext).setTitle(R.string.screen_off_delay_title)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int radioGroupCheckIndex = radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));
                        onDialogConfirmButtonClicked(mEntryValues[radioGroupCheckIndex]);
                        updateSummary();
                        Log.v(TAG, "checked index:" + radioGroupCheckIndex + " value is:" + mEntries[radioGroupCheckIndex]);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                radioGroup.removeAllViews();
            }
        });
    }

    protected void updateSummary() { }

    @Override
    public void dismissDialogs() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public abstract void onDialogConfirmButtonClicked(Object value);
}
