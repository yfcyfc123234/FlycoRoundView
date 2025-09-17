package com.flyco.roundviewsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.flyco.roundview.RoundTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.rtv_1).setOnClickListener {
            Toast.makeText(this@MainActivity, "Click--->RoundTextView_1", Toast.LENGTH_SHORT).show()
        }

        val rtv2 = findViewById<View?>(R.id.rtv_2) as RoundTextView
        rtv2.setOnLongClickListener {
            Toast.makeText(this@MainActivity, "LongClick--->RoundTextView_2", Toast.LENGTH_SHORT).show()
            false
        }

        val rtv3 = findViewById<View?>(R.id.rtv_3) as RoundTextView
        rtv3.setOnClickListener {
            val delegate = rtv3.delegate
            delegate.backgroundColor = if (delegate.backgroundColor == "#FFFFFF".toColorInt()) {
                "#F6CE59".toColorInt()
            } else {
                "#FFFFFF".toColorInt()
            }
        }
    }
}
