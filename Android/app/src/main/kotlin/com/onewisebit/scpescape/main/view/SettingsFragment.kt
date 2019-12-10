package com.onewisebit.scpescape.main.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.onewisebit.scpescape.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //TODO: populate settings
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}