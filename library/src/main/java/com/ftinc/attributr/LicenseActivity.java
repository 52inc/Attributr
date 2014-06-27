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

import com.ftinc.attributr.util.Parser;
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

    public static final String EXTRA_CONFIG = "config";
    public static final String EXTRA_THEME = "theme";
    public static final String EXTRA_ICON = "icon";


    // The List View
    private ListView mList;

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
        super.onCreate(savedInstanceState);

        // Get configuration extras from the intent
        Bundle xtras = getIntent().getExtras();
        if(xtras != null){
            mConfigFile = xtras.getInt(EXTRA_CONFIG, 0);
            mTheme = xtras.getInt(EXTRA_THEME, 0);
            mIcon = xtras.getInt(EXTRA_ICON, 0);
        }

        // Check for a theme, and apply if available
        if(mTheme != 0){
            setTheme(mTheme);
        }

        // Set the content view of this activity
        setContentView(R.layout.activity_license);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Load listview or grid
        mList = (ListView) findViewById(R.id.license_list);

        // Look for saved licences file
        if(savedInstanceState != null){
            int savedConfigId = savedInstanceState.getInt("config_file", -1);
            if(savedConfigId != -1){
                mConfigFile = savedConfigId;
            }
        }

        // Ensure that a config file was passed along, quitting if there is wasn't
        if(mConfigFile == 0){
            Utils.log(TAG, "No valid config file for the LicenseActivity");
            finish();
            return;
        }

        // Set Icon if available
        if(mIcon != 0){
            getActionBar().setIcon(mIcon);
        }

        // Parse Configuration and create adapter
        mLibraries = Parser.parse(this, mConfigFile);
        mAdapter = new LibraryListAdapter(this, R.layout.layout_library_item, mLibraries);

        // Create Dummy footer views
        View footer = new View(this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        footer.setLayoutParams(params);
        mList.addFooterView(footer, null, false);

        // Set adapter and click listeners
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

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

    /**
     * Library list item click listener to launch the source webpage to view the library
     * source
     */
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Library lib = (Library) adapterView.getItemAtPosition(i);

            Intent link = lib.getSourceIntent();
            startActivity(link);
        }
    };

}
