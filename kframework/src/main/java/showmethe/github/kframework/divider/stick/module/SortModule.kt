package showmethe.github.kframework.divider.stick.module

import android.util.ArrayMap

class SortModule<T : Any>{

    private val arrayMap = ArrayMap<String,ArrayList<T>>()
    private val groupNames = ArrayMap<Int ,String>()
    private val flag = ArrayList<Int>()

    fun  soft(data : java.util.ArrayList<T>, factor: ((item:T,position:Int)->Pair<String,T>)) : ArrayList<T>{
        val newData = ArrayList<T>()
        for((index,item) in data.withIndex()){
            val pair = factor.invoke(item,index)
            if(arrayMap[pair.first] == null){
                arrayMap[pair.first] = ArrayList<T>()
            }
            arrayMap[pair.first]?.add(pair.second)
        }

        for(item in arrayMap.keys){
            val value = arrayMap[item]
            if (value != null) {
                newData.addAll(value)
                flag.add(newData.size - value.size)
                groupNames[newData.size - value.size] = item
            }
        }
        return newData
    }

    fun getGroupName() : ArrayMap<Int ,String> = groupNames

    fun getFlag():ArrayList<Int> = flag

    fun getArrayMap():ArrayMap<String,ArrayList<T>> = arrayMap

}