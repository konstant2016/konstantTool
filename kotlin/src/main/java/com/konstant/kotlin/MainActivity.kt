package com.konstant.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val anotherInt = 0xFF;
    val moreInt = 0b000000011
    val maxInt = Int.MAX_VALUE
    val minInt = Int.MIN_VALUE

    val sum = { a: Int, b: Int -> a + b }
    val sum1 = fun(a: Int, b: Int): Int { return a + b }
    val sum2 = fun(a: Int, b: Int) = a + b

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(sum.invoke(1,2))
    }
}
