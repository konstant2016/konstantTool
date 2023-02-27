package com.konstant.develop.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.konstant.develop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.*

class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        lifeCycle()

    }


    private fun coroutine(){
        
        GlobalScope.launch {

        }

        CoroutineScope(Dispatchers.IO).launch {

        }

        lifecycleScope.launch {

        }

        lifecycleScope.launch(Dispatchers.IO) {

        }

    }


    private fun lifeCycle(){
        lifecycleScope.launchWhenCreated {

        }

        lifecycleScope.launchWhenResumed {

        }

        lifecycleScope.launchWhenStarted {

        }
    }

}