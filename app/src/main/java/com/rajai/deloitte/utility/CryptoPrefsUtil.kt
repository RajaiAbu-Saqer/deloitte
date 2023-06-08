package com.rajai.deloitte.utility

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.rajai.deloitte.R
import com.rajai.deloitte.enum.SupportedLanguagesEnum

class CryptoPrefsUtil
//@Inject constructor(@ApplicationContext appContext: Context)
{
    companion object {
        private lateinit var INSTANCE: CryptoPrefsUtil
        val instance: CryptoPrefsUtil
            get() {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = CryptoPrefsUtil()
                }
                return INSTANCE
            }
        private val LANG = Constants.LANG
        private val PERSIST_RAW_KEY_ROOM_DATABASE = Constants.PERSIST_RAW_KEY_ROOM_DATABASE

    }


    private var masterKey = ContextUtil.instance?.applicationContext?.let {
        MasterKey.Builder(
            it, MasterKey.DEFAULT_MASTER_KEY_ALIAS
        ).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    private val prefs = ContextUtil.instance?.applicationContext?.let { applicationContext ->
        masterKey?.let { masterKey ->
            EncryptedSharedPreferences.create(
                applicationContext, applicationContext.getString(R.string.app_name),
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }


    fun setValue(key: String, value: Any?) {
        when (value) {
            is Int -> prefs?.edit()?.putInt(key, value)?.apply()
            is String -> prefs?.edit()?.putString(key, value)?.apply()
            is Boolean -> prefs?.edit()?.putBoolean(key, value)?.apply()
            else -> {
                val gson = Gson()
                val json = gson.toJson(value)
                prefs?.edit()?.putString(key, json)?.apply()
            }
        }
    }

    fun getInt(key: String) = prefs?.getInt(key, 0)
    fun getString(key: String) = prefs?.getString(key, "")
    fun getBoolean(key: String) = prefs?.getBoolean(key, false)
    fun getBoolean(key: String, defaultValue: Boolean) = prefs?.getBoolean(key, defaultValue)
    fun <T> getObject(key: String, classOfT: Class<T>): T? {
        val gson = Gson()
        val json = prefs?.getString(key, "")
        return gson.fromJson(json, classOfT)
    }


    fun getLanguage() =
        if (getString(LANG).isNullOrEmpty() || getString(LANG).equals(SupportedLanguagesEnum.EN.text)) SupportedLanguagesEnum.EN
        else SupportedLanguagesEnum.AR

    fun isEnglish() =
        !getString(LANG).isNullOrEmpty() || getString(LANG).equals(SupportedLanguagesEnum.EN.text)

    fun isArabic() = getString(LANG).equals(SupportedLanguagesEnum.AR.text)
}