package com.hackerearth.searchbar.views.filters

import io.reactivex.Observable
import java.util.*

class Test {
    internal var name: String? = null

    fun test(isSorted: Boolean) {
        val record = ArrayList<Test>()
        Observable.fromIterable(record).sorted { o1, o2 ->
            if (isSorted) {
                o1.name!!.length - o2.name!!.length
            } else {
                o2.name!!.length - o1.name!!.length
            }
        }.filter { test -> test.name!!.length < 20 }
            .toList().subscribe { tests, throwable -> }
    }
}
