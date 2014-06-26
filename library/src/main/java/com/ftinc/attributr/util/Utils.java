package com.ftinc.attributr.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/**
 * This is the standard utils file containing common util functions
 * throughout my applications.
 * 
 * @author drew.heavner
 *
 */
public class Utils {
	
	/**
	 * Constants
	 */

	/*
	 * This is the 'DEBUG' flag that signifies whether this application 
	 * is in development mode, or production mode
	 */
	public static boolean DEBUG = false;
	

	/**
	 * Turn on debugging
	 * @param value
	 */
	public static void setDebug(boolean value){
		DEBUG = value;
	}


    /**
     * Return whether or not this device is a tablet or a phone (2)
     * This one is slightly more efficient than the first one
     * by using the same comparison as the resource buckets for determining
     * which layouts/resources to grab
     *
     * @param ctx       the application context
     * @return          true if tablet, false if phone
     */
    public static boolean isTablet(Context ctx){
        if(ctx == null) return false;

        // Check to see if it is a large display
        boolean isLargeDisplay = (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if(isLargeDisplay){
            // get Display Metrics
            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();

            // Compute widthDP
            float widthDP = metrics.widthPixels / metrics.density;

            // If widthDp is > 600dp, then the device is a tablet
            if(widthDP >= 600){
                return true;
            }
        }

        return false;
    }


    /**
     * Convert Density Independent Pixels to Pixels
     *
     * @param ctx   the application context
     * @param dp    the dp unit to convert
     * @return      the px amount for dp
     */
    public static float dpToPx(Context ctx, float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getResources().getDisplayMetrics());
    }

    /**
     * Convert Scale Pixels (used for text) to screen pixels
     *
     * @param ctx   the application context
     * @param sp    the sp unit to convert
     * @return      the px amount for sp
     */
    public static float spToPx(Context ctx, float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, ctx.getResources().getDisplayMetrics());
    }


	/**
	 * Easy method for Logging 'DEBUG' messages
	 * @param msg		the message
	 * @param tag		the identifing tag
	 */
	public static void log(String tag, String msg){
		log(Log.DEBUG, tag, msg);
	}
	
	/**
	 * Easy method for Logging 'INFO' messages
	 * @param tag		the message
	 * @param msg		the identifing tag
	 */
	public static void logi(String tag, String msg){
		log(Log.INFO, tag, msg);
	}
	
	/**
	 * Easy method for Logging 'ERROR' messages
	 * @param tag		the message
	 * @param msg		the identifing tag
	 */
	public static void loge(String tag, String msg){
		log(Log.ERROR, tag, msg);
	}
	
	/**
	 * Easy method for Logging 'WARN' messages
	 * @param tag		the message
	 * @param msg		the identifing tag
	 */
	public static void logw(String tag, String msg){
		log(Log.WARN, tag, msg);
	}

    /**
     * Easy method for Loggin 'WTF' messages
     * @param tag
     * @param msg
     */
    public static void logwtf(String tag, String msg){
        if(DEBUG)
            Log.wtf(tag, msg);
    }
	
	/**
	 * Easy Wrapper to printing to android Log
	 * @param priority		the message priority
	 * @param msg			the message
	 * @param tag			the identifing tag
	 */
	public static void log(int priority, String tag, String msg){
		if(DEBUG)
			Log.println(priority, tag, msg);
	}

}
