package com.dom.lattsvenska.ui.wordbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dom.lattsvenska.R
import com.dom.lattsvenska.database.Word

class WordRecyclerViewAdapter(var words: List<Word>):
    RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_word, parent, false)
        return WordViewHolder(layout)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    fun update(data: List<Word>) {
        words = data
        notifyDataSetChanged()
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var wordValue: TextView = itemView.findViewById(R.id.cardview_word_value)
        private var wordTranslation: TextView = itemView.findViewById(R.id.cardview_word_translation)

        fun bind(word: Word) {
            wordValue.text = word.value
            wordTranslation.text = word.translation
        }
    }
}