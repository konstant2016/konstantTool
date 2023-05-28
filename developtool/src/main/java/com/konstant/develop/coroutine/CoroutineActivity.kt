package com.konstant.develop.coroutine

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.konstant.develop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {

   private val requestData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
       if (it.resultCode == Activity.RESULT_OK){

       }
   }

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