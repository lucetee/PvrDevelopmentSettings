package com.pvr.developmentsettings.deviceinfo;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.preference.Preference;
import android.text.TextUtils;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;

public class WifiMacAddressController extends BaseController {

    private static final String WIFI_MAC_ADDR_KEY = "wifi_mac_address";


    public WifiMacAddressController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return WIFI_MAC_ADDR_KEY;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        String macAddress = getWifiMacAddress();
        preference.setSummary(!TextUtils.isEmpty(macAddress) ? macAddress : mContext.getResources().getString(R.string.status_unavailable));
    }

    private String getWifiMacAddress() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        android.net.wifi.WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String macAddress = wifiInfo == null ? null : wifiInfo.getMacAddress();
        return macAddress;
    }
}
