package com.example.noteapp.ui.fragment.note

import android.os.Bundle
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

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNote()
        updateDataYear()
        setupListeners()
    }

    private fun updateNote() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val argsNote = App.appDatabase?.noteDao()?.getNoteById(noteId)
            argsNote?.let { item ->
                binding.etTitle.setText(item.text)
                binding.etDescription.setText(item.description)
            }
        }
    }

    private fun updateDataYear() {
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        binding.tvTime.text = currentDate
    }

    private fun setupListeners() = with(binding) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btnAddText.visibility =
                    if (etTitle.text.isNotEmpty() && etDescription.text.isNotEmpty())
                        View.VISIBLE
                    else
                        View.INVISIBLE
            }
        }
        etTitle.addTextChangedListener(textWatcher)
        etDescription.addTextChangedListener(textWatcher)

        btnAddText.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            if (noteId != -1) {
                val updatedNote = NoteModel(title, description).apply { id = noteId }
                App.appDatabase?.noteDao()?.updateNote(updatedNote)
            } else {
                App.appDatabase?.noteDao()?.insertNote(NoteModel(title, description))
            }
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
