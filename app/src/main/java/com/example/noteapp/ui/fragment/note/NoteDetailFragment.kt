package com.example.noteapp.ui.fragment.note

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import com.example.noteapp.ui.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private var noteId: Int = -1
    var colorI = R.color.btn_gray
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Asia/Bishkek")
    }

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
        setupButtons()
        updateDate()
        setupListeners()
    }

    private fun updateNote() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
            if (noteId != -1) {
                val note = App.appDatabase?.noteDao()?.getNoteById(noteId)
                note?.let {
                    binding.etTitle.setText(it.text)
                    binding.etDescription.setText(it.description)
                    binding.llBackground.setBackgroundColor(it.color)
                }
            }
        }
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
                    etTitle.text.toString().isNotEmpty() &&
                    etDescription.text.toString().isNotEmpty()

                ) View.VISIBLE else View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etTitle.addTextChangedListener(textWatcher)
        etDescription.addTextChangedListener(textWatcher)

        btnAddText.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
          //  val backgroundColor = (llBackground.background as ColorDrawable).color
            val note = NoteModel(title, description, colorI).apply { id = noteId }

            lifecycleScope.launch(Dispatchers.IO) {
                if (noteId != -1) {
                    App.appDatabase?.noteDao()?.updateNote(note)
                } else {
                    App.appDatabase?.noteDao()?.insertNote(note)
                }
            }
            findNavController().navigateUp()
        }
    }

    private fun setupButtons() = with(binding) {
        btnGray.setOnClickListener {
            Log.d("NoteDetailFragment", "Gray button ")
            changeBackgroundColor(R.color.btn_gray)
        }
        btnWhite.setOnClickListener {
            Log.d("NoteDetailFragment", "White button clicked")
            changeBackgroundColor(R.color.btn_white)
        }
        btnRed.setOnClickListener {
            Log.d("NoteDetailFragment", "Red button clicked")
            changeBackgroundColor(R.color.btn_red)
        }
    }

    private fun changeBackgroundColor(colorRes: Int) {
        var color = ContextCompat.getColor(requireContext(), colorRes)
        Log.d("NoteDetailFragment", "Setting background color: $color")
        binding.llBackground.setBackgroundColor(color)
        colorI = colorRes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
