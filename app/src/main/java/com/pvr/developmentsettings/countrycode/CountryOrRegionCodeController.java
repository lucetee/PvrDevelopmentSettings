package com.pvr.developmentsettings.countrycode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.Constants;
import com.pvr.developmentsettings.R;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

public class CountryOrRegionCodeController extends BaseController {

    private static final String TAG = "CountryOrRegionCodeController";
    private static final String COUNTRY_REGION_CODE_KEY = "country_region_code";

    private AlertDialog mPicoSetCountryCodeDialog, mPicoCountryCodeChooseDialog;
    private ArrayAdapter mCountryCodeAdapter;
    private Preference mPreference;

    public CountryOrRegionCodeController(Context context) {
        super(context);
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(Settings.Global.getString(mContext.getContentResolver(), Constants.COUNTRY_CODE_SETTINGS_INITIALIZED));
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            mPreference = screen.findPreference(COUNTRY_REGION_CODE_KEY);
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return COUNTRY_REGION_CODE_KEY;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(COUNTRY_REGION_CODE_KEY, preference.getKey())) {
            showPasswordInputDialog();
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    private void showPasswordInputDialog() {
        dismissDialogs();
        final EditText passwordET = new EditText(mContext);
        passwordET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        passwordET.setTextColor(Color.BLACK);
        mPicoSetCountryCodeDialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.country_region_code_dialog_title)
                .setView(passwordET)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pwd = passwordET.getText().toString();
                        if(Constants.PICO_CIT_PASSWORD.equals(pwd)) {
                            setCountryCodeDialogShow();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void setCountryCodeDialogShow() {
        LinearLayout dialogView = new LinearLayout(mContext);
        dialogView.setOrientation(LinearLayout.VERTICAL);
        EditText countrycodeET = new EditText(mContext);
        countrycodeET.setTextColor(Color.BLACK);
        countrycodeET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        countrycodeET.addTextChangedListener(mCountryCodeTextWatcher);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogView.addView(countrycodeET, param);
        ListView countrycodeListView = new ListView(mContext);
        String[] isoCountries = Locale.getISOCountries();
        String[] countryCodeList = Arrays.copyOf(isoCountries, isoCountries.length + 1);
        countryCodeList[countryCodeList.length - 1] = "EU";
        mCountryCodeAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, countryCodeList);
        countrycodeListView.setAdapter(mCountryCodeAdapter);
        countrycodeListView.setOnItemClickListener(onCDItemClickListener);
        dialogView.addView(countrycodeListView, param);
        mPicoCountryCodeChooseDialog = new AlertDialog.Builder(mContext).setTitle(R.string.country_region_code_title).setView(dialogView).show();
    }

    private TextWatcher mCountryCodeTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mCountryCodeAdapter != null) {
                mCountryCodeAdapter.getFilter().filter(s);
            }
        }
    };

    private AdapterView.OnItemClickListener onCDItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView text = view.findViewById(android.R.id.text1);
            if (text != null) {
                mPicoCountryCodeChooseDialog.dismiss();
                Log.i(TAG, "Country Code : " + "position : " + position + " text : " + text.getText().toString());
                updateUserSettings(text.getText().toString());
                String currentCountryCode = Settings.Global.getString(mContext.getContentResolver(), Constants.COUNTRY_CODE_SETTINGS_INITIALIZED);
                if (mPreference != null) mPreference.setSummary(currentCountryCode);
            }
        }
    };

    private void updateUserSettings(String countryOrRegionCode) {
        try {
            Class<?> pvr = Class.forName("com.pvr.PvrManager");
            Method getInstance = pvr.getDeclaredMethod("getInstance", Context.class);
            Object pvrManager = getInstance.invoke(pvr, mContext);
            Method updateUserSettings = pvrManager.getClass().getDeclaredMethod("updateUserSettings", String.class);
            updateUserSettings.invoke(pvrManager, countryOrRegionCode);
        } catch (Exception e) {
            Log.e(TAG, "Exception when updateUserSettings!", e);
        }
    }

    @Override
    public void dismissDialogs() {
        if (mPicoSetCountryCodeDialog != null) {
            mPicoSetCountryCodeDialog.dismiss();
            mPicoSetCountryCodeDialog = null;
        }
        if (mPicoCountryCodeChooseDialog != null) {
            mPicoCountryCodeChooseDialog.dismiss();
            mPicoCountryCodeChooseDialog = null;
        }
    }
}
