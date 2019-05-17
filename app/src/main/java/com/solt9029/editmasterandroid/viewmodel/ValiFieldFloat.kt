package com.solt9029.editmasterandroid.viewmodel

import com.mlykotom.valifi.fields.number.ValiFieldNumber

class ValiFieldFloat : ValiFieldNumber<Float> {
    constructor() : super() {}
    constructor(defaultValue: Float?) : super(defaultValue) {}

    @Throws(NumberFormatException::class)
    override fun parse(value: String?): Float? {
        return java.lang.Float.parseFloat(value!!)
    }
}
