package com.konstant.tool.ui.activity.pdfreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.konstant.tool.R
import kotlinx.android.synthetic.main.activity_pdf_reader_h5.*

class PdfReaderH5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader_h5)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        web_view.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }

        web_view.loadUrl("file:///android_asset/web/viewer.html?file=" + "file:///android_asset/web/副本-hello.pdf");
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
