package com.pvr.developmentsettings.commonlyused;

import android.content.Context;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.BaseControllerFragment;
import com.pvr.developmentsettings.R;
import com.pvr.developmentsettings.countrycode.CountryOrRegionCodeController;
import com.pvr.developmentsettings.development.AcceptSystemUpdatesController;
import com.pvr.developmentsettings.development.GeoMagneticCalibrationController;
import com.pvr.developmentsettings.development.IntelligentAwakenController;
import com.pvr.developmentsettings.power.ScreenOffDelayController;
import com.pvr.developmentsettings.power.SystemSleepDelayController;
import com.pvr.developmentsettings.tob.AutoRecenterModeController;
import com.pvr.developmentsettings.tob.AutoSleepModeController;
import com.pvr.developmentsettings.tob.BackButtonIn2DVisibilityController;
import com.pvr.developmentsettings.tob.CalibrationPromptWhenBootUpController;
import com.pvr.developmentsettings.tob.ChargingWhileUsingController;
import com.pvr.developmentsettings.tob.CombinationKeyController;
import com.pvr.developmentsettings.tob.EnableWfdSinkDeviceSpeakerController;
import com.pvr.developmentsettings.tob.MonocularMiracastDisplayModeController;
import com.pvr.developmentsettings.tob.OtgChargeModeController;
import com.pvr.developmentsettings.tob.PermissionPromptController;
import com.pvr.developmentsettings.tob.UsbBootModeController;

import java.util.ArrayList;
import java.util.List;

public class CommonlyUsedSettings extends BaseControllerFragment {
    @Override
    protected List<BaseController> getPreferenceControllers(Context context) {
        final List<BaseController> controllers = new ArrayList<>();
        controllers.add(new CombinationKeyController(context));
        controllers.add(new BackButtonIn2DVisibilityController(context));
        controllers.add(new CalibrationPromptWhenBootUpController(context));
        controllers.add(new UsbBootModeController(context));
        controllers.add(new OtgChargeModeController(context));
        controllers.add(new AutoRecenterModeController(context));
        controllers.add(new AutoSleepModeController(context));
        controllers.add(new ChargingWhileUsingController(context));
        controllers.add(new EnableWfdSinkDeviceSpeakerController(context));
        controllers.add(new PermissionPromptController(context));
        controllers.add(new MonocularMiracastDisplayModeController(context));
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
        return R.xml.commonly_used_settings;
    }
}
