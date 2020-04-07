package com.konstant.tool.ui.activity.pdfreader

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.konstant.tool.R
import kotlinx.android.synthetic.main.activity_pdf_reader_bitmap.*

class PdfReaderBitmapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader_bitmap)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        view_pdf.fromAsset("web/副本-hello.pdf")
                .load()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
