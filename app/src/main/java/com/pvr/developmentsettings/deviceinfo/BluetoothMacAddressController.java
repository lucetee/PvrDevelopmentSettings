package com.pvr.developmentsettings.deviceinfo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.v7.preference.Preference;
import android.text.TextUtils;

import com.pvr.developmentsettings.BaseController;
import com.pvr.developmentsettings.R;

public class BluetoothMacAddressController extends BaseController {

    private static final String BT_MAC_ADDR_KEY = "bluetooth_mac_address";

    public BluetoothMacAddressController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return BT_MAC_ADDR_KEY;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setSummary(getBluetoothMacAddress());
    }

    /**
     *
     * Bluetooth address is only available for system apps!
     * It requires system uid or LOCAL_MAC_ADDRESS permission which is only granted by system apps!
     * Otherwise you will only get default bt mac address BluetoothAdapter.DEFAULT_MAC_ADDRESS:"02:00:00:00:00:00"
     */
    private String getBluetoothMacAddress() {
        String bluetoothAddress = mContext.getResources().getString(R.string.status_unavailable);
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth != null) {
            String address = bluetooth.isEnabled() ? bluetooth.getAddress() : null;
            if (!TextUtils.isEmpty(address)) {
                // Convert the address to lowercase for consistency with the wifi MAC address.
                bluetoothAddress = address.toLowerCase();
            }
        }
        return bluetoothAddress;
    }
}
