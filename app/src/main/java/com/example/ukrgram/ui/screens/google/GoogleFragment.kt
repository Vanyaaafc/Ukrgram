package com.example.ukrgram.ui.screens.google

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.ukrgram.R
import com.example.ukrgram.ui.screens.base.BaseFragment
import com.example.ukrgram.utilits.APP_ACTIVITY


class GoogleFragment : BaseFragment(R.layout.fragment_google) {


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Google"
        showWebView()
    }
//
    private fun showWebView() {
        val webView = view?.findViewById<WebView>(R.id.webView)
        webView?.loadUrl("https://google.com")
        val webViewClient: WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest,
            ): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }
        webView?.webViewClient = webViewClient
    }
}
