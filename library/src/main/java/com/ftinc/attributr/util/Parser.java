package com.ftinc.attributr.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Xml;

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

    public static final String CONFIG_TAG = "Configuration";
    public static final String LIBRARY_TAG = "Library";

    public static final String NAME_TAG = "Name";
    public static final String AUTHOR_TAG = "Author";
    public static final String SOURCE_TAG = "Source";
    public static final String LICENSE_TAG = "License";

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
            parser.nextTag();

            // Verify start tag
            //parser.require(XmlPullParser.START_TAG, null, CONFIG_TAG);

            // Iterate and parse child nodes
            while(parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                // Get the name of the tag
                String name = parser.getName();
                if(name.equalsIgnoreCase(LIBRARY_TAG)){
                    // parse  info
                    Library lib = readLibrary(parser);
                    libs.add(lib);
                }else{
                    skip(parser);
                }

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

    /**
     * Read a library element
     * @param parser
     * @return
     */
    private static Library readLibrary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, LIBRARY_TAG);

        Library lib = new Library();

        // Parse Children
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            switch (name) {
                case NAME_TAG:
                    lib.name = readSimpleTextContent(NAME_TAG, parser);
                    break;
                case AUTHOR_TAG:
                    lib.author = readSimpleTextContent(AUTHOR_TAG, parser);
                    break;
                case SOURCE_TAG:
                    lib.source = readSimpleTextContent(SOURCE_TAG, parser);
                    break;
                case LICENSE_TAG:
                    lib.licenseText = readSimpleTextContent(LICENSE_TAG, parser);
                    break;
                default:
                    skip(parser);
            }
        }

        return lib;
    }

    /**
     * Skip the current tag in the parser
     *
     * @param parser
     * @throws XmlPullParserException
     * @throws IOException
     */
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /**
     * Read the text content between the start and end tags
     *
     * @param parser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Read and validate simple text content node
     *
     * @param nodeName
     * @param parser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private static String readSimpleTextContent(String nodeName, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, nodeName);
        String textContent = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, nodeName);
        return textContent;
    }
}
