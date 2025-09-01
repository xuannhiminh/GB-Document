package com.office.document.reader.viewer.editor.screen.reloadfile


import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import com.ezteam.baseproject.activity.BaseActivity
import com.office.document.reader.viewer.editor.R
import com.office.document.reader.viewer.editor.databinding.ActivityReloadingBinding
import com.office.document.reader.viewer.editor.screen.main.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class ReloadLoadingActivity : BaseActivity<ActivityReloadingBinding>() {
    private val viewModel by inject<MainViewModel>()
    companion object {

        fun start(activity: FragmentActivity) {
            activity.intent.data?.let {
                activity.intent.apply {
                    setClass(activity, ReloadLoadingActivity::class.java)
                }
                activity.startActivity(activity.intent)
            } ?: kotlin.run {
                val intent = Intent(activity, ReloadLoadingActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.animationView.playAnimation()
//        binding.animationView.scaleX = 1.5f
//        binding.animationView.scaleY = 1.5f
        binding.animationView.apply {
            setAnimation(R.raw.loading_file)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        startPercentageCounter()
    }
    private fun startPercentageCounter() {
        ValueAnimator.ofInt(0, 100).apply {
            duration = 5000L
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                binding.percentText.text = "$value%"
            }
            start()
        }
    }


    override fun initData() {
        lifecycleScope.launch {
            if (isAcceptManagerStorage()) {
                viewModel.migrateFileData()
            } else {
                viewModel.addSameFilesInternal()
                Log.w("SplashActivity", "Skipping migration, no storage permission")
            }
            navigateToNextScreen()
        }
    }



    private fun navigateToNextScreen() {
        ReloadFileActivity.start(this@ReloadLoadingActivity)
        finish()
    }

    override fun initListener() {}
    override fun viewBinding(): ActivityReloadingBinding =
        ActivityReloadingBinding.inflate(LayoutInflater.from(this))
}