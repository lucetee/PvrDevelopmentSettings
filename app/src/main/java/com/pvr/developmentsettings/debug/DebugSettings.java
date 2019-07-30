package com.pvr.developmentsettings.debug;

import android.content.Context;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.BaseControllerFragment;
import com.pvr.developmentsettings.R;

import java.util.ArrayList;
import java.util.List;

public class DebugSettings extends BaseControllerFragment {

    @Override
    protected List<BaseController> getPreferenceControllers(Context context) {
        final List<BaseController> controllers = new ArrayList<>();
        controllers.add(new ShowFpsController(context));
        controllers.add(new SystemStatusController(context));
        controllers.add(new SdkTrackingModeController(context));
        controllers.add(new PvrServiceTrackingModeController(context));
        controllers.add(new DisplayFpsModeController(context));
        return controllers;
    }

    @Override
    protected int getPreferenceScreenResourceId() {
        return R.xml.debug_settings;
    }
}
