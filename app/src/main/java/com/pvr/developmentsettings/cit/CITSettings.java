package com.pvr.developmentsettings.cit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.pvr.developmentsettings.Constants;
import com.pvr.developmentsettings.R;

public class CITSettings extends PreferenceFragment {

    private static final String TAG = "CITSettings";

    private static final String AUTOMATIC_CIT_KEY = "automatic_cit_settings";

    private Preference mAutomaticCITPref;
    private Dialog mAutomaticCITDialog;

    public CITSettings() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setTheme(R.style.AppTheme);
        addPreferencesFromResource(R.xml.cit_settings);

        initPreferences();
    }

    private void initPreferences() {
        mAutomaticCITPref = findPreference(AUTOMATIC_CIT_KEY);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mAutomaticCITPref) {
            Log.i(TAG,"mAutomatedCITKeys");
            if (mAutomaticCITDialog != null) dismissDialogs();
            final EditText passwordET = new EditText(getActivity());
            passwordET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            mAutomaticCITDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.cit_settings_title)
                    .setView(passwordET)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String pwd = passwordET.getText().toString();
                            if(Constants.PICO_CIT_PASSWORD.equals(pwd)) {
                                Log.i(TAG,"start into automatic cit test.");
                                Intent factory = new Intent();
                                factory.setComponent(new ComponentName("com.picovr.factorytest", "com.picovr.factorytest.PicoFactoryTestMainActivity"));
                                factory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(factory);
                            } else {
                                Toast.makeText(getActivity(), R.string.open_automatic_cit_failed, Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void dismissDialogs() {
        if (mAutomaticCITDialog != null) {
            mAutomaticCITDialog.dismiss();
            mAutomaticCITDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        dismissDialogs();
        super.onDestroy();
    }
}
