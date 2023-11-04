package com.project.myspending

import androidx.annotation.Keep
import com.project.myspending.util.DateUtil
import java.io.Serializable
import java.util.*

@Keep
data class Spending(
    var id: String = "",
    var purpose: String = "",
    var amount: Long = 0,
    var date: Date = DateUtil.getCurrentDate(),
) : Serializable
