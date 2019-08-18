package showmethe.github.kframework.divider.stick.module

class GroupModule {

    private var isInit = false

    private lateinit var softModule: SortModule<*>

    fun init(softModule: SortModule<*>) : GroupModule {
        this.softModule = softModule
        isInit = true
        return this
    }


    fun getGroupCount() = softModule.getArrayMap().keys.size

    fun getGrouSize(groupName:String) : Int {
        return if(softModule.getArrayMap()[groupName] == null){
            0
        }else{
            softModule.getArrayMap()[groupName]!!.size
        }
    }


    fun getGroupName(flagPosition:Int ):String = softModule.getGroupName()[flagPosition]!!

    fun isFirstPositionInGroup(position:Int) : Boolean{
        if(!isInit){
            return false
        }
        for(it in softModule.getFlag()){
            if(it == position){
                return true
            }
        }
        return false
    }

}