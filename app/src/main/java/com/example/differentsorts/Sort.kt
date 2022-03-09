package com.example.differentsorts

class SortLogic {
    fun quickSort(list: MutableList<Int>, left: Int, right: Int): Pair<Int, Int> {
        var l = left
        var r = right
        var middle = (list[left] + list[right] + list[(left + right) / 2]) / 3

        while (l <= r) {
            while(list[l] < middle) {
                l++
            }
            while (list[r] > middle) {
                r--
            }

            if (l <= r) {
                val temp = list[l]
                list[l] = list[r]
                list[r] = temp
                l++
                r--
            }
        }
        return Pair(l, r)
    }
}