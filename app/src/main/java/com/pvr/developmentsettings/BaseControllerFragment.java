package com.pvr.developmentsettings;

import android.content.Context;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseControllerFragment extends PreferenceFragment {

    private static final String TAG = "BaseControllerFragment";

    private final Map<Class, BaseController> mPreferenceControllers = new ArrayMap<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        List<BaseController> controllers = getPreferenceControllers(context);
        for (BaseController controller : controllers) {
            mPreferenceControllers.put(controller.getClass(), controller);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePreferenceStates();
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePreferenceStates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (BaseController controller : mPreferenceControllers.values()) {
            controller.dismissDialogs();
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        Collection<BaseController> controllers = mPreferenceControllers.values();
        for (BaseController controller : controllers) {
            if (controller.handlePreferenceTreeClick(preference)) {
                return true;
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    protected void updatePreferenceStates() {
        Collection<BaseController> controllers = mPreferenceControllers.values();
        final PreferenceScreen screen = getPreferenceScreen();
        for (BaseController controller : controllers) {
            if (!controller.isAvailable()) {
                continue;
            }
            final String key = controller.getPreferenceKey();

            final Preference preference = screen.findPreference(key);
            if (preference == null) {
                Log.d(TAG, String.format("Cannot find preference with key %s in Controller %s",
                        key, controller.getClass().getSimpleName()));
                continue;
            }
            controller.updateState(preference);
        }
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        refreshAllPreferences();
    }

    private void refreshAllPreferences() {
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().removeAll();
        }
        reloadPreferencesFromResource();
    }

    private void reloadPreferencesFromResource() {
        final int resId = getPreferenceScreenResourceId();
        if (resId <= 0) {
            Log.w(TAG, "Could not load preferences from resourceId:" + resId);
            return;
        }
        addPreferencesFromResource(resId);
        final PreferenceScreen screen = getPreferenceScreen();
        Collection<BaseController> controllers = mPreferenceControllers.values();
        for (BaseController controller : controllers) {
            controller.displayPreference(screen);
        }
    }

    protected abstract List<BaseController> getPreferenceControllers(Context context);
    protected abstract int getPreferenceScreenResourceId();
}
