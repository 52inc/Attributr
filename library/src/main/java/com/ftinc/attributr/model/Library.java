package com.ftinc.attributr.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Third party library representation class.
 * This contains all the information for specific library
 */
public class Library {
    /**
     * Variables
     */

    public String name;
    public String author;
    public String source;
    public String licenseText;

    /**
     * Empty Constructor
     */
    public Library(){}

    /**
     * String res constructor
     *
     * @param ctx               application context
     * @param nameRes           library name res id
     * @param authorRes         author name res id
     * @param sourceRes           library link res id
     * @param licenText         library license text to display
     */
    public Library(Context ctx, int nameRes, int authorRes, int sourceRes, int licenText){
        name = ctx.getString(nameRes);
        author = ctx.getString(authorRes);
        source = ctx.getString(sourceRes);
        licenseText = ctx.getString(licenText);
    }

    /**
     * Plain string constructor
     *
     * @param name          the name of the library
     * @param author        the author of the library
     * @param source        the source link to the library
     * @param licenText     the license text
     */
    public Library(String name, String author, String source, String licenText){
        this.name = name;
        this.author = author;
        this.source = source;
        this.licenseText = licenText;
    }

    /**
     * Get an intent to look at the source
     *
     * @return
     */
    public Intent getSourceIntent(){
        return openLink(this.source);
    }

    /**
     * Get a human-readable representation of this
     * class
     * @return  the class in string form
     */
    @Override
    public String toString() {
        return "[" + name + ":" + author + "] source[" + source + "] license[" + licenseText + "]";
    }

    /**
     * Generate an intent for a given URL to launch the user into the browser
     * with the given url
     *
     * @param url       the url to link to
     * @return          the intent to launch the action
     */
    private static Intent openLink(String url){
        // if protocol isn't defined use http by default
        if (!TextUtils.isEmpty(url) && !url.contains("://")) {
            url = "http://" + url;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

}