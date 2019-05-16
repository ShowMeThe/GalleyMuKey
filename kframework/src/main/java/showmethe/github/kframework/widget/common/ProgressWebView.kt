package showmethe.github.kframework.widget.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

import showmethe.github.kframework.R


/**
 * 带进度条的WebView
 */
class ProgressWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : WebView(context, attrs) {

    private val progressbar: ProgressBar

    init {
        val view = View.inflate(context, R.layout.progress_webview_layout, null)
        progressbar = view.findViewById<View>(R.id.progressbar) as ProgressBar
        progressbar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0, 0)
        addView(progressbar)

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http://") || url.startsWith("https://")) {
                    false

                } else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                        true
                    } catch (e: Exception) {
                        true
                    }

                }
            }
        }
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressbar.visibility = View.GONE
                } else {
                    if (progressbar.visibility == View.GONE) {
                        progressbar.visibility = View.VISIBLE
                    }
                    progressbar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressbar.layoutParams as LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }

    /**
     * 通用设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun defaultSetting() {
        val settings = settings
        //设置WebView属性，能够执行Javascript脚本
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.defaultTextEncodingName = "utf-8"
        settings.allowFileAccess = false
        settings.setSupportMultipleWindows(true)
        settings.minimumFontSize = settings.minimumFontSize + 8
        settings.textZoom = 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        }
        // 首先使用缓存
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true//启用js
        settings.blockNetworkImage = false//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Https需要加这句
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webViewClient = WebViewClient()
    }
}
