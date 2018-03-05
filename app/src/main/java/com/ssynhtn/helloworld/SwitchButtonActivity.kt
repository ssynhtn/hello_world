package com.ssynhtn.helloworld

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.kyleduo.switchbutton.SwitchButton

class SwitchButtonActivity : AppCompatActivity() {

    val TAG = "SwitchButtonActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_button)

        val switchButton: SwitchButton = findViewById(R.id.sb_ios)

        switchButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "is checked ${switchButton.isChecked}")
            Log.d(TAG, "width " + switchButton.width + ", height " + switchButton.height + ", ratio " + (switchButton.width * 1.0f / switchButton.height))
        })

    }
}
