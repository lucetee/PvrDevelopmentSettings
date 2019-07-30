package com.pvr.developmentsettings.development;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.util.Log;

import com.pvr.developmentsettings.BaseSwitchController;
import com.pvr.developmentsettings.Utils;

import java.io.File;

public class GeoMagneticCalibrationController extends BaseSwitchController {

    private static final String TAG = "GeoMagneticCalibrationController";
    private static final String GEO_MAGNETIC_CALIBRATION_KEY = "geo_magnetic_calibration";
    private static final String GEO_MAGNETIC_CALIBRATION_SWITCH_FILE_NAME = "/persist/Geomagnetic_Switch.txt";

    public GeoMagneticCalibrationController(Context context) {
        super(context);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (GEO_MAGNETIC_CALIBRATION_KEY.equals(preference.getKey())) {
            Utils.writeFile(GEO_MAGNETIC_CALIBRATION_SWITCH_FILE_NAME, mPreference.isChecked() ? "1" : "0");
            return true;
        }
        return super.handlePreferenceTreeClick(preference);
    }

    protected boolean shouldBeChecked() {
        String geoMagneticCalibrationOn = "1";//Default on
        File file = new File(GEO_MAGNETIC_CALIBRATION_SWITCH_FILE_NAME);
        if (file.exists() && file.canRead()) {
            char[] result = geoMagneticCalibrationOn.toCharArray();
            Utils.readFile(GEO_MAGNETIC_CALIBRATION_SWITCH_FILE_NAME, result);
            geoMagneticCalibrationOn = String.valueOf(result);
            Log.v(TAG, "geoMagneticCalibrationOn read from file:" + String.valueOf(geoMagneticCalibrationOn));
        } else {
            Log.v(TAG, "File:" + GEO_MAGNETIC_CALIBRATION_SWITCH_FILE_NAME + " does not exist,geo magnetic calibration considered to be on");
        }
        return geoMagneticCalibrationOn.equals("1");
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return GEO_MAGNETIC_CALIBRATION_KEY;
    }
}
