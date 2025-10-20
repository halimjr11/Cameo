package com.halimjr11.cameo.view.feature.settings

import androidx.core.content.res.ResourcesCompat
import com.halimjr11.cameo.common.Constants.LANG_EN
import com.halimjr11.cameo.common.Constants.LANG_ID
import com.halimjr11.cameo.common.launchAndCollect
import com.halimjr11.cameo.components.BaseFragment
import com.halimjr11.cameo.databinding.FragmentSettingsBinding
import com.halimjr11.cameo.resources.R
import com.halimjr11.cameo.view.feature.settings.di.loadSettingModule
import com.halimjr11.cameo.view.feature.settings.viewmodel.SettingsViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewModel>(FragmentSettingsBinding::inflate),
    AndroidScopeComponent {
    override val viewModel: SettingsViewModel by viewModel()
    override val scope: Scope by fragmentScope()

    init {
        loadSettingModule()
    }

    override fun setupListeners() = with(binding) {
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            switchTheme.thumbIconDrawable = if (isChecked) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_moon, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_sun, null)
            }
            viewModel.toggleDarkMode(isChecked)
        }
        switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            switchLanguage.thumbIconDrawable = if (isChecked) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_indonesia, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_english, null)
            }
            viewModel.toggleLanguage(if (isChecked) LANG_ID else LANG_EN)
        }
        super.setupListeners()
    }

    override fun observeData() = with(viewModel) {
        launchAndCollect(viewModel.isDarkModeEnabled) { isEnabled ->
            binding.run {
                if (switchTheme.isChecked != isEnabled) {
                    switchTheme.isChecked = isEnabled
                }
            }
        }
        launchAndCollect(currentLanguage) {
            binding.run {
                switchLanguage.isChecked = it == LANG_ID
            }
        }
        super.observeData()
    }

}