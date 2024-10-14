package com.example.noteapp.ui.fragment.note

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import com.example.noteapp.ui.App
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Asia/Bishkek")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateDate()
        setupListeners()
    }

    private fun updateDate() {
        handler.post {
            val currentDate = dateFormat.format(Date())
            binding.tvTime.text = currentDate
        }
    }

    private fun setupListeners() = with(binding) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnAddText.visibility = if (
                    etTitle.text.toString().trim().isNotEmpty() &&
                    etDescription.text.toString().trim().isNotEmpty()
                ) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        etTitle.addTextChangedListener(textWatcher)
        etDescription.addTextChangedListener(textWatcher)

        btnAddText.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
                App.appDatabase?.noteDao()?.insertNote(NoteModel(title, description))
            findNavController().navigateUp()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}