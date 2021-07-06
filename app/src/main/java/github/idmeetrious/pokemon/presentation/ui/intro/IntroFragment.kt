package github.idmeetrious.pokemon.presentation.ui.intro

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import github.idmeetrious.pokemon.databinding.FragmentIntroBinding

private const val TAG = "IntroFragment"

class IntroFragment : Fragment() {
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initViews()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimationLogo()

        // Move to search screen
        binding.introToSearchScreenBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Move to search screen")
        }
        // Move to random screen
        binding.introToRandomScreenBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Move to random screen")
        }
    }

    @SuppressLint("Recycle")
    private fun playAnimationLogo() {
        val fadeOutAnimator = ValueAnimator.ofInt(200, 0)
            .apply {
                addUpdateListener {
                    val value = it.animatedValue as Int
                    binding.introLogo.imageAlpha = value
                }
                duration = 500
                doOnEnd {
                    Log.i(TAG, "--> playAnimationLogo: fadeOut")
                    updateViews()
                }
            }

        val rotateAnimator = ValueAnimator.ofFloat(0f, 360f)
            .apply {
                addUpdateListener {
                    val value = it.animatedValue as Float
                    binding.introLogo.rotation = value
                }
                duration = 2000
                doOnEnd {
                    Log.i(TAG, "--> playAnimationLogo: rotation")
                    fadeOutAnimator.start()
                }
            }

        val fadeInAnimator = ValueAnimator.ofInt(0, 200)
            .apply {
                addUpdateListener {
                    val value = it.animatedValue as Int
                    binding.introLogo.imageAlpha = value
                }
                duration = 1000
                doOnEnd {
                    Log.i(TAG, "--> playAnimationLogo: fadeIn")
                    rotateAnimator.start()
                }
            }.start()


    }

    private fun initViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.introLogo.visibility = View.VISIBLE
        binding.introMenuLayout.visibility = View.GONE
    }

    private fun updateViews() {
//        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.introLogo.visibility = View.GONE
        binding.introMenuLayout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}