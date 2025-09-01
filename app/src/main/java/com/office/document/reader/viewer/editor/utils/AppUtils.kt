package com.office.document.reader.viewer.editor.utils

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import java.util.Currency
import java.util.Locale

class AppUtils {

     companion object {
         fun getCurrencySymbol(currencyCode: String, locale: Locale = Locale.getDefault()): String {
             return Currency.getInstance(currencyCode)
                 .getSymbol(locale)
         }
        fun isWidgetNotAdded(context: Context, widgetClass: Class<*>): Boolean {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, widgetClass))
            return appWidgetIds.isEmpty()
        }
         const val PDF_DETAIL_EZLIB = 0L // 0 mean use EZ lib pdf detail, 1 mean use SoLib PDF detail
         const val FOLDER_EXTERNAL_IN_DOWNLOADS = "AllPDFReaderTripSoft"
         const val PUBLIC_LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk/AMBWMZGL6vDTXt2pfrObQNB0m4vlBQC7kQ0TZRYHHXZLNRLvfIjDLSbK/ETBaRwKGK0/UiAeF6W5OeT35FdRlewxtVoIZdyVW1Wev/kcPAJ1RQ1uJg/omelhCchXctOBoZZ/SqBEye4U+Ll9UA7vyftVDvPdDvQ4GthO+KiuNAvczfp4KwoiSA5nXjXoYA3HoXXdlmeQwClSinoB1J8N8vSB9ybN3MlLyWs6eE+SfwLoK1GsIva5qPS+Ii1ATixLdHpyH0NVRheA302+JPoWSuK8wp35gECmTivajHX+USgWWeZ1I9hSeY4EZrrtoLX4wkv6KPLTa7sQKDM+8gOQIDAQAB"
     }
 }