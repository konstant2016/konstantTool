package com.konstant.toollite.activity

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_compass.*

/**
 * 描述:指南针页面
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */


@SuppressLint("MissingSuperCall")
class CompassActivity : BaseActivity() {

    // 当前角度
    private var currentDegree = 0f

    // 获取系统传感器
    private val mSensorManager: SensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    // 指南针传感器监听者
    private val mSensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            val degree = event.values[0]        // 水平方向的角度
            startRotate(degree)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        setTitle("指南针")
        initBaseViews()
    }

    // 旋转图片动画
    private fun startRotate(degree: Float) {
        val animation = RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f)
        animation.duration = 50
        img_compass.startAnimation(animation)
        currentDegree = -degree
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(mSensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME)
    }


    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(mSensorEventListener)
    }
}
