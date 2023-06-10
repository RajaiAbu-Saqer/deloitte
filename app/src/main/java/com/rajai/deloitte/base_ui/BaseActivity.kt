package com.rajai.deloitte.base_ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rajai.deloitte.R
import com.rajai.deloitte.databinding.ActivityMainBinding
import com.rajai.deloitte.dialog.ProgressBarLoading
import com.rajai.deloitte.enum.SupportedLanguagesEnum
import com.rajai.deloitte.ui.home.MoreFragment
import com.rajai.deloitte.ui.home.dashboard.DashboardFragment
import com.rajai.deloitte.utility.Constants
import com.rajai.deloitte.utility.CryptoPrefsUtil
import java.util.*

open class BaseActivity : AppCompatActivity() {
    private val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
    private val progressBarLoading by lazy { ProgressBarLoading(this) }
    protected lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_more
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.hide()
        mainBinding.navView.setupWithNavController(navController)
        setDirection()
    }

    private fun setDirection() {
        val direction = when (CryptoPrefsUtil.instance.getLanguage()) {
            SupportedLanguagesEnum.AR -> View.LAYOUT_DIRECTION_RTL
            else -> View.LAYOUT_DIRECTION_LTR
        }
        window.decorView.layoutDirection = direction
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            super.attachBaseContext(getLocalizedConfiguration(it))
        }
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.let {
            val uiMode = it.uiMode
            it.setTo(baseContext.resources.configuration)
            it.uiMode = uiMode
        }

        super.applyOverrideConfiguration(overrideConfiguration)
    }

    private fun getLocalizedConfiguration(context: Context): Context {
        val res = context.resources
        val configuration = res.configuration
        val newLocale = Locale(
            CryptoPrefsUtil.instance.getLanguage().text
        )
        configuration.setLocale(newLocale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale)
            val localeList = LocaleList(newLocale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            context.createConfigurationContext(configuration)
        } else context.createConfigurationContext(configuration)
    }

    fun showProgressLoading() {
        progressBarLoading.show()
    }

    fun hideProgressLoading() {
        progressBarLoading.dismiss()
    }

    fun setLanguage(languageEnum: SupportedLanguagesEnum) {
        CryptoPrefsUtil.instance.setValue(
            Constants.LANG, languageEnum.text
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.flip_in_left, R.anim.flip_in_right)
    }

    private fun getCurrentFragment() =
        supportFragmentManager.fragments[0].childFragmentManager.fragments[0]

    fun isMainFragments() =
        getCurrentFragment() is DashboardFragment || getCurrentFragment() is MoreFragment

    override fun onBackPressed() {
        if (isMainFragments()) moveTaskToBack(true)
        else onBackPressedDispatcher.onBackPressed()
    }

    private fun getStackCount() = navHostFragment?.childFragmentManager?.backStackEntryCount ?: 0
}