package com.dom.lattsvenska.ui.wordbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dom.lattsvenska.database.AppDatabase
import com.dom.lattsvenska.database.Word
import kotlin.properties.Delegates

class WordbookViewModel(application: Application) : AndroidViewModel(application) {

    var database: AppDatabase = AppDatabase.getInstance(application)

    private val wordsList = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = wordsList

    private val messageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = messageError

    var wordId by Delegates.notNull<Int>()
    lateinit var wordValue: String
    lateinit var wordTranslation: String

    fun loadWords() {
        wordsList.value = database.wordDao().getAll()
    }

    fun saveNewWord() {
        if (wordValue.trim().isEmpty() || wordTranslation.trim().isEmpty()) {
            messageError.value = "Missing a word or translation."
        }
        database.wordDao().save(Word(wordValue, wordTranslation))
    }
}