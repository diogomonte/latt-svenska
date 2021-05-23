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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dom.lattsvenska.R
import com.dom.lattsvenska.database.Word
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.doAsync


class WordbookFragment : Fragment() {

    private lateinit var wordbookViewModel: WordbookViewModel
    private lateinit var errorLayout: View
    private lateinit var errorMessage: TextView
    private lateinit var wordRecyclerView: RecyclerView
    private lateinit var wordRecycleViewAdapter: WordRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_wordbook, container, false)
        setupViewModel()
        setupUI(root)
        return root
    }

    private fun setupViewModel() {
        wordbookViewModel = ViewModelProvider(this).get(WordbookViewModel::class.java)
        wordbookViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        wordbookViewModel.words.observe(viewLifecycleOwner, onWordsLoaded)
    }

    private fun setupUI(root: View) {
        wordRecycleViewAdapter = WordRecyclerViewAdapter(wordbookViewModel.words.value ?: emptyList())
        errorLayout = root.findViewById(R.id.fragment_wordbook_error)
        errorMessage = root.findViewById(R.id.view_error_message)
        wordRecyclerView = root.findViewById(R.id.fragment_wordbook_recycler_view)
        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        wordRecyclerView.layoutManager = layoutManager
        wordRecyclerView.adapter = wordRecycleViewAdapter

        val addWordButton = root.findViewById<FloatingActionButton>(R.id.fragment_wordbook_float_button)
        addWordButton.setOnClickListener {
            openNewWordDialog()
        }
    }

    private val onWordsLoaded = Observer<List<Word>> {
        errorLayout.visibility = View.GONE
        wordRecycleViewAdapter.update(it)
    }

    private val onMessageErrorObserver = Observer<String> {
        errorMessage.text = it
        errorLayout.visibility = View.VISIBLE
    }

    private fun openNewWordDialog() {
        activity?.let {
            val dialog = Dialog(it, android.R.style.Theme_Light_NoTitleBar_Fullscreen).apply {
                setContentView(R.layout.dialog_new_word)
                val wordValue = findViewById<TextView>(R.id.fragment_new_word_value)
                val wordTranslation = findViewById<TextView>(R.id.fragment_new_word_translation)

                findViewById<Button>(R.id.dialog_new_word_cancel)
                        .setOnClickListener {
                            wordbookViewModel.wordValue = ""
                            wordbookViewModel.wordTranslation = ""
                            dismiss()
                        }

                findViewById<Button>(R.id.dialog_new_word_save)
                        .setOnClickListener {
                            wordbookViewModel.wordValue = wordValue.text.toString()
                            wordbookViewModel.wordTranslation = wordTranslation.text.toString()
                            doAsync {
                                wordbookViewModel.saveNewWord()
                            }
                        }
            }

            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        wordbookViewModel.loadWords()
    }

}