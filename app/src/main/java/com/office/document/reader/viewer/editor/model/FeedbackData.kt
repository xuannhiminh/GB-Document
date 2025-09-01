package com.office.document.reader.viewer.editor.model

import android.os.Build
import androidx.annotation.Keep
import com.ezteam.baseproject.utils.IAPUtils
import com.office.document.reader.viewer.editor.BuildConfig
import java.util.Locale

@Keep
data class FeedbackData(
    val versionCode: Int = BuildConfig.VERSION_CODE,
    val message: String = "",
    val problem: String = "",

    val osApiLevel: Int = Build.VERSION.SDK_INT,
    val deviceModel: String = Build.MODEL,
    val locale: String = Locale.getDefault().toString(),

    val timestamp: Long = System.currentTimeMillis(),

    val isPremium: Boolean = IAPUtils.isPremium(),
    val type: String = "feedback"
)