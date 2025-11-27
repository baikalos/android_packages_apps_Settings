/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.android.settings.applications.appinfo;

import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.PreferenceScreen;

import com.android.settings.SettingsPreferenceFragment;
import ru.baikalos.settings.fragments.BaikalAppProfileFragment;

import com.android.settingslib.applications.AppUtils;

public class BaikalAppProfilePreferenceController extends AppInfoPreferenceControllerBase {

    private static final String TAG = "BaikalAppProfilePreferenceController";

    public BaikalAppProfilePreferenceController(Context context, String key) {
        super(context, key);
        Log.w(TAG, "BaikalAppProfilePreferenceController:ctor");
    }

    @Override
    public int getAvailabilityStatus() {
        Log.w(TAG, "BaikalAppProfilePreferenceController:AVAILABLE");
        return AVAILABLE;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mPreference.setEnabled(AppUtils.isAppInstalled(mAppEntry));
    }

    @Override
    protected Class<? extends SettingsPreferenceFragment> getDetailFragmentClass() {
        Log.w(TAG, "BaikalAppProfilePreferenceController:getDetailFragmentClass");
        return BaikalAppProfileFragment.class;
    }

    @Override
    public CharSequence getSummary() {
        Log.w(TAG, "BaikalAppProfilePreferenceController:getSummary");
        return null; // DrawOverlayDetails.getSummary(mContext, mParent.getAppEntry());
    }
}
