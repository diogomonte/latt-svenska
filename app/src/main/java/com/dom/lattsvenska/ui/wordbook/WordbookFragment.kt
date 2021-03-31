package com.dom.lattsvenska.ui.wordbook

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dom.lattsvenska.R
import com.dom.lattsvenska.database.Word
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WordbookFragment : Fragment() {

    private lateinit var wordbookViewModel: WordbookViewModel
    private lateinit var errorLayout: View
    private lateinit var errorMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_wordbook, container, false)
        setupUI(root)
        setupViewModel()
        return root
    }


    private fun setupUI(root: View) {
        errorLayout = root.findViewById(R.id.view_error)
        errorMessage = root.findViewById(R.id.view_error_message)

        val addWordButton = root.findViewById<FloatingActionButton>(R.id.fragment_wordbook_float_button)
        addWordButton.setOnClickListener {
            openNewWordDialog()
        }
    }

    private fun setupViewModel() {
        wordbookViewModel = ViewModelProvider(this).get(WordbookViewModel::class.java)
        wordbookViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        wordbookViewModel.words.observe(viewLifecycleOwner, onWordsLoaded)
    }

    private val onWordsLoaded = Observer<List<Word>> {
        errorLayout.visibility = View.GONE
    }

    private val onMessageErrorObserver = Observer<String> {
        errorMessage.text = it
        errorLayout.visibility = View.VISIBLE
    }

    private fun openNewWordDialog() {
        activity?.let {
            val dialog = Dialog(it, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
            dialog.setContentView(R.layout.dialog_new_word)

            val wordValue = dialog.findViewById<TextView>(R.id.fragment_new_word_value)
            val wordTranslation = dialog.findViewById<TextView>(R.id.fragment_new_word_translation)

            dialog.findViewById<Button>(R.id.dialog_new_word_cancel)
                .setOnClickListener {
                    dialog.dismiss()
                }

            dialog.findViewById<Button>(R.id.dialog_new_word_save)
                .setOnClickListener {
                    wordbookViewModel.wordValue = wordValue.text as String
                    wordbookViewModel.wordTranslation = wordValue.text as String
                    wordbookViewModel.saveNewWord()
                }

            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        wordbookViewModel.loadWords()
    }

}