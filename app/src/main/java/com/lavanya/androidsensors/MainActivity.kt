package com.lavanya.androidsensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_GRAVITY
import android.hardware.Sensor.TYPE_PROXIMITY
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val listOfSensors = sm.getSensorList(Sensor.TYPE_ALL)
//        listOfSensors.forEach {
//            Log.e("TAG", "--------------")
//            Log.e("TAG", "Name : " + it.name)
//            Log.e("TAG", "Type : " + it.stringType)
//            Log.e("TAG", "Vendor : " + it.vendor)
//            Log.e("TAG", "Max Event Count : " + it.fifoMaxEventCount.toString())
//            Log.e("TAG", "Sensor ID : " + it.id.toString())
//            Log.e("TAG", "Dynamic sensor : " + it.isDynamicSensor)
//            Log.e("TAG", "Is Wakeup : " + it.isWakeUpSensor)
//            Log.e("TAG", "Max Delay" + it.maxDelay)
//            Log.e("TAG", "Min Delay : " + it.minDelay)
//            Log.e("TAG", "Max Range : " + it.maximumRange)
//            Log.e("TAG", "Power consumption : " + it.power)
//            Log.e("TAG", "Version : " + it.version)
//            Log.e("TAG", "--------------")
    }

    override fun onStart() {
        super.onStart()
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val accelSensor = sm.getDefaultSensor(TYPE_GRAVITY)

        val proximitySensor = sm.getDefaultSensor(TYPE_PROXIMITY)
        sm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_UI)
        sm.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //This is called whenever the accuracy of the sensor in use changes.
        //This can happen when the user undergoes a transition from high-low battery and vice versa
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                TYPE_GRAVITY -> {
                    Log.e("TAG", "acceleration in x : " + it.values[0])
                    Log.e("TAG", "acceleration in y : " + it.values[1])
                    Log.e("TAG", "acceleration in z : " + it.values[2])

                    val red = (Math.abs(it.values[0]) * 255 / SensorManager.GRAVITY_EARTH).toInt()
                    val green = (Math.abs(it.values[1]) * 255 / SensorManager.GRAVITY_EARTH).toInt()
                    val blue = (Math.abs(it.values[2]) * 255 / SensorManager.GRAVITY_EARTH).toInt()

                    val color = Color.rgb(red, green, blue)
                    parentLayout.setBackgroundColor(color)
                }
                TYPE_PROXIMITY -> {
                    Log.e("TAG", "Distance from the sensor : " + it.values[0])
                }
                else -> Toast.makeText(this, "Unsupported sensor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sm.unregisterListener(this)
    }
}