package com.example.differentsorts

class Sort {
    var sortingSteps = ArrayList<SortingStep>()

    fun quickSort(list: ArrayList<Int>, left: Int, right: Int) {
        var l = left
        var r = right
        val middle = (list[left] + list[right] + list[(left + right) / 2]) / 3

        while (l <= r) {
            while(list[l] < middle) {
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
}
