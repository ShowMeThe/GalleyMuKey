package example.ken.galleymukey.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CommentDto")
class CommentDto {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var parentId : Int = 0
    var comment:String = ""

    constructor(parentId: Int, comment: String) {
        this.parentId = parentId
        this.comment = comment
    }
}