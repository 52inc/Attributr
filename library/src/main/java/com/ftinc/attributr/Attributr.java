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

/**
 * Use this class to generate all the necessary actions for using this system
 *
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
     * @param configResId       the config file id (i.e. R.xml.<config_file>)
     * @param actionBarIcon     the actionbar icon file
     * @param theme             A theme you might want to apply
     *
     * @return                  the compiled intent to launch this activity
     */
    public static void openLicenseActivity(Context ctx, int configResId, int actionBarIcon, int theme){

        // Create base intent for opening the license activity
        Intent intent = new Intent(ctx, LicenseActivity.class);

        // Put extra parameters
        intent.putExtra(LicenseActivity.EXTRA_CONFIG, configResId);
        intent.putExtra(LicenseActivity.EXTRA_THEME, theme);
        intent.putExtra(LicenseActivity.EXTRA_ICON, actionBarIcon);

        // Launch intent
        ctx.startActivity(intent);
    }

}
