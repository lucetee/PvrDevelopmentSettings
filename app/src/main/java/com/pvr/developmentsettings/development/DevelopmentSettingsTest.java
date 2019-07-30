package com.pvr.developmentsettings.development;

import android.content.Context;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.BaseControllerFragment;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.countrycode.CountryOrRegionCodeController;
import com.pvr.developmentsettings.deviceinfo.SoftwareVersionController;
import com.pvr.developmentsettings.power.ScreenOffDelayController;
import com.pvr.developmentsettings.power.SystemSleepDelayController;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentSettingsTest extends BaseControllerFragment {
    @Override
    protected List<BaseController> getPreferenceControllers(Context context) {
        final List<BaseController> controllers = new ArrayList<>();
        controllers.add(new CountryOrRegionCodeController(context));
        controllers.add(new IntelligentAwakenController(context));
        controllers.add(new ScreenOffDelayController(context));
        controllers.add(new SystemSleepDelayController(context));
        controllers.add(new AcceptSystemUpdatesController(context));
        controllers.add(new GeoMagneticCalibrationController(context));
        return controllers;
    }

    @Override
    protected int getPreferenceScreenResourceId() {
        return R.xml.development_settings;
    }
}
