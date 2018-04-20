package iii_conventions


data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year > other.year) return 1
        if (year < other.year) return -1
        if (month > other.month) return 1
        if (month < other.month) return -1
        if (dayOfMonth > other.dayOfMonth) return 1
        if (dayOfMonth < other.dayOfMonth) return -1
        return 0
    }

    operator fun plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)
    operator fun plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate = addTimeIntervals(repeatedTimeInterval.interval, repeatedTimeInterval.frequency)

}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(start = this, endInclusive = other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(i: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, i)
}

class RepeatedTimeInterval(val interval: TimeInterval, val frequency: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterator<MyDate> {
    private var current: MyDate = start

    override fun hasNext(): Boolean {
         return current <= endInclusive
    }

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}
