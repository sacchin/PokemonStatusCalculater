package com.gmail.sacchin.myapplication

import io.realm.RealmObject

open class Speed(
        open var detail: String = "-",
        open var bs: Int = 0,
        open var ev: Int = 0,
        open var av: Int = 0,
        open var characteristic: Boolean = false,
        open var other: Int = 0
) : RealmObject() {

    companion object {
        val SINGLE = 0
        val HALF = 1
        val DOUBLE = 2
        val OTHER_CORRECTION = arrayOf(SINGLE, HALF, DOUBLE)
    }

    fun charCorrection(): Double {
        return if (characteristic) 1.1 else 1.0
    }

    fun otherCorrection(): Double {
        return when (other) {
            SINGLE -> 1.0
            HALF -> 1.5
            else -> 2.0
        }
    }

    fun key(): String {
        var base = "${bs}族${ev}振"
        if (characteristic) base += "補正有"
        if (other != SINGLE) base += "${otherCorrection()}倍"
        return base
    }

}