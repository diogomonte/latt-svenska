package com.dom.lattsvenska.ui.wordbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.dom.lattsvenska.database.AppDatabase
import com.dom.lattsvenska.database.Word
import kotlin.properties.Delegates

class WordbookViewModel(application: Application) : AndroidViewModel(application) {

    var database: AppDatabase = AppDatabase.getInstance(application)

    var words: LiveData<List<Word>> = database.wordDao().getAll()

    private val messageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = messageError

    private val message = MutableLiveData<String>()
    val onMessage: LiveData<String> = message

    var wordId by Delegates.notNull<Int>()
    lateinit var wordValue: String
    lateinit var wordTranslation: String

    fun loadWords() {
        words = database.wordDao().getAll()
    }

    fun saveNewWord() {
        if (wordValue.trim().isEmpty() || wordTranslation.trim().isEmpty()) {
            messageError.value = "Missing a word or translation."
        }
        database.wordDao().save(Word(value = wordValue, translation = wordTranslation))
    }
}