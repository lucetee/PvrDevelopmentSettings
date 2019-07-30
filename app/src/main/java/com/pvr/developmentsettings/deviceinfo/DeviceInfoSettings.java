package com.pvr.developmentsettings.deviceinfo;

import android.content.Context;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.BaseControllerFragment;
import com.pvr.developmentsettings.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfoSettings extends BaseControllerFragment {

    private static final String TAG = "DeviceInfoSettings";

    @Override
    protected List<BaseController> getPreferenceControllers(Context context) {
        final List<BaseController> controllers = new ArrayList<>();
        controllers.add(new SoftwareVersionController(context));
        controllers.add(new HardwareVersionController(context));
        controllers.add(new WifiMacAddressController(context));
        controllers.add(new BluetoothMacAddressController(context));
        return controllers;
    }

    @Override
    protected int getPreferenceScreenResourceId() {
        return R.xml.device_info_settings;
    }
}
