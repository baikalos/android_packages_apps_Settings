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

package com.android.settings.notification.zen;

import static android.provider.Settings.Global.ZEN_MODE;
import static android.provider.Settings.Global.ZEN_MODE_ALARMS;
import static android.provider.Settings.Global.ZEN_MODE_IMPORTANT_INTERRUPTIONS;
import static android.provider.Settings.Global.ZEN_MODE_NO_INTERRUPTIONS;
import static android.provider.Settings.Global.ZEN_MODE_OFF;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.widget.MainSwitchPreference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ReflectionHelpers;

@RunWith(RobolectricTestRunner.class)
public class ZenModeButtonPreferenceControllerTest {

    private ZenModeButtonPreferenceController mController;

    @Mock
    private ZenModeBackend mBackend;
    @Mock
    private NotificationManager mNotificationManager;
    @Mock
    private MainSwitchPreference mMockPref;
    @Mock
    private NotificationManager.Policy mPolicy;
    @Mock
    private PreferenceScreen mPreferenceScreen;
    private ContentResolver mContentResolver;
    private Context mContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        shadowApplication.setSystemService(Context.NOTIFICATION_SERVICE, mNotificationManager);

        mContext = RuntimeEnvironment.application;
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        mController = new ZenModeButtonPreferenceController(mContext, mock(Lifecycle.class),
                mock(FragmentManager.class));
        when(mNotificationManager.getNotificationPolicy()).thenReturn(mPolicy);
        ReflectionHelpers.setField(mController, "mBackend", mBackend);

        when(mPreferenceScreen.findPreference(mController.getPreferenceKey())).thenReturn(
                mMockPref);
        mController.displayPreference(mPreferenceScreen);
    }

    @Test
    public void updateState_TotalSilence() {
        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_NO_INTERRUPTIONS);
        final MainSwitchPreference pref = new MainSwitchPreference(mContext);

        mController.updateState(pref);

        assertThat(pref.isChecked()).isFalse();
    }

    @Test
    public void updateState_AlarmsOnly() {
        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_ALARMS);
        final MainSwitchPreference pref = new MainSwitchPreference(mContext);

        mController.updateState(pref);

        assertThat(pref.isChecked()).isFalse();
    }

    @Test
    public void updateState_Priority() {
        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_IMPORTANT_INTERRUPTIONS);
        final MainSwitchPreference pref = new MainSwitchPreference(mContext);

        mController.updateState(pref);

        assertThat(pref.isChecked()).isFalse();
    }

    @Test
    public void updateState_ZenOff() {
        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_OFF);
        final MainSwitchPreference pref = new MainSwitchPreference(mContext);

        mController.updateState(pref);

        assertThat(pref.isChecked()).isFalse();
    }

    @Test
    public void updateState_otherUserChangedZen() {
        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_OFF);
        final MainSwitchPreference pref = new MainSwitchPreference(mContext);
        mController.updateState(pref);

        assertThat(pref.isChecked()).isFalse();

        Settings.Global.putInt(mContentResolver, ZEN_MODE, ZEN_MODE_IMPORTANT_INTERRUPTIONS);
        final int GUEST_USER_ID = 10;
        mController.mSettingObserver.onChange(false,
                Settings.Global.getUriFor(Settings.Global.ZEN_MODE), GUEST_USER_ID);

        assertThat(pref.isChecked()).isFalse();
    }
}
