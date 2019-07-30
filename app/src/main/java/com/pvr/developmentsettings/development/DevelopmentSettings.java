package com.pvr.developmentsettings.development;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.countrycode.CountryOrRegionCodeController;
import com.pvr.developmentsettings.power.ScreenOffDelayController;
import com.pvr.developmentsettings.power.SystemSleepDelayController;

//Seems that we could use DevelopmentSettingsTest instead of this
@Deprecated
public class DevelopmentSettings extends PreferenceFragment {
    private static final String TAG = "DevelopmentSettings";

    private CountryOrRegionCodeController mCountryOrRegionController;
    private IntelligentAwakenController mIntelligentAwakenController;
    private ScreenOffDelayController mScreenOffDelayController;
    private SystemSleepDelayController mSystemSleepDelayController;
    private AcceptSystemUpdatesController mAcceptSystemUpdatesController;
    private GeoMagneticCalibrationController mGeoMagneticCalibrationController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountryOrRegionController = new CountryOrRegionCodeController(getActivity());
        mIntelligentAwakenController = new IntelligentAwakenController(getActivity());
        mScreenOffDelayController = new ScreenOffDelayController(getActivity());
        mSystemSleepDelayController = new SystemSleepDelayController(getActivity());
        mAcceptSystemUpdatesController = new AcceptSystemUpdatesController(getActivity());
        mGeoMagneticCalibrationController = new GeoMagneticCalibrationController(getActivity());

        //onCreatePreferences is called at last line of super.onCreate(),so it is safe here to call displayPreference()
        //to display or remove this preference according to isAvailable()
        mCountryOrRegionController.displayPreference(getPreferenceScreen());
        mIntelligentAwakenController.displayPreference(getPreferenceScreen());
        mScreenOffDelayController.displayPreference(getPreferenceScreen());
        mSystemSleepDelayController.displayPreference(getPreferenceScreen());
        mAcceptSystemUpdatesController.displayPreference(getPreferenceScreen());
        mGeoMagneticCalibrationController.displayPreference(getPreferenceScreen());
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.development_settings);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (mCountryOrRegionController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        if (mIntelligentAwakenController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        if (mScreenOffDelayController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        if (mSystemSleepDelayController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        if (mAcceptSystemUpdatesController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        if (mGeoMagneticCalibrationController.handlePreferenceTreeClick(preference)) {
            return true;
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onDestroy() {
        mCountryOrRegionController.dismissDialogs();
        mIntelligentAwakenController.dismissDialogs();
        mScreenOffDelayController.dismissDialogs();
        mSystemSleepDelayController.dismissDialogs();
        super.onDestroy();
    }
}
