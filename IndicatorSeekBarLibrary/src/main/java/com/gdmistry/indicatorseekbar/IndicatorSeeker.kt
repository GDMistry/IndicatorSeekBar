package com.gdmistry.indicatorseekbar

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.gdmistry.indicatorseekbar.databinding.IndicatorSeekerBinding

class IndicatorSeeker : ConstraintLayout, SeekBar.OnSeekBarChangeListener {
    //Inflate the child actual layout
    private var binding = IndicatorSeekerBinding.inflate(LayoutInflater.from(context), this, true)

    //Set the initial max limit for the seekbar
    private var maxLimit = 100

    //Set initially LTR
    private var isMadeRtl = false

    var callback: Callback? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    interface Callback {
        fun onProgressChanged(seeker: SeekBar?, progress: Int, fromUser: Boolean)
        fun onTrackingStart(seeker: SeekBar?) {}
        fun onTrackingStop(seeker: SeekBar?) {}
    }

    init {
        //Set the initial limit to the seekbar
        binding.seeker.max = maxLimit

        //Set the progress changed listener
        binding.seeker.setOnSeekBarChangeListener(this)
    }

    fun toggleIndicator(show: Boolean) {
        if (show) {
            binding.groupIndicator.visibility = View.VISIBLE
        } else {
            binding.groupIndicator.visibility = View.GONE
        }
    }

    fun hideIndicatorBackground() {
        binding.ivIndicatorStyle.background = null
    }

    fun setIndicatorBackground(backgroundResId: Int) {
        binding.ivIndicatorStyle.background = ContextCompat.getDrawable(context, backgroundResId)
    }

    fun setIndicatorTextAttribs(
        textSize: Float?,
        textColor: Int?,
        typeface: Typeface?
    ) {
        if (textSize != null) {
            if (textSize > 14f) {
                throw IndicatorSeekerException("Cannot set value of the text greater than 14f.")
            }
            binding.tvIndicatorText.textSize = textSize
        }

        if (textColor != null) {
            binding.tvIndicatorText.setTextColor(ContextCompat.getColor(context, textColor))
        }

        if (typeface != null) {
            binding.tvIndicatorText.typeface = typeface
        }
    }

    class IndicatorSeekerException(message: String) : Exception(message)

    /**
     * Set max to the seek bar and update the max limit
     */
    fun setMax(max: Int) {
        maxLimit = max
        binding.seeker.max = max
    }

    /**
     * Makes the whole layout to support RTL
     */
    fun makeRTL() {
        isMadeRtl = true
        binding.seeker.layoutDirection = LAYOUT_DIRECTION_RTL

        //Measured width takes some time, load it when available
        binding.root.post {
            val thumbPos = (binding.root.measuredWidth - binding.ivIndicatorStyle.width)
            animate(binding.ivIndicatorStyle, thumbPos.toFloat())
            animate(binding.tvIndicatorText, thumbPos.toFloat())
        }
    }

    /**
     * Animate the indicator views
     */
    private fun animate(view: View, translateTo: Float) {
        view
            .animate()
            .translationX(translateTo)
            .setDuration(0)
            .start()
    }

    private fun adjustThumbPos(progress: Int): Int {
        return if (isMadeRtl) {
            (binding.root.measuredWidth - binding.ivIndicatorStyle.measuredWidth) * (maxLimit - progress) / maxLimit
        } else {
            (binding.root.measuredWidth - binding.ivIndicatorStyle.measuredWidth) * progress / maxLimit
        }
    }

    override fun onProgressChanged(seeker: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seeker == null) {
            return
        }

        val thumbPos: Int = adjustThumbPos(progress)

        //Do the progress change settings
        binding.tvIndicatorText.text = "${seeker.progress}"
        animate(binding.ivIndicatorStyle, thumbPos.toFloat())
        animate(binding.tvIndicatorText, thumbPos.toFloat())
        callback?.onProgressChanged(seeker, progress, fromUser)
    }

    override fun onStartTrackingTouch(seeker: SeekBar?) {
        callback?.onTrackingStart(seeker)
    }

    override fun onStopTrackingTouch(seeker: SeekBar?) {
        callback?.onTrackingStop(seeker)
    }
}