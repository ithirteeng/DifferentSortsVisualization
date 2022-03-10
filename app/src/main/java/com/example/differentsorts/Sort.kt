package com.example.differentsorts

class Sort {
    var sortingSteps = ArrayList<SortingStep>()

    fun quickSort(list: ArrayList<Int>, left: Int, right: Int) {
        var l = left
        var r = right
        val middle = (list[left] + list[right] + list[(left + right) / 2]) / 3

        while (l <= r) {
            while (list[l] < middle) {
                l++
            }
            while (list[r] > middle) {
                r--
            }

            if (l <= r) {
                list[l] = list[r].also { list[r] = list[l] }
                sortingSteps.add(SortingStep(l, r))
                l++
                r--
            }
        }

        if (r > left) {
            quickSort(list, left, r)
        }
        if (l < right) {
            quickSort(list, l, right)
        }
    }


    fun heapSort(list: ArrayList<Int>, size: Int) {
        for (i in size / 2 - 1 downTo 0) heapBuilder(list, size, i)

        for (i in size - 1 downTo 0) {
            list[0] = list[i].also { list[i] = list[0] }
            sortingSteps.add(SortingStep(0, i))
            heapBuilder(list, i, 0)
        }
    }

    private fun heapBuilder(list: ArrayList<Int>, size: Int, index: Int) {
        var maxDigitIndex = index
        val leftChildIndex = 2 * index + 1
        val rightChildIndex = 2 * index + 2

        if (leftChildIndex < size && list[leftChildIndex] > list[maxDigitIndex]) maxDigitIndex =
            leftChildIndex

        if (rightChildIndex < size && list[rightChildIndex] > list[maxDigitIndex]) maxDigitIndex =
            rightChildIndex

        if (maxDigitIndex != index) {
            list[index] = list[maxDigitIndex].also { list[maxDigitIndex] = list[index] }
            sortingSteps.add(SortingStep(index, maxDigitIndex))
            heapBuilder(list, size, maxDigitIndex)
        }
    }


    fun mergeSort(list: ArrayList<Int>, size: Int, start: Int, end: Int) {
        if (end - start < 2) {
            return
        }
        if (end - start == 2) {
            if (list[start] > list[start + 1]) {
                sortingSteps.add(SortingStep(start, start + 1))
                return
            }
        }

        mergeSort(list, size, start, start + (end - start) / 2)
        mergeSort(list, size, start + (end - start) / 2, end)

        val tempArray = ArrayList<Int>()
        var firstHalfStart = start
        val firstHalfEnd = start + (start + end) / 2
        var secondHalfStart = start + (start + end) / 2
        var counter = 0

        while (counter < end - start) {
            if (firstHalfStart >= firstHalfEnd || (secondHalfStart < end && list[secondHalfStart] <= list[firstHalfStart])) {
                tempArray[counter] = list[secondHalfStart]
                secondHalfStart++
                counter++
            } else {
                tempArray[counter] = list[firstHalfStart]
                firstHalfStart++
                counter++
            }
        }

        for (i in start until end) {
            list[i] = tempArray[i - start]
            sortingSteps.add(SortingStep(i, i - start))
        }
    }

}
