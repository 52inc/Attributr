package com.ftinc.attributr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ftinc.attributr.adapters.LibraryListAdapter;
import com.ftinc.attributr.model.Library;

import com.ftinc.attributr.util.Utils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an easy to use class for displaying project dependancies in
 * your Android applications.
 *
 * Created by drew.heavner on 8/21/13.
 */
public class LicenseActivity extends Activity {
    private static final String TAG = "LICENSE_ACTIVITY";

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



    // The List View
    private ListView mList;
    private GridView mGrid;

    private LibraryListAdapter mAdapter;
    private LibraryListAdapter.ILibraryActionListener mActionListener;

    // Library array
    private List<Library> mLibraries = new ArrayList<>();

    // XML Config File
    private int mConfigFile;
    private int mTheme;
    private int mIcon;

    /**************************************************************
     *
     * Lifecycle methods
     *
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Get configuration from Intent
        Bundle xtras = getIntent().getExtras();
        if(xtras != null){
            mConfigFile = xtras.getInt("config", 0);
            mTheme = xtras.getInt("theme", 0);
            mIcon = xtras.getInt("icon", 0);
        }

        if(mTheme != 0){
            setTheme(mTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Load listview
        View container = findViewById(R.id.license_list);
        if(container instanceof ListView){
            mList = (ListView) container;
        }else if(container instanceof GridView){
            mGrid = (GridView) container;
        }

        // Look for saved licences file
        if(savedInstanceState != null){
            int savedConfigId = savedInstanceState.getInt("config_file", -1);
            if(savedConfigId != -1){
                mConfigFile = savedConfigId;
            }
        }

        // Safety
        if(mConfigFile == 0){
            Utils.log(TAG, "No valid config file for the LicenceFragment");
            finish();
            return;
        }

        // Set Icon if available
        if(mIcon != 0){
            getActionBar().setIcon(mIcon);
        }

        // Parse Configuration and create adapter
        mLibraries = parseConfigFile();
        mAdapter = new LibraryListAdapter(this, R.layout.layout_library_item, mLibraries);

        // Create Dummy header/footer views
        if(mList != null) {
            View header = new View(this);
            View footer = new View(this);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            header.setLayoutParams(params);
            footer.setLayoutParams(params);
            mList.addHeaderView(header, null, false);
            mList.addFooterView(footer, null, false);
            mList.setAdapter(mAdapter);
            mList.setOnItemClickListener(mItemClickListener);
        }else if(mGrid != null){
            mGrid.setAdapter(mAdapter);
            mGrid.setOnItemClickListener(mItemClickListener);
        }

        // Set action listener if available
        if(mActionListener != null){
            mAdapter.setActionListener(mActionListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("config_file", mConfigFile);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // Pop the back stack
                finish();
                return true;
        }
        return false;
    }

    /**************************************************************
     *
     * Helper Methods
     *
     */

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Library lib = (Library) adapterView.getItemAtPosition(i);

            Intent link = lib.getSourceIntent();
            startActivity(link);
        }
    };

    /**
     * Parse the XML config file and load the third party library references
     * @return      a list of parsed Library objects
     */
    private List<Library> parseConfigFile(){
        List<Library> libs = new ArrayList<Library>();

        // Load licence information from XML configuration in root application
        try{
            XmlResourceParser parser = getResources().getXml(mConfigFile);

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

    /**
     * Set the action listener for when a user selects the source or license link
     * buttons. This is to allow maximum flexibility so the user of this object can specify
     * what method they want to use to display those weblinks (external, or internally)
     *
     * @param listener      the action listener
     */
    public void setLibraryActionListener(LibraryListAdapter.ILibraryActionListener listener){
        mActionListener = listener;
        if(mAdapter != null){
            mAdapter.setActionListener(mActionListener);
        }
    }

    /**************************************************************
     *
     * Inner Classes and Interfaces
     *
     */


}
