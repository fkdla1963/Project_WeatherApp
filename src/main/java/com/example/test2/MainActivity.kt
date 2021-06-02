package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn_click_me = findViewById(R.id.Button1) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()


            val btn_click = findViewById(R.id.Button2) as Button
            // set on-click listener
            btn_click.setOnClickListener {
                // your code to perform when the user clicks on the button
                Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}