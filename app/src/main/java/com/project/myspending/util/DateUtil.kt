package com.project.myspending.util

import java.text.SimpleDateFormat
import java.util.*

/**Provided the general util function for date**/
object DateUtil {
    /**Converts the date to string with particular format**/
    fun getFormattedDate(date: Date): String {
        val simpleDateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return simpleDateFormatter.format(date)
    }

    /**gets the dat full name from the day**/
    fun getDayName(date: Date = getCurrentDate()): String {
        val simpleDateFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormatter.format(date)
    }

    /**get today's date without time**/
    fun getCurrentDate(): Date {
        return timeRemovedDate(Calendar.getInstance().time)
    }

    /**get first date of current month**/
    fun getFirstAndLastDayOfMonth(): Pair<Date, Date> {
        val f = Calendar.getInstance().also {
            it.set(Calendar.DAY_OF_MONTH, it.getActualMinimum(Calendar.DAY_OF_MONTH))
        }.time
        val l = Calendar.getInstance().also {
            it.set(Calendar.DAY_OF_MONTH, it.getActualMaximum(Calendar.DAY_OF_MONTH))
        }.time
        return timeRemovedDate(f) to timeRemovedDate(l)
    }

    /**Removed Time From Date**/
    private fun timeRemovedDate(date: Date): Date {
        val simpleDateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return simpleDateFormatter.parse(
            simpleDateFormatter.format(date)
        )!!
    }

    /**give the incremented new date from the provided date object **/
    fun incrementedDate(date: Date): Date = with(Calendar.getInstance()) {
        time = date
        add(Calendar.DAY_OF_MONTH, 1)
        time
    }

    /**give the decremented new date from the provided date object **/
    fun decrementedDate(date: Date): Date = with(Calendar.getInstance()) {
        time = date
        add(Calendar.DAY_OF_MONTH, -1)
        time
    }
}
