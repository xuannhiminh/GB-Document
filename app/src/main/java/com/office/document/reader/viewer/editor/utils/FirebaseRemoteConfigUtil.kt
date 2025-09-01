package com.office.document.reader.viewer.editor.utils

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.office.document.reader.viewer.editor.BuildConfig

class FirebaseRemoteConfigUtil private constructor() {

    companion object {
        private const val DEFAULT_FORCE_UPDATE = false
        private const val DEFAULT_MIN_VERSION_CODE = 0
        private const val DEFAULT_LATEST_VERSION = "N/A"
        private const val DEFAULT_UPDATE_USER_COUNT = 10000
        private const val DEFAULT_UPDATE_FEATURES = "✔ Fix minor bugs"
        private const val DEFAULT_VERSION_CODE_REVIEWING = 0
        private const val DEFAULT_FEEDBACK_TYPE = 0
        private const val DEFAULT_NOTIFICATION_FREQUENCY_MINUTES = 60
        private const val DEFAULT_NOTIFICATION_TIME_WINDOW = 0
        private const val DEFAULT_PDF_DETAIL_TYPE = 0
        private const val DEFAULT_REQUEST_FEATURE_SETTING_ON_OFF = false
        private const val DEFAULT_FEEDBACK_SETTING_ON_OFF = false

        private const val REMOTE_KEY_FORCE_UPDATE = "force_update"
        private const val REMOTE_KEY_MIN_VERSION_CODE = "min_version_code"
        private const val REMOTE_KEY_LATEST_VERSION = "latest_version_name"
        private const val REMOTE_KEY_UPDATE_USER_COUNT = "update_user_count"
        private const val REMOTE_KEY_UPDATE_FEATURES = "update_features"
        private const val REMOTE_KEY_VERSION_CODE_REVIEWING = "version_code_reviewing"
        private const val REMOTE_KEY_FEEDBACK_TYPE = "feedback_type"
        private const val REMOTE_KEY_NOTIFICATION_FREQUENCY_MINUTES = "notification_frequency_minutes"
        private const val REMOTE_KEY_NOTIFICATION_FREQUENCY_TIME_WINDOW = "notification__time_window"
        private const val REMOTE_KEY_PDF_DETAIL_TYPE = "pdf_detail_type"
        private const val REMOTE_KEY_REQUEST_FEATURE_SETTING_ON_OFF = "request_feature_setting_on_off"
        private const val REMOTE_KEY_FEEDBACK_SETTING_ON_OFF = "feedback_setting_on_off"

        private var instance: FirebaseRemoteConfigUtil? = null

        @Synchronized
        fun getInstance(): FirebaseRemoteConfigUtil {
            if (instance == null) {
                instance = FirebaseRemoteConfigUtil()
            }
            return instance!!
        }
    }

    private val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // Fetch every hour
            .build()
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        firebaseRemoteConfig.setDefaultsAsync(
            mapOf(
                REMOTE_KEY_FORCE_UPDATE to DEFAULT_FORCE_UPDATE,
                REMOTE_KEY_MIN_VERSION_CODE to DEFAULT_MIN_VERSION_CODE,
                REMOTE_KEY_LATEST_VERSION to DEFAULT_LATEST_VERSION,
                REMOTE_KEY_UPDATE_USER_COUNT to DEFAULT_UPDATE_USER_COUNT,
                REMOTE_KEY_UPDATE_FEATURES to DEFAULT_UPDATE_FEATURES,
                REMOTE_KEY_VERSION_CODE_REVIEWING to DEFAULT_VERSION_CODE_REVIEWING,
                REMOTE_KEY_FEEDBACK_TYPE to DEFAULT_FEEDBACK_TYPE,
                REMOTE_KEY_NOTIFICATION_FREQUENCY_MINUTES to DEFAULT_NOTIFICATION_FREQUENCY_MINUTES,
                REMOTE_KEY_NOTIFICATION_FREQUENCY_TIME_WINDOW to DEFAULT_NOTIFICATION_TIME_WINDOW,
                REMOTE_KEY_PDF_DETAIL_TYPE to DEFAULT_PDF_DETAIL_TYPE,
                REMOTE_KEY_REQUEST_FEATURE_SETTING_ON_OFF to DEFAULT_REQUEST_FEATURE_SETTING_ON_OFF,
                REMOTE_KEY_FEEDBACK_SETTING_ON_OFF to DEFAULT_FEEDBACK_SETTING_ON_OFF
            )
        )
    }

    fun fetchRemoteConfig(onComplete: (Boolean) -> Unit) {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseUtil", "Remote config fetch successful!")
                    onComplete(true)
                } else {
                    Log.e("FirebaseUtil", "Remote config fetch failed!")
                    onComplete(false)
                }
            }
    }

    fun isForceUpdateRequired(): Boolean {
        return firebaseRemoteConfig.getBoolean(REMOTE_KEY_FORCE_UPDATE)
    }

    fun getMinVersionCode(): Int {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_MIN_VERSION_CODE).toInt()
    }

    fun getLatestVersion(): String {
        return firebaseRemoteConfig.getString(REMOTE_KEY_LATEST_VERSION)
    }

    fun getUpdateUserCount(): Number {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_UPDATE_USER_COUNT)
    }

    fun getUpdateFeatures(): String {
        return firebaseRemoteConfig.getString(REMOTE_KEY_UPDATE_FEATURES)
    }

    private fun getVersionCodeReviewing(): Number {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_VERSION_CODE_REVIEWING)
    }

    fun isCurrentVersionUnderReview(): Boolean {
        return BuildConfig.VERSION_CODE == getVersionCodeReviewing()
    }
    fun getFeedbackType(): Number {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_FEEDBACK_TYPE)
    }
    fun getNotificationFrequencyMinutes(): Long {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_NOTIFICATION_FREQUENCY_MINUTES)
    }
    fun getPDFDetailType(): Long {
        return firebaseRemoteConfig.getLong(REMOTE_KEY_PDF_DETAIL_TYPE)
    }
    fun isRequestFeatureSettingOnOff(): Boolean {
        return firebaseRemoteConfig.getBoolean(REMOTE_KEY_REQUEST_FEATURE_SETTING_ON_OFF)
    }
    fun isFeedbackSettingOnOff(): Boolean {
        return firebaseRemoteConfig.getBoolean(REMOTE_KEY_FEEDBACK_SETTING_ON_OFF)
    }
}