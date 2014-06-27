/*
 * Copyright (c) 2014 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.attributr;

import android.content.Context;
import android.content.Intent;
import com.ftinc.attributr.model.Library;
import com.ftinc.attributr.util.Parser;

import java.util.List;

/**
 * Use this class to generate all the necessary actions for using this system
 *
 * Created by r0adkll on 6/26/14.
 * Project: Attributr
 * Package: com.ftinc.attributr
 */
public class Attributr {

    /**
     * Open a license activity to display
     * a list of license attributions
     *
     * @param ctx               the context reference
     * @param title             (OPTIONAL) the title of the activity to display in the actionbar
     * @param configResId       the config file id (i.e. R.xml.<config_file>)
     * @param actionBarIcon     (OPTIONAL) the actionbar icon file
     * @param theme             (OPTIONAL) A theme you might want to apply
     *
     * @return                  the compiled intent to launch this activity
     */
    public static void openLicenseActivity(Context ctx, String title, int configResId, int actionBarIcon, int theme){

        // Create base intent for opening the license activity
        Intent intent = new Intent(ctx, LicenseActivity.class);

        // Put extra parameters
        intent.putExtra(LicenseActivity.EXTRA_CONFIG, configResId);

        if(theme != -1 && theme != 0)
            intent.putExtra(LicenseActivity.EXTRA_THEME, theme);

        if(actionBarIcon != -1 && actionBarIcon != 0)
            intent.putExtra(LicenseActivity.EXTRA_ICON, actionBarIcon);

        if(title != null && !title.isEmpty())
            intent.putExtra(LicenseActivity.EXTRA_NAME, title);

        // Launch intent
        ctx.startActivity(intent);
    }

    // TODO: Add method to return a list view of all the license so that the developer can choose how he wants to display the information

    // TODO: Add method to allow developer to customize the look of the data

    /**
     * Parse a configuration file from the '/res/raw/' directory and return its raw
     * object representation in a list
     *
     * @param ctx           the context reference
     * @param config        the configuration file identifier
     * @return              the objectified configuration file
     */
    public static List<Library> parseConfiguration(Context ctx, int config){
        return Parser.parse(ctx, config);
    }

}
