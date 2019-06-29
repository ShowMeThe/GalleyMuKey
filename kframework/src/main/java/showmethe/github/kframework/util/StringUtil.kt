package showmethe.github.kframework.util


import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * showmethe.github.kframework.util
 * cuvsu
 * 2019/6/1
 **/
object StringUtil {

    /**
     * 保留两位小数
     * @param num
     * @return
     */
    fun double2Decimal(num: Double): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = 2
        df.groupingSize = 0
        df.roundingMode = RoundingMode.FLOOR
        val style = "###0.00"// 定义要显示的数字的格式
        df.applyPattern(style)
        return df.format(num)
    }

    /**
     * 保留两位小数
     * @param num
     * @return
     */
    fun float2Decimal(num: Float): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = 2
        df.groupingSize = 0
        df.roundingMode = RoundingMode.FLOOR
        val style = "###0.00"// 定义要显示的数字的格式
        df.applyPattern(style)
        return df.format(num.toDouble())
    }

}