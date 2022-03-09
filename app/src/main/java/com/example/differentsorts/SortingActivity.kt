package com.example.differentsorts

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.example.differentsorts.databinding.ActivitySortingBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class SortingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySortingBinding

    companion object {
        const val SORTING_NAME = "sort_name"
        const val SIZE = 20
    }

    private var mainListOfDigits = ArrayList<Int>()
    private val sorting = Sort()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySortingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sorting = intent.getStringExtra(SORTING_NAME).toString()
        binding.nameOfSorting.text = sorting

        buttonsClickEvent()
    }

    private fun buttonsClickEvent() {
        randomButtonClick()
        backButtonClick()
        sortButtonClick()
    }

    private fun randomButtonClick() {
        binding.randomButton.setOnClickListener {
            mainListOfDigits = makeRandomDigits()
            for ((counter, digit) in binding.digits.referencedIds.withIndex()) {
                val digitView = findViewById<TextView>(digit)
                digitView.text = mainListOfDigits[counter].toString()
                digitView.setTextColor(resources.getColor(R.color.textColor, this.theme))
            }
        }
    }

    private fun sortButtonClick() {
        binding.buttonSort.setOnClickListener {
            sorting.sortingSteps.clear()
            if (binding.nameOfSorting.text.toString() == "QUICK SORT") {
                val left = 0
                val right = SIZE - 1
                sorting.quickSort(mainListOfDigits, left, right)

            }
            animationOfSorting()
        }


    }

    private fun animationOfSorting() {
        val idOfDigits = ArrayList<Int>()
        for (digit in binding.digits.referencedIds) {
            idOfDigits.add(digit)
        }
        var counter = 0
        while (counter != sorting.sortingSteps.size - 1) {
            object : CountDownTimer(3000, 3000) {
                override fun onTick(millisUntilFinished: Long) {
                    val pair = Pair(
                        sorting.sortingSteps[counter].firstIndex,
                        sorting.sortingSteps[counter].secondIndex
                    )
                    val firstDigit: TextView = findViewById(idOfDigits[pair.first])
                    val secondDigit: TextView = findViewById(idOfDigits[pair.second])
                    firstDigit.setTextColor(Color.RED)
                    secondDigit.setTextColor(Color.RED)
                    Handler().postDelayed({
                        firstDigit.text =
                            secondDigit.text.also { secondDigit.text = firstDigit.text }
                    }, 1000)
                    Handler().postDelayed({
                        firstDigit.setTextColor(resources.getColor(R.color.textColor, theme))
                        secondDigit.setTextColor(resources.getColor(R.color.textColor, theme))
                    }, 2000)
                }

                override fun onFinish() {
                }
            }.start()
            counter++
        }


    }

//    private suspend fun animation(firstDigit: TextView, secondDigit: TextView) {
//       withContext(Dispatchers.IO){
//            firstDigit.setTextColor(Color.RED)
//            secondDigit.setTextColor(Color.RED)
//            delay(1000L)
//            firstDigit.text = secondDigit.text.also { secondDigit.text = firstDigit.text }
//            delay(1000L)
//            firstDigit.setTextColor(resources.getColor(R.color.textColor, theme))
//            secondDigit.setTextColor(resources.getColor(R.color.textColor, theme))
//            delay(1000L) }
//
//    }


    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }
    }


    private fun makeRandomDigits(): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in 0 until SIZE) {
            list.add((0..100).random())
        }
        return list
    }


}