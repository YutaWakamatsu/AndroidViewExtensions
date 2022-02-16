package jp.co.arsaga.extensions.view.baseLayout

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.co.arsaga.extensions.view.R
import timber.log.Timber

abstract class BaseWebViewFragment : Fragment() {

    protected fun getWebView(): WebView? = view?.findViewById(R.id.webView)

    protected fun getSwipeView(): SwipeRefreshLayout? = view?.findViewById(R.id.reloadWebView)

    open class Client(
        private val swipeViewQuery: () -> SwipeRefreshLayout?,
        private val isEnabledJavascript: (view: WebView?, url: String?) -> Boolean,
    ) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            view?.settings?.javaScriptEnabled = isEnabledJavascript(view, url)
            super.onPageStarted(view, url, favicon)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Timber.d("URL::${request?.url}")
            view?.settings?.javaScriptEnabled = isEnabledJavascript(view, request?.url?.toString())
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            swipeViewQuery()?.isRefreshing = false
        }
    }

    abstract fun isEnabledJavascript(view: WebView?, url: String?): Boolean

    open val webViewClient: Client by lazy {
        Client(::getSwipeView, ::isEnabledJavascript)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.base_web_view, container, false)

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onStart() {
        super.onStart()
        getWebView()?.let { webView ->
            initWebView(webView)
            webView.webViewClient = webViewClient
            setJavascriptEnabledAssist(webView.settings)
        }
        getSwipeView()?.setOnRefreshListener {
            getWebView()?.reload()
        }
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    private fun setJavascriptEnabledAssist(webSettings: WebSettings) {
        webSettings.apply {
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            displayZoomControls = false
        }
    }

    abstract fun initWebView(webView: WebView)

}