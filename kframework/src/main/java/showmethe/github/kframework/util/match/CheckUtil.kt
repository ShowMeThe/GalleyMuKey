package showmethe.github.kframework.util.match

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Hashtable
import java.util.regex.Pattern

object CheckUtil {

    /**
     * 判断两个string是否相等
     */
    fun checkEquels(strObj0: Any, strObj1: Any): Boolean {
        val str0 = strObj0.toString() + ""
        val str1 = strObj1.toString() + ""
        return if (str0 == str1) {
            true
        } else false
    }


    /**
     * 同时判断多个参数是否为空
     *
     * @param strArray
     * @return
     */
    fun isNull(vararg strArray: Any): Boolean {
        for (obj in strArray) {
            if ("" != obj.toString() + "") {
                return false
            }
        }
        return true
    }

    /**
     * 判是否是字母
     *
     * @return
     */
    fun isLetter(txt: String): Boolean {
        if (isNull(txt)) {
            return false
        }
        val p = Pattern.compile("[a-zA-Z]")
        val m = p.matcher(txt)
        return if (m.matches()) {
            true
        } else false
    }


    /**
     * 判断对象是否为空
     */
    fun isNull(strObj: Any): Boolean {
        val str = strObj.toString() + ""
        return if ("" != str && "null" != str) {
            false
        } else true
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */

    fun containsEmoji(source: String): Boolean {

        val len = source.length

        for (i in 0 until len) {

            val codePoint = source[i]

            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情

                return true

            }

        }

        return false
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint             比较的单个字符
     * @return
     */

    private fun isEmojiCharacter(codePoint: Char): Boolean {

        return (codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD

                || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD

                || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF)

    }

    /**
     * 判断是否邮箱
     *
     * @param strObj
     * @return
     */
    fun checkEmail(strObj: Any): Boolean {
        val str = strObj.toString() + ""
        if (!str.endsWith(".com")) {
            return false
        }
        val match = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
        val pattern = Pattern.compile(match)
        val matcher = pattern.matcher(str)
        return if (matcher.matches()) {
            true
        } else false
    }


    /**
     * 监测是否为正确的手机号码
     *
     * @param mobile
     * @return
     */
    fun isMobileCorrect(mobile: String): Boolean {
        val regex = "^((13[0-9])|(15[^4])|(16[0-9])|(17[0-8])|(18[0-9])|(19[0-9])|(147,145))\\d{8}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(mobile)
        return matcher.matches()
    }


    fun isIDCardValidate(IDStr: String): Boolean {
        val ValCodeArr = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
        val Wi = arrayOf("7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2")
        var Ai = ""
        // ================ 号码的长度18位 ================
        if (IDStr.length != 18) {
            return false
        }
        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length == 18) {
            Ai = IDStr.substring(0, 17)
        }
        if (isNumeric(Ai) == false) {
            //errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false
        }
        // ================ 出生年月是否有效 ================
        val strYear = Ai.substring(6, 10)// 年份
        val strMonth = Ai.substring(10, 12)// 月份
        val strDay = Ai.substring(12, 14)// 日
        if (isDate("$strYear-$strMonth-$strDay") == false) {
            //          errorInfo = "身份证生日无效。";
            return false
        }
        val gc = GregorianCalendar()
        val s = SimpleDateFormat("yyyy-MM-dd")
        try {
            if (gc.get(Calendar.YEAR) - Integer.parseInt(strYear) > 150 || gc.time.time - s.parse("$strYear-$strMonth-$strDay").time < 0) {
                //errorInfo = "身份证生日不在有效范围。";
                return false
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            //errorInfo = "身份证月份无效";
            return false
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            //errorInfo = "身份证日期无效";
            return false
        }
        // ================ 地区码时候有效 ================
        val h = GetAreaCode()
        if (h[Ai.substring(0, 2)] == null) {
            //errorInfo = "身份证地区编码错误。";
            return false
        }
        // ================ 判断最后一位的值 ================
        var TotalmulAiWi = 0
        for (i in 0..16) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(Ai[i].toString()) * Integer.parseInt(Wi[i])
        }
        val modValue = TotalmulAiWi % 11
        val strVerifyCode = ValCodeArr[modValue]
        Ai = Ai + strVerifyCode

        if (IDStr.length == 18) {
            if (Ai == IDStr == false) {
                //errorInfo = "身份证无效，不是合法的身份证号码";
                return false
            }
        } else {
            return true
        }
        return true
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private fun GetAreaCode(): Hashtable<*, *> {
        val hashtable = Hashtable<String, String>()
        hashtable.put("11", "北京")
        hashtable.put("12", "天津")
        hashtable.put("13", "河北")
        hashtable.put("14", "山西")
        hashtable.put("15", "内蒙古")
        hashtable.put("21", "辽宁")
        hashtable.put("22", "吉林")
        hashtable.put("23", "黑龙江")
        hashtable.put("31", "上海")
        hashtable.put("32", "江苏")
        hashtable.put("33", "浙江")
        hashtable.put("34", "安徽")
        hashtable.put("35", "福建")
        hashtable.put("36", "江西")
        hashtable.put("37", "山东")
        hashtable.put("41", "河南")
        hashtable.put("42", "湖北")
        hashtable.put("43", "湖南")
        hashtable.put("44", "广东")
        hashtable.put("45", "广西")
        hashtable.put("46", "海南")
        hashtable.put("50", "重庆")
        hashtable.put("51", "四川")
        hashtable.put("52", "贵州")
        hashtable.put("53", "云南")
        hashtable.put("54", "西藏")
        hashtable.put("61", "陕西")
        hashtable.put("62", "甘肃")
        hashtable.put("63", "青海")
        hashtable.put("64", "宁夏")
        hashtable.put("65", "新疆")
        //      hashtable.put("71", "台湾");
        //      hashtable.put("81", "香港");
        //      hashtable.put("82", "澳门");
        //      hashtable.put("91", "国外");
        return hashtable
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return if (isNum.matches()) {
            true
        } else {
            false
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param
     * @return
     */
    fun isDate(strDate: String): Boolean {
        val pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$")
        val m = pattern.matcher(strDate)
        return if (m.matches()) {
            true
        } else {
            false
        }
    }

    /**
     * 判断是否为min到max位的字母或数字
     *
     * @param s
     * @param min
     * @param max
     * @return
     */
    fun isAlphanumericRange(s: String, min: Int, max: Int): Boolean {
        val regex = "^[a-z0-9A-Z]{$min,$max}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }

    /**
     * 判断是否为n为数字的验证码
     *
     * @param s
     * @param n
     * @return
     */
    fun isVerificationCode(s: String, n: Int): Boolean {
        val regex = "^[0-9]{$n}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }

    /**
     * 检查内容的长度是否大于等于要求
     *
     * @param
     * @param
     * @return
     */
    fun checkLength(strObj: Any, length: Int): Boolean {
        val str = strObj.toString() + ""
        return str.length >= length
    }

    /**
     * 检测文件链接
     * @param url
     * @return
     */
    fun checkFileURL(url: String): Boolean {
        if (isNull(url)) {
            return false
        }
        val regular = "(file)://[\\S]*"
        return Pattern.matches(regular, url)
    }

    /**
     * 检查是否合法金额
     *
     * @param goal
     * @return
     */
    fun checkIsGoal(goal: String): Boolean {
        val regular = "^(([1-9]\\d*)|([0]))(\\.(\\d){0,2})?$"
        return Pattern.matches(regular, goal)
    }

    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    fun checkPassword(passwordStr: String): String {
        val regexZ = "\\d*"
        val regexS = "[a-zA-Z]+"
        val regexZS = "\\w*"

        if (passwordStr.length in 6..9 && passwordStr.matches(regexZ.toRegex())) {
            return "弱"
        }
        if (passwordStr.length in 6..9 && passwordStr.matches(regexS.toRegex())) {
            return "弱"
        }
        if (passwordStr.length in 6..9 && passwordStr.matches(regexZS.toRegex())) {
            return "中"
        }
        if (passwordStr.length > 9 && passwordStr.matches(regexZ.toRegex())) {
            return "中"
        }
        if (passwordStr.length > 9 && passwordStr.matches(regexS.toRegex())) {
            return "中"
        }
        return if (passwordStr.length > 9 && passwordStr.matches(regexZS.toRegex())) {
            "强"
        } else passwordStr
    }
}
