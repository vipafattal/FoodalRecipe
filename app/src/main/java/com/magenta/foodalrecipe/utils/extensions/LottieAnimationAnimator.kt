package com.magenta.foodalrecipe.utils.extensions

import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import com.airbnb.lottie.LottieAnimationView
import com.magenta.foodalrecipe.utils.commen.unitFun

/**
 * Created by Abed on 2/26/2018.
 */

fun LottieAnimationView.animatorBuilder(
    start: Float,
    end: Float,
    duration: Long,
    reverse: Boolean = false
): ValueAnimator {

    val animator: ValueAnimator =
        if (reverse)
            ValueAnimator.ofFloat(end, start)
        else
            ValueAnimator.ofFloat(start, end)

    animator.duration = duration
    animator.addUpdateListener {
        progress = animator.animatedValue as Float
    }
    return animator
}

inline fun LottieAnimationView.lottieAnimationControl(
    duration: Long,
    reverse: Boolean,
    crossinline blockOnEnd: unitFun
) {
    animatorBuilder(0f,1f,duration,reverse).apply {
        doOnEnd{
            blockOnEnd()
        }
    }.start()
}

fun LottieAnimationView.lottieAnimationControl(
    duration: Long,
    reverse: Boolean) {
    animatorBuilder(0f,1f,duration,reverse).start()

}


