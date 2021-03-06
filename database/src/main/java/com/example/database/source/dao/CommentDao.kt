package com.example.database.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.source.dto.CommentDto

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(bean : CommentDto)

    @Query("select * from CommentDto where parentId =:id")
    suspend fun findCommentById(id:Int) :List<CommentDto>


}