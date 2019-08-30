package showmethe.github.kframework.divider.stick.module

import android.util.ArrayMap
import androidx.databinding.ObservableArrayList

class SortModule<T : Any>{

    private val arrayMap = ArrayMap<String,ArrayList<T>>()
    private val groupNames = ArrayMap<Int ,String>()
    private val flag = ArrayList<Int>()
    private val normalList = ArrayList<T>()
    private val observableArrList = ObservableArrayList<T>()

    fun  soft(data : java.util.ArrayList<T>, factor: ((item:T,position:Int)->Pair<String,T>)) : ArrayList<T>{
        val newData = ArrayList<T>()
        allClear()
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
                for(index in value.indices){
                    groupNames[newData.size - value.size + index] = item
                }
            }
        }

        normalList.addAll(newData)
        observableArrList.addAll(newData)
        return newData
    }

    private  fun allClear(){
        normalList.clear()
        observableArrList.clear()
        arrayMap.clear()
        groupNames.clear()
        flag.clear()
    }

    fun getNormalList() = normalList
    fun getObservableArrayList() = observableArrList


    fun getGroupName() : ArrayMap<Int ,String> = groupNames

    fun getFlag():ArrayList<Int> = flag

    fun getArrayMap():ArrayMap<String,ArrayList<T>> = arrayMap

}