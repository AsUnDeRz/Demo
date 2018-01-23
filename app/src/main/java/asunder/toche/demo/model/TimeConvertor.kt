package asunder.toche.demo.model

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*





/**
 *Created by ToCHe on 22/1/2018 AD.
 */
object TimeConvertor {
    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale("th"))

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}