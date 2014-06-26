package com.ftinc.attributr.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import com.ftinc.attributr.model.Library;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the helper class that will parse the incoming xml configurations
 *
 *
 * Created by drew.heavner on 6/26/14.
 */
public class Parser {
    private static final String TAG = "Parser";

    /**************************************************************
     *
     * Variables
     *
     */

    public static final String LIBRARY_TAG = "Library";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_AUTHOR = "author";
    public static final String ATTR_SOURCE = "source";
    public static final String ATTR_LICENSE = "license";

    /**
     * Parse Configuration file from resources
     *
     * @param ctx               the application context
     * @param configFileResId   the configuration file resource identifier
     * @return                  the list of parsed library items
     */
    public static List<Library> parse(Context ctx, int configFileResId){
        List<Library> libs = new ArrayList<>();

        // Load licence information from XML configuration in root application
        try{
            XmlResourceParser parser = ctx.getResources().getXml(configFileResId);

            // Parse dat shit
            parser.next();
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                // Look for tags
                if(eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase(LIBRARY_TAG)){

                    // Get the name of the library from the attributes
                    String name = parser.getAttributeValue(null, ATTR_NAME);
                    String author = parser.getAttributeValue(null, ATTR_AUTHOR);
                    String source = parser.getAttributeValue(null, ATTR_SOURCE);
                    String license = parser.getAttributeValue(null, ATTR_LICENSE);

                    // Construct library object, and add to return list
                    Library lib = new Library(name, author, source, license);
                    libs.add(lib);
                }
                eventType = parser.next();
            }

        }catch(Resources.NotFoundException e){
            Utils.log(TAG, "ERROR: Config file not found [" + e.getLocalizedMessage() + "]");
        } catch (XmlPullParserException e) {
            Utils.log(TAG, "ERROR: Unable to parse configuration [" + e.getLocalizedMessage() + "]");
        } catch (IOException e) {
            Utils.log(TAG, "ERROR: Unable to load config file [" + e.getLocalizedMessage() + "]");
        }

        return libs;
    }

}
