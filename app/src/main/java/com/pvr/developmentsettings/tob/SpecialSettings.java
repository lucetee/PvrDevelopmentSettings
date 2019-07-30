package com.pvr.developmentsettings.tob;

import android.content.Context;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.BaseControllerFragment;
import com.pvr.developmentsettings.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialSettings extends BaseControllerFragment {
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
        return controllers;
    }

    @Override
    protected int getPreferenceScreenResourceId() {
        return R.xml.tob_settings;
    }
}
