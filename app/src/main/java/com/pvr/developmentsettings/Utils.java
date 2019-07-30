package com.pvr.developmentsettings;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

    private static final String TAG = "Utils";

    public static void startWithFragment(Context context, String fragmentName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(context, BaseFragmentActivity.class);
        intent.putExtra(Constants.EXTRA_SHOW_FRAGMENT, fragmentName);
        context.startActivity(intent);
    }

    public static String getProperty(String propertyName, String defValue) {
        String result = defValue;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", new Class[] {String.class, String.class});
            Object retVal = method.invoke(null, propertyName, defValue);
            result = String.valueOf(retVal);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException when getProperty!", e);
        } catch (NoSuchMethodException e1) {
            Log.e(TAG, "NoSuchMethodException when getProperty!", e1);
        } catch (InvocationTargetException e2) {
            Log.e(TAG, "InvocationTargetException when getProperty!", e2);
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "IllegalAccessException when getProperty!", e3);
        }
        Log.i(TAG, "getProperty:" + propertyName + " value is:" + result);
        return result;
    }

    public static void setProperty(String propertyName, String value) {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("set", new Class[] {String.class, String.class});
            method.invoke(null, propertyName, value);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException when setProperty!", e);
        } catch (NoSuchMethodException e1) {
            Log.e(TAG, "NoSuchMethodException when setProperty!", e1);
        } catch (InvocationTargetException e2) {
            Log.e(TAG, "InvocationTargetException when setProperty!", e2);
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "IllegalAccessException when setProperty!", e3);
        }
    }

    public static void setPvrManagerSystemFeatrue(Context context, int mode, String value) {
        try {
            Class<?> pvr = Class.forName("com.pvr.PvrManager");
            Method getInstance = pvr.getDeclaredMethod("getInstance", Context.class);
            Object pvrManager = getInstance.invoke(pvr, context);
            Method updateUserSettings = pvrManager.getClass().getDeclaredMethod("setSystemFeatures", int.class, String.class);
            updateUserSettings.invoke(pvrManager, mode, value);
        } catch (Exception e) {
            Log.e(TAG, "Exception when updateUserSettings!", e);
        }
    }

    public static int readFile(String fileName, char[] buf) {
        int length = 0;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        if ((fileName == null) || (buf == null)) {
            return length;
        }

        try {
            fileReader = new FileReader(fileName);

            if (fileReader != null) {
                bufferedReader = new BufferedReader(fileReader);

                if (bufferedReader != null) {
                    length = bufferedReader.read(buf);
                    bufferedReader.close();
                }

                fileReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return length;
    }

    public static boolean writeFile(String path, String content) {
        File file = new File(path);
        if (!file.exists() && mkdir(file.getParentFile())) {
            try {
                boolean success = file.createNewFile() && file.canWrite();
                if (!success) {
                    return false;
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path, false);
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean mkdir(File file) {
        boolean success = false;
        if (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        if (!file.exists()) {
            success = file.mkdir();
        } else {
            success = true;
        }
        return success;
    }
}
