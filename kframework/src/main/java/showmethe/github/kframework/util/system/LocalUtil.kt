package showmethe.github.kframework.util.system

import android.content.Context
import android.os.Build
import android.os.LocaleList

import java.util.Locale

/**
 * Created by Ken on 2018/10/10.
 */


object LocalUtil {

    /**
     * 中文
     */
    val LOCALE_CHINESE = Locale.CHINESE
    /**
     * 英文
     */
    val LOCALE_ENGLISH = Locale.ENGLISH


    fun updateLocale(context: Context, locale: Locale): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales = LocaleList(locale)
        }
        return context.createConfigurationContext(configuration)
    }


}

