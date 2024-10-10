package com.example.noteapp.ui.fragment.onBoard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private var _binding: FragmentOnBoardPagingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardPagingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ON_BOARD_PAGE_KEY)) {
            0 -> {
                tvTitle.text = getString(R.string.convenience)
                tvDesc.text = getString(R.string.desc_one)
                lottieView.setAnimation(R.raw.unity2)

            }

            1 -> {
                tvTitle.text = getString(R.string.organization)
                tvDesc.text = getString(R.string.desc_two)
                lottieView.setAnimation(R.raw.two_anim)
            }

            2 -> {
                tvTitle.text = getString(R.string.sinchronization)
                tvDesc.text = getString(R.string.desc_three)
                lottieView.setAnimation(R.raw.unity2)
            }
        }
    }

    companion object {
        const val ARG_ON_BOARD_PAGE_KEY = "onBoard"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}