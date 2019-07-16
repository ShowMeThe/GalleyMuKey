package showmethe.github.kframework.util.system

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * showmethe.github.kframework.util.system
 * ken
 * 2019/1/12
 **/
object DateUtil {

    val yyMMddHHmmss = "yy年MM月dd日 HH:mm:ss"
    val yyyyMMddHHmm = "yyyy年MM月dd日 HH:mm"
    val yyyyMMddHHmmss = "yyyy年MM月dd日 HH:mm:ss"

    val yyyy_MMddHHmm = "yyyy-MM-dd HH:mm"
    val yyyy_MM_dd = "yyyy-MM-dd"
    val yyyyMMdd_HHmm = "yyyy/MM/dd HH:mm"


    fun parseDateTime(time :  String , format : String ) : Date?{
        var style  = format
        if(style.isEmpty()){
            style = yyyy_MMddHHmm
        }
        val sdf = SimpleDateFormat(format, Locale.CHINESE)
        sdf.timeZone = TimeZone.getTimeZone("GMT+8:00")
        var date : Date? = null
        try {
            date = sdf.parse(time)
        } catch (e : Exception ) {
            e.printStackTrace()
        }
        return date
    }


    fun parseToDateLong(time: String, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.CHINA)
        var date: Date? = null
        if (time.length < 5)
            return 0
        return try {
            date = sdf.parse(time)
            date.time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun getNowTime(): String {
        val nowDate = Date()
        val now = Calendar.getInstance()
        now.time = nowDate
        val formatter = SimpleDateFormat(yyyyMMddHHmmss, Locale.CHINESE)
        return formatter.format(now.time)
    }

    fun getTimeDistance(startTime: Long, endTime: Long): String {
        var ret: String? = null
        val offset = endTime - startTime
        if (endTime == 0L) {
            ret = ""
        } else {
            if (offset > 0) {
                val day: Long = offset / (24 * 60 * 60 * 1000)
                val hour: Long
                val min: Long
                val sec: Long
                hour = offset / (60 * 60 * 1000) - day * 24
                min = offset / (60 * 1000) - day * 24 * 60 - hour * 60
                sec = offset / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
                if (day != 0L) return (day * 24 + hour).toString() + ":" + min + ":" + sec + ""
                if (hour != 0L) return "$hour:$min:$sec"
                if (min != 0L) return "$min:$sec"
                return if (sec != 0L) sec.toString() + "" else "00:00:00"
            } else {
                ret = "00:00:00"
            }
        }
        return ret
    }


}