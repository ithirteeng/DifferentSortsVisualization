package com.example.differentsorts

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.example.differentsorts.databinding.ActivitySortingBinding
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

@DelicateCoroutinesApi
class SortingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySortingBinding

    private lateinit var timer: Timer

    companion object {
        const val SORTING_NAME = "sort_name"
        const val SIZE = 20
    }

    private var currentIterationStep = 0

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
            if (binding.nameOfSorting.text.toString() == this.getString(R.string.quicksortButtonText)) {
                val left = 0
                val right = SIZE - 1

                GlobalScope.launch(Dispatchers.IO) {
                    sorting.quickSort(mainListOfDigits, left, right)
                }.invokeOnCompletion {
                    GlobalScope.launch(Dispatchers.Main) {
                        animationOfSorting()
                    }
                }
            }
        }
    }

    private fun animationOfSorting() {
        timer = Timer(::processSortingSteps)
        timer.start()
    }

    private fun processSortingSteps() {
        if (currentIterationStep < sorting.sortingSteps.size) {
            lateinit var firstTextView: TextView
            lateinit var secondTextView: TextView

            val currentSortingStep = sorting.sortingSteps[currentIterationStep]

            with(binding.digits.referencedIds) {
                firstTextView = binding.root.findViewById(this[currentSortingStep.firstIndex])
                secondTextView = binding.root.findViewById(this[currentSortingStep.secondIndex])
            }

            animateTextColor(
                firstTextView,
                this@SortingActivity.getColor(R.color.textColor),
                Color.RED
            )
            animateTextColor(
                secondTextView,
                this@SortingActivity.getColor(R.color.textColor),
                Color.RED
            )

            firstTextView.text =
                secondTextView.text.also { secondTextView.text = firstTextView.text }

            animateTextColor(
                firstTextView,
                Color.RED,
                this@SortingActivity.getColor(R.color.textColor)
            )
            animateTextColor(
                secondTextView,
                Color.RED,
                this@SortingActivity.getColor(R.color.textColor)
            )

            currentIterationStep++
        } else {
            timer.cancel()
            currentIterationStep = 0
        }
    }

    private fun animateTextColor(textView: TextView, previousColor: Int, newColor: Int) {
        val textColorAnimation = ObjectAnimator.ofInt(
            textView, "textColor",
            previousColor,
            newColor
        )

        textColorAnimation.duration = 700
        textColorAnimation.setEvaluator(ArgbEvaluator())
        textColorAnimation.start()
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }
    }


    private fun makeRandomDigits(): java.util.ArrayList<Int> {
        val list = java.util.ArrayList<Int>()
        for (i in 0 until SIZE) {
            list.add((0..100).random())
        }
        return list
    }

    internal class Timer(private val methodToExecute: () -> Unit) :
        CountDownTimer(Int.MAX_VALUE.toLong(), 300) {

        override fun onTick(millisUntilFinished: Long) {
            methodToExecute.invoke()
        }

        override fun onFinish() {}
    }


}