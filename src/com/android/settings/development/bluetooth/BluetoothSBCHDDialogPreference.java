/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.android.settings.development.bluetooth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.android.settings.R;

/**
 * Dialog preference to set the Bluetooth A2DP config of SBCHD quality
 */
public class BluetoothSBCHDDialogPreference extends BaseBluetoothDialogPreference implements
        RadioGroup.OnCheckedChangeListener {

    public BluetoothSBCHDDialogPreference(Context context) {
        super(context);
        initialize(context);
    }

    public BluetoothSBCHDDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public BluetoothSBCHDDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public BluetoothSBCHDDialogPreference(Context context, AttributeSet attrs, int defStyleAttr,
                                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    @Override
    protected int getRadioButtonGroupId() {
        return R.id.bluetooth_audio_sbchd_radio_group;
    }

    @Override
    protected int getDefaultIndex() {
        return 0;
    }

    private void initialize(Context context) {
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_default);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_1);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_2);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_3);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_4);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_5);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_6);
        mRadioButtonIds.add(R.id.bluetooth_audio_sbchd_quality_7);
        String[] stringArray = context.getResources().getStringArray(
                com.android.settingslib.R.array.bluetooth_a2dp_codec_sbchd_playback_bitrate_titles);
        for (int i = 0; i < stringArray.length; i++) {
            mRadioButtonStrings.add(stringArray[i]);
        }
        stringArray = context.getResources().getStringArray(
                com.android.settingslib.R
                        .array.bluetooth_a2dp_codec_sbchd_playback_bitrate_summaries);
        for (int i = 0; i < stringArray.length; i++) {
            mSummaryStrings.add(stringArray[i]);
        }
    }
}
