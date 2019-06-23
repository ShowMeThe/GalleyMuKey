package showmethe.github.kframework.util.system

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import showmethe.github.kframework.base.AppManager

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import kotlin.system.exitProcess

/**
 * 捕捉异常保存到本地
 *
 *
 * 2019/1/4
 */
class CrashHandler
/** 保证只有一个CrashHandler实例  */
private constructor() : Thread.UncaughtExceptionHandler {
    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context? = null

    // 用来存储设备信息和异常信息
    private val infos = HashMap<String, String>()

    private val globalpath: String
        get() = (mContext!!.externalCacheDir!!.absolutePath
                + File.separator + "crash" + File.separator)

    /**
     * 初始化
     *
     * @param context
     */
    fun init(context: Context) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }



    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @RequiresApi(Build.VERSION_CODES.P)
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            // 退出当前程序
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    @RequiresApi(Build.VERSION_CODES.P)
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null)
            return false

        try {
            // 收集设备参数信息
            collectDeviceInfo(mContext)
            // 保存日志文件
            saveCrashInfoFile(ex)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun collectDeviceInfo(ctx: Context?) {
        try {
            val pm = ctx!!.packageManager
            val pi = pm.getPackageInfo(ctx.packageName,
                    PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = pi.versionName + ""
                val versionCode = pi.longVersionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
                infos["android version"] = Build.VERSION.RELEASE
                infos["phoneType"] = Build.BRAND + "  " + Build.MODEL
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun saveCrashInfoFile(ex: Throwable): String? {
        val sb = StringBuffer()
        try {
            val sDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT)
            val date = sDateFormat.format(Date())
            sb.append("\r\n" + date + "\n")
            for ((key, value) in infos) {
                sb.append("$key=$value\n")
            }

            val writer = StringWriter()
            val printWriter = PrintWriter(writer)
            ex.printStackTrace(printWriter)
            var cause: Throwable? = ex.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.flush()
            printWriter.close()
            val result = writer.toString()
            sb.append(result)

            return writeFile(sb.toString())
        } catch (e: Exception) {
            writeFile(sb.toString())
        }

        return null
    }

    @Throws(Exception::class)
    private fun writeFile(sb: String): String {
        val time = System.currentTimeMillis().toString() + ""
        val fileName = "crash-$time.log"
        try {
            val path = globalpath
            val dir = File(path)
            if (!dir.exists())
                dir.mkdirs()
            val fos = FileOutputStream(path + fileName, true)
            fos.write(sb.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fileName
    }

    companion object {
        var TAG = "Crash"

        private val instant by lazy { CrashHandler() }

        fun get():CrashHandler{
            return  instant
        }
    }

}
