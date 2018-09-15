package com.a8kmusic.a8kmusic.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.a8kmusic.a8kmusic.R

class MainActivity : AppCompatActivity() {

    var webView: WebView? = null
    var btnRefresh: ImageView? = null
    var progressBar: ProgressBar? = null
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WebView.setWebContentsDebuggingEnabled(false)
        progressBar = findViewById(R.id.progress_bar)
        btnRefresh = findViewById(R.id.btn_refresh)
        btnRefresh!!.setOnClickListener {
            showProgress()
            initWebView()
        }
        webView = findViewById(R.id.webView)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        webView!!.settings.javaScriptEnabled = true
        webView!!.isVerticalScrollBarEnabled = true
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView!!.loadUrl("https://8kmusic.com/")
        Handler().postDelayed({ hideProgress() }, 2500)
    }

    fun showProgress() {
        this@MainActivity.runOnUiThread {
            progressBar!!.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {
        this@MainActivity.runOnUiThread {
            progressBar!!.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        showExitAlert()
    }

    fun showExitAlert() {
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null)
        val btnConfirm = v.findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener({
            dialog!!.dismiss()
            this.finish()
        })

        val btnCancel = v.findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener({
            dialog!!.dismiss()
        })
        dialog!!.setContentView(v)
        dialog!!.show()
    }

}