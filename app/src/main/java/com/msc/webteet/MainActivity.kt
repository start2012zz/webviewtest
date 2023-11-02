package com.msc.webteet

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var webAppInterface: MainActivity.WebAppInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        setupWebView()

        webAppInterface = MainActivity.WebAppInterface(this, webView)
        webView.addJavascriptInterface(webAppInterface, "Android")

        webView.loadUrl("file:///android_asset/3.html")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                sendDataToWebView()
            }
        }
    }
    private fun sendDataToWebView() {
        val anchorsList = listOf(
            mapOf("x" to -0.120, "y" to -0.954, "z" to -1.712),
            mapOf("x" to 0.272, "y" to -0.947, "z" to -1.394),
            mapOf("x" to 0.377, "y" to -0.988, "z" to -0.964),
            mapOf("x" to 0.019, "y" to -0.988, "z" to -0.619),
            mapOf("x" to -0.286, "y" to -0.988, "z" to -0.904),
            mapOf("x" to -0.309, "y" to -0.988, "z" to -1.366)
        )
        val modelPlacementList = listOf(
            mapOf("x" to 0.025, "y" to -0.990, "z" to -1.116),
        )

        val anchorsJson = Gson().toJson(anchorsList)
        val modelPlacementJson = Gson().toJson(modelPlacementList)

        webView.evaluateJavascript("javascript:window.AndroidInterface.receiveAnchorsData('$anchorsJson')", null)
        webView.evaluateJavascript("javascript:window.AndroidInterface.receiveModelPlacementData('$modelPlacementJson')", null)
    }

    class WebAppInterface(private val context: Context, private val webView: WebView) {

        @JavascriptInterface
        fun sendDataToAndroid(dataJson: String) {
            // Process the data here
            Log.d("WebAppInterface", "Data received from web: $dataJson")
        }
    }


}



//class MainActivity : AppCompatActivity() {
//    private lateinit var webview: WebView
//    private lateinit var webAppInterface: WebAppInterface;
//    private val assetLoader = WebViewAssetLoader.Builder()
//        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
//        .build()
//    class WebAppInterface(private val context: Context, private val webView: WebView) {
//
//        @JavascriptInterface
//        fun sendAnchorsData(anchorsJson: String) {
//            webView.post {
//                webView.evaluateJavascript("javascript:receiveAnchorsData('$anchorsJson')", null)
//            }
//        }
//
//        @JavascriptInterface
//        fun sendModelPlacementData(modelPlacementJson: String) {
//            webView.post {
//                webView.evaluateJavascript("javascript:receiveModelPlacementData('$modelPlacementJson')", null)
//            }
//        }
//
//        @JavascriptInterface
//        fun getAnchorsData(anchorsList:List<Int>): String {
//            // Implement the logic to retrieve and return anchors data as a JSON string
//            val anchorsList: List<Int> = anchorsList // Retrieve your anchors data here
//            val anchorsJson = Gson().toJson(anchorsList)
//            return anchorsJson
//        }
//    }
//
//    @SuppressLint("SetJavaScriptEnabled")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val anchorsList = listOf(
//            mapOf("x" to -0.120, "y" to -0.954, "z" to -1.712),
//            mapOf("x" to 0.272, "y" to -0.947, "z" to -1.394),
//            mapOf("x" to 0.377, "y" to -0.988, "z" to -0.964),
//            mapOf("x" to 0.019, "y" to -0.988, "z" to -0.619),
//            mapOf("x" to -0.286, "y" to -0.988, "z" to -0.904),
//            mapOf("x" to -0.309, "y" to -0.988, "z" to -1.366)
//        )
//        val modelPlacementList = listOf(
//            mapOf("x" to 0.025, "y" to -0.990, "z" to -1.116),
//        )
//        webview = findViewById(R.id.webView)
//        val webSettings: WebSettings = webview.getSettings()
//        webSettings.allowFileAccess = true
//        webview.settings.javaScriptEnabled = true
//        webAppInterface=WebAppInterface(this,webview)
//        webview.addJavascriptInterface(webAppInterface, "Android")
////        webview.webViewClient =WebViewClient()
//        webview.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                sendDataToWebView(anchorsList,modelPlacementList)
//            }
//        }
//
//        webview.loadUrl("file:///android_asset/3.html")
//        Log.e("anchorsJson", "web-view load")
//
//    }
//
//    private fun sendDataToWebView(anchorsList: List<Map<String, Double>>, modelPlacementList: List<Map<String, Double>>) {
//
//
//
//        val anchorsJson = Gson().toJson(anchorsList)
//        val modelPlacementJson = Gson().toJson(modelPlacementList)
//
//        webAppInterface.sendAnchorsData(anchorsJson)
//        webAppInterface.sendModelPlacementData(modelPlacementJson)
//        Log.e("anchorsJson", "anchor send")
//    }
//}