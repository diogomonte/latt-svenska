package com.dom.lattsvenska.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "words")
data class Word(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                @ColumnInfo(name = "value") val value: String,
                @ColumnInfo(name = "translation")  val translation: String)

@Dao
interface WordDao {

    @Query("SELECT * FROM words")
    fun getAll(): LiveData<List<Word>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(word: Word)

    @Insert
    fun save(word: Word)

    @Delete
    fun delete(word: Word)

}