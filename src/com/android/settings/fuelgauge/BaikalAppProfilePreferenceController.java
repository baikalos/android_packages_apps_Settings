/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.fuelgauge;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.annotation.VisibleForTesting;
import androidx.preference.Preference;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SubSettingLauncher;

import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.crdroid.settings.fragments.AppProfileFragment;

public class BaikalAppProfilePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {

    private static final String TAG = "BAIKALAPPPROFILE_PREF";

    @VisibleForTesting String KEY_BAIKALAPPPROFILE_PREF = "baikal_app_profile_pref";

    public static final String ARG_PACKAGE_NAME = "package";
    public static final String ARG_PACKAGE_UID = "uid";

    private final Context mContext;
    private final String mPackageName;
    private final int mUid;

    public BaikalAppProfilePreferenceController(Context context, int uid, String packageName) {
        super(context);
        mPackageName = packageName; 
        mUid = uid;
        mContext = context;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_BAIKALAPPPROFILE_PREF;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if( getPreferenceKey().equals(preference.getKey()) ) {
            BaikalAppProfilePreferenceController.show(mContext, mUid, mPackageName, SettingsEnums.DIALOG_APP_INFO_ACTION);
            return true;
        }
        return false;
    }

    public static void show(Context context, int uid, String packageName, int sourceMetricsCategory) {
        final Bundle args = new Bundle();
        args.putString(ARG_PACKAGE_NAME, packageName);
        args.putInt(ARG_PACKAGE_UID, uid);
        new SubSettingLauncher(context)
                .setDestination(AppProfileFragment.class.getName())
                .setArguments(args)
                .setTitleText(packageName)
                .setSourceMetricsCategory(sourceMetricsCategory)
                .setUserHandle(UserHandle.getUserHandleForUid(uid))
                .launch();
    }

}
