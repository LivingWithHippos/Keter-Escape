package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //TODO: populate settings
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}