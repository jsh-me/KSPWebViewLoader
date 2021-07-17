package com.jshme.kspsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.jshme.ksp.WebViewBuilder

class MainActivity : AppCompatActivity() {

    @WebViewBuilder(
        url = "https://www.google.com/",
        autoSet = true
    )
    lateinit var webView: WebView

    @WebViewBuilder(
        url = "https://google.com/",
        autoSet = true
    )
    lateinit var webView2: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById<WebView>(R.id.webView)
        webView2 = findViewById<WebView>(R.id.webView2)

        webView.settings.apply {

        }
        WebViewLoader.onInitialize(this)
    }
}
