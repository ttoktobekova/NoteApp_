package com.example.noteapp.ui.fragment.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.ui.utils.PreferenceHelper

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() = with(binding) {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        btnSave.setOnClickListener {
            val et = etEnter.text.toString().trim()
            sharedPreferences.text = et
            tvMeaning.text = et
        }
        tvMeaning.text = sharedPreferences.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}