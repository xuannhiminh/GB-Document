package com.office.document.reader.viewer.editor.screen.setting

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.ezteam.baseproject.animation.AnimationUtils
import com.ezteam.baseproject.utils.PreferencesUtils
import com.ezteam.baseproject.utils.TemporaryStorage
import com.google.firebase.analytics.FirebaseAnalytics
import com.office.document.reader.viewer.editor.R
import com.office.document.reader.viewer.editor.databinding.RateUsDialogBinding
import com.office.document.reader.viewer.editor.screen.search.FeedBackActivity

class RateUsDialog : DialogFragment() {
    override fun getTheme(): Int {
        return R.style.DialogStyle
    }
    private var _binding: RateUsDialogBinding? = null
    private val binding get() = _binding!!
    private var selectedRating = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(false)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RateUsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private fun logEvent(firebaseAnalytic: FirebaseAnalytics, event: String) {
        firebaseAnalytic.logEvent(event, Bundle().apply {
            putString("screen", "RateUsDialog")
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRate.isEnabled = false
        binding.btnRate.alpha = 0.5f

        setupStarRating()
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        } catch (e: Exception) {

        }
        binding.btnRate.setOnClickListener {
            if (selectedRating == 0) return@setOnClickListener
            logEvent(firebaseAnalytics, "selected_rating_${selectedRating}")
            if (selectedRating < 5) {
                FeedBackActivity.start(requireActivity())
            }  else {
                PreferencesUtils.putBoolean("SHOW_SATISFIED_DIALOG", false)
                TemporaryStorage.isRateFullStar = true
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=${requireContext().packageName}")
                }
                startActivity(intent)
            }

            dismiss()
        }
    }

    private fun setupStarRating() {
        val stars = listOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )

        stars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectedRating = index + 1
                updateStarUI(selectedRating)

                binding.btnRate.isEnabled = true
                binding.btnRate.alpha = 1f
            }
        }
    }

    private fun updateStarUI(rating: Int) {
        val stars = listOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )

        val bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)

        stars.forEachIndexed { index, imageView ->
            if (index < rating) {
                imageView.setImageResource(R.drawable.icon_favourite)
                imageView.startAnimation(bounce)
            } else {
                imageView.setImageResource(R.drawable.icon_rate)
            }
        }

        when (rating) {
            1 -> {
                binding.emojiHeader.text = "😢"
                updateTexts(R.string.we_sorry, R.string.your_feedback_is_welcome, R.string.send_feedback)
            }
            2 -> {
                binding.emojiHeader.text = "😞"
                updateTexts(R.string.we_sorry, R.string.your_feedback_is_welcome, R.string.send_feedback)
            }
            3 -> {
                binding.emojiHeader.text = "😮"
                updateTexts(R.string.we_sorry, R.string.your_feedback_is_welcome, R.string.send_feedback)
            }
            4 -> {
                binding.emojiHeader.text = "😊"
                updateTexts(R.string.much_appreciated, R.string.your_support_is_motivation, R.string.send_feedback)
            }
            5 -> {
                binding.emojiHeader.text = "🥰"
                updateTexts(R.string.much_appreciated, R.string.your_support_is_motivation, R.string.rate_on_google)
            }
        }
    }

    private fun updateTexts(titleResId: Int, subtitleResId: Int, btnTextResId: Int) {
        binding.titleText.setText(titleResId)
        binding.subtitleText.setText(subtitleResId)
        binding.btnRate.setText(btnTextResId)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
            setDimAmount(0.5f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

