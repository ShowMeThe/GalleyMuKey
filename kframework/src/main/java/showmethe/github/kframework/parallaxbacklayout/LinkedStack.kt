package showmethe.github.kframework.parallaxbacklayout

import java.util.LinkedHashMap
import java.util.LinkedList

/**
 * ParallaxBackLayout
 *
 */

class LinkedStack<K, V> {
    private val mLinkedList = LinkedList<K>()
    private val mTraceInfoHashMap = LinkedHashMap<K, V>()

    fun put(k: K, v: V) {
        mLinkedList.add(k)
        mTraceInfoHashMap[k] = v
    }

    fun remove(k: K) {
        mLinkedList.remove(k)
        mTraceInfoHashMap.remove(k)
    }

    fun before(k: K): K? {
        val index = mLinkedList.indexOf(k)
        return if (index < 1) null else mLinkedList[index - 1]
    }

    operator fun get(k: K): V? {
        return mTraceInfoHashMap[k]
    }

    fun getKey(index: Int): K {
        return mLinkedList[index]
    }

    fun size(): Int {
        return mLinkedList.size
    }
}
