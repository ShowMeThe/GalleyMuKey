package showmethe.github.kframework.util.assetFile

import android.content.Context
import android.content.res.AssetManager

import showmethe.github.kframework.base.BaseApplication


import java.io.*


/**
 * PackageName: example.ken.com.library.utils.AssetFile.AssetFile
 * Author : jiaqi Ye
 * Date : 2018/9/28
 * Time : 11:56
 */
class AssetFile(private val assetPath: String) {

    private val fileName: String
    private val manager: AssetManager
    private var isDirectory: Boolean = false


    val parent: String
        get() {
            val index = assetPath.lastIndexOf(File.separatorChar.toString())
            return assetPath.substring(0, index)
        }

    val parentFile: AssetFile
        get() = AssetFile(parent)

    init {
        val index = assetPath.lastIndexOf(File.separatorChar.toString())
        this.fileName = assetPath.substring(index + 1, assetPath.length)
        manager = BaseApplication.context.assets
    }

    /**
     * 是否存在
     * @return
     */
    fun exists(): Boolean {
        try {
            manager.list(assetPath)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 是否为目录
     * @return
     */
    fun isDirectory(): Boolean {
        try {
            isDirectory = manager.list(assetPath)!!.isNotEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
            isDirectory = false
        }

        return isDirectory
    }

    companion object {

        /**
         * 复制到存储目录
         * @param assetManager
         * @param assetPath
         * @param outputFile
         * @return
         */
        fun copyToFile(assetManager: AssetManager, assetPath: String, outputFile: File): Boolean {
            try {
                val `is` = assetManager.open(assetPath)
                var byteRead: Int
                val fs = FileOutputStream(outputFile)
                val buffer = ByteArray(1024)
                while ((`is` .read(buffer).also {
                            byteRead = it
                        }) != -1) {
                      fs.write(buffer, 0, byteRead)
                }
                fs.flush()
                fs.close()
                `is`.close()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }


        fun getJson(context: Context, fileName: String): String {
            val stringBuilder = StringBuilder()
            try {
                val assetManager = context.assets
                val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
                var line: String
                bf.use {
                    while (true) {
                        line = bf.readLine() ?:break
                        stringBuilder.append(line)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

    }

}
