package com.example.differentsorts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.differentsorts.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsClickListener()

    }

    private fun buttonsClickListener() {
        for (button in binding.selectorButtonsGroup.referencedIds) {
            val btn = findViewById<Button>(button)
            btn.setOnClickListener {
                val intent = Intent(this, SortingActivity::class.java)
                intent.putExtra(SortingActivity.SORTING_NAME, btn.text.toString())
                startActivity(intent)
            }
        }
    }
}