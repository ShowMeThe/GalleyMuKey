package example.ken.galleymukey.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import example.ken.galleymukey.source.dto.CommentDto

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(bean : CommentDto)

    @Query("select * from CommentDto where id =:id")
    fun findCommentById(id:Int) : LiveData<List<CommentDto>>


}