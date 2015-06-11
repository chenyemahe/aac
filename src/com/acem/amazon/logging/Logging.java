package com.acem.amazon.logging;

import android.text.TextUtils;
import android.util.Log;

/**
 * Class to help logging
 */

public final class Logging {
    private static final String TAG = "aac";

    /**
     * Suppress default constructor for non-instantiability
     */
    private Logging() {
        throw new AssertionError();
    }

    public static final void logI(String message) {
        log(Log.INFO, message);
    }

    public static final void logI(String message, Throwable t) {
        log(Log.INFO, message, t);
    }

    public static final void logI(String module, String message) {
        log(Log.INFO, module, message);
    }

    public static final void logI(String module, String message, Throwable t) {
        log(Log.INFO, module, message, t);
    }

    public static final void logD(String message) {
        log(Log.DEBUG, message);
    }

    public static final void logD(String message, Throwable t) {
        log(Log.DEBUG, message, t);
    }

    public static final void logD(String module, String message) {
        log(Log.DEBUG, module, message);
    }

    public static final void logD(String module, String message, Throwable t) {
        log(Log.DEBUG, module, message, t);
    }

    public static final void logE(String message) {
        log(Log.ERROR, message);
    }

    public static final void logE(String message, Throwable t) {
        log(Log.ERROR, message, t);
    }

    public static final void logE(String module, String message) {
        log(Log.ERROR, module, message);
    }

    public static final void logE(String module, String message, Throwable t) {
        log(Log.ERROR, module, message, t);
    }

    public static final void logW(String message) {
        log(Log.WARN, message);
    }

    public static final void logW(String message, Throwable t) {
        log(Log.WARN, message, t);
    }

    public static final void logW(String module, String message) {
        log(Log.WARN, module, message);
    }

    public static final void logW(String module, String message, Throwable t) {
        log(Log.WARN, module, message, t);
    }

    public static final void logV(String message) {
        log(Log.VERBOSE, message);
    }

    public static final void logV(String message, Throwable t) {
        log(Log.VERBOSE, message, t);
    }

    public static final void logV(String module, String message) {
        log(Log.VERBOSE, module, message);
    }

    public static final void logV(String module, String message, Throwable t) {
        log(Log.VERBOSE, module, message, t);
    }

    public static final void log(int level, String message) {
        log(level, null, message, null);
    }

    public static final void log(int level, String message, Throwable t) {
        log(level, null, message, t);
    }

    public static final void log(int level, String module, String message) {
        log(level, module, message, null );
    }

    /**
     * Log messages assuming logging has been enabled
     *
     * @param level
     * @param message
     * @param throwable
     */
    public static final void log(int level, String module, String message, Throwable t) {
        // Log everything for QA build, otherwise suppress VERBOSE
        // and DEBUG. If needed, setprop can be used to log these levels.

        if (!"eng".equals(android.os.Build.TYPE)) {
            // Suppress logging of exceptions
            t = null;

            // By default, isLoggable() fails on VERBOSE and DEBUG levels
            if (!Log.isLoggable(TAG, level)) {
                return;
            }
        }

        if (!TextUtils.isEmpty(module)) {
            message = "[" + module + "] " + message;
        }

        switch (level) {
            case Log.DEBUG:
                Log.d(TAG, message, t);
                break;

            case Log.ERROR:
                Log.e(TAG, message, t);
                break;

            case Log.INFO:
                Log.i(TAG, message, t);
                break;

            case Log.VERBOSE:
                Log.v(TAG, message, t);
                break;

            case Log.WARN:
                Log.w(TAG, message, t);
                break;

            default:
                Log.wtf(TAG, message, t);
        }
    }
}
