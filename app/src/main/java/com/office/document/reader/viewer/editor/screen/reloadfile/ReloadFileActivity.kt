package com.office.document.reader.viewer.editor.screen.reloadfile

//import com.google.android.gms.ads.ez.EzAdControl
//import com.google.android.gms.ads.ez.listenner.ShowAdCallback
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.ezteam.baseproject.utils.IAPUtils
import com.ezteam.baseproject.utils.SystemUtils
import com.ezteam.baseproject.utils.TemporaryStorage
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.analytics.FirebaseAnalytics
import com.nlbn.ads.callback.AdCallback
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.AppOpenManager
import com.nlbn.ads.util.ConsentHelper
import com.office.document.reader.viewer.editor.R
import com.office.document.reader.viewer.editor.databinding.ActivityReloadFileBinding
import com.office.document.reader.viewer.editor.dialog.SortDialog
import com.office.document.reader.viewer.editor.screen.base.PdfBaseActivity
import com.office.document.reader.viewer.editor.screen.main.MainActivity

class ReloadFileActivity : PdfBaseActivity<ActivityReloadFileBinding>() {
    companion object {
        private const val TAG = "ReloadFileActivity"

        fun start(activity: FragmentActivity) {
            activity.intent.data?.let {
                activity.intent.apply {
                    setClass(activity, ReloadFileActivity::class.java)
                }
                activity.startActivity(activity.intent)
            } ?: kotlin.run {
                val intent = Intent(activity, ReloadFileActivity::class.java).apply {
                }
                activity.startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {

    }

    override fun initData() {

    }
    private fun loadNativeNomedia() {
        if (IAPUtils.isPremium()) {
            binding.layoutNative.visibility = View.GONE
            return
        }
        Log.d("Load Ads", "Start load Ads")
        if (SystemUtils.isInternetAvailable(this)) {
            binding.layoutNative.visibility = View.VISIBLE
            val loadingView = LayoutInflater.from(this)
                .inflate(R.layout.ads_native_bot_loading, null)
            binding.layoutNative.removeAllViews()
            binding.layoutNative.addView(loadingView)

            val callback = object : NativeCallback() {
                override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                    super.onNativeAdLoaded(nativeAd)

                    val layoutRes = R.layout.ads_native_bot
                    val adView = LayoutInflater.from(this@ReloadFileActivity)
                        .inflate(layoutRes, null) as NativeAdView

                    binding.layoutNative.removeAllViews()
                    binding.layoutNative.addView(adView)

                    // Gán dữ liệu quảng cáo vào view
                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView)
                }

                override fun onAdFailedToLoad() {
                    super.onAdFailedToLoad()
                    binding.layoutNative.visibility = View.GONE
                }
            }

            Admob.getInstance().loadNativeAd(
                applicationContext,
                getString(R.string.native_keep_user),
                callback
            )
        } else {
            binding.layoutNative.visibility = View.GONE
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

    }


    override fun viewBinding(): ActivityReloadFileBinding {
        return ActivityReloadFileBinding.inflate(LayoutInflater.from(this))
    }

    override fun onResume() {
        super.onResume()
        if (TemporaryStorage.isLoadAds) {
            loadNativeNomedia()
        } else {
            Log.d("Load Ads", "Not load Ads")
        }
    }

    override fun onStop() {
        super.onStop()
    }

}