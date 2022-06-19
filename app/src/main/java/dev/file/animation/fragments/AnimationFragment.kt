package dev.file.animation.fragments

import android.animation.ArgbEvaluator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import dev.file.animation.R
import dev.file.animation.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment(R.layout.fragment_animation) {

    private var fragmentAnimationBinding: FragmentAnimationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_up)
        exitTransition = inflater.inflateTransition(R.transition.side_down)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAnimationBinding.bind(view)
        fragmentAnimationBinding = binding

        binding.buttonAnimation.setOnClickListener {
            animation(binding)
        }
    }

    private fun animation(binding: FragmentAnimationBinding) {
        val squareSizeHolder = PropertyValuesHolder.ofInt(
            "squareSize",
            binding.square.height,
            resources.getDimension(R.dimen.square_size_end).toInt()
        )
        val rectangleSizeHolder = PropertyValuesHolder.ofInt(
            "rectangleSize",
            binding.rectangle.height,
            resources.getDimension(R.dimen.rectangle_size_end).toInt()
        )

        val background = binding.square.background as ColorDrawable
        val squareColorHolder = PropertyValuesHolder.ofInt(
            "squareColor",
            background.color,
            resources.getColor(R.color.end_square, null)
        )
        squareColorHolder.setEvaluator(ArgbEvaluator())

        ValueAnimator.ofPropertyValuesHolder(
            squareSizeHolder,
            rectangleSizeHolder,
            squareColorHolder
        ).apply {
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                binding.square.updateLayoutParams {
                    val squareSize = it.getAnimatedValue("squareSize") as Int
                    height = squareSize
                    width = squareSize
                }

                binding.rectangle.updateLayoutParams {
                    val rectangleSize = it.getAnimatedValue("rectangleSize") as Int
                    height = rectangleSize
                }

                val squareColor = it.getAnimatedValue("squareColor") as Int
                binding.square.setBackgroundColor(squareColor)
            }
            start()
        }
    }

    override fun onDestroyView() {
        fragmentAnimationBinding = null
        super.onDestroyView()
    }
}