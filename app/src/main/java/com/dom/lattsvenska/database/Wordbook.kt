package com.dom.lattsvenska.database

import androidx.room.*

@Entity(tableName = "words")
data class Word(@PrimaryKey val id: Int,
                @ColumnInfo(name = "value") val value: String,
                @ColumnInfo(name = "translation")  val translation: String) {

    constructor(value: String,
                translation: String)
}

@Dao
interface WordDao {

    @Query("SELECT * FROM words")
    fun getAll(): List<Word>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(word: Word)

    @Insert
    fun save(word: Word)

    @Delete
    fun delete(word: Word)

}