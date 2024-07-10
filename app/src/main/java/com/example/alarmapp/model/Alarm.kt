package com.example.alarmapp.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "alarms_list_table",
    indices = [
        Index(
            value = ["hour", "minute", "title", "description", "daysSelectedJson"],
            unique = true
        )
    ]
)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var hour: String = DEFAULT_HOUR,
    var minute: String = DEFAULT_MINUTE,
    var title: String = "",
    var description: String = "Tomorrow-${DEFAULT_NEXT_DAY.format(DEFAULT_DATE_TIME_FORMATTER)}",
    var isScheduled: Boolean = false,
    var isRecurring: Boolean = false,
    var daysSelectedJson: String = DEFAULT_DAYS_SELECTED_JSON
) {
    companion object {
        private const val DEFAULT_HOUR = "00"
        private const val DEFAULT_MINUTE = "00"
        private val DEFAULT_NEXT_DAY = LocalDate.now().plusDays(1)
        private val DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE")
        private val DEFAULT_DAYS_SELECTED = mapOf(
            "Sun" to false,
            "Mon" to false,
            "Tue" to false,
            "Wed" to false,
            "Thu" to false,
            "Fri" to false,
            "Sat" to false
        )
        private val gson = Gson()
        val DEFAULT_DAYS_SELECTED_JSON: String = gson.toJson(DEFAULT_DAYS_SELECTED)
    }

    val daysSelected: Map<String, Boolean>
        get() = gson.fromJson(daysSelectedJson, object : TypeToken<Map<String, Boolean>>() {}.type)

    fun setDaysSelected(daysSelected: Map<String, Boolean>) {
        this.daysSelectedJson = gson.toJson(daysSelected)
    }
}

