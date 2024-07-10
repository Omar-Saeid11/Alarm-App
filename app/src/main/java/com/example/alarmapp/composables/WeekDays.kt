package com.example.alarmapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.util.checkDate
import com.example.alarmapp.util.components.CustomChip
import com.google.gson.Gson

@Composable
fun WeekDays(
    modifier: Modifier = Modifier,
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
) {
    val daysSelected = remember {
        mutableStateMapOf<String, Boolean>().apply {
            putAll(alarmCreationState.daysSelected)
        }
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        daysSelected.forEach { (day, isSelected) ->
            CustomChip(
                isChecked = isSelected,
                text = day,
                onChecked = { isChecked ->
                    daysSelected[day] = isChecked
                    val activeDays = daysSelected.filterValues { it }.keys
                    val description = if (activeDays.isEmpty()) {
                        alarmCreationState.checkDate()
                    } else {
                        activeDays.joinToString(separator = " ")
                    }

                    updateAlarmCreationState(
                        alarmCreationState.copy(
                            description = description,
                            isRecurring = daysSelected.any { it.value },
                            daysSelectedJson = Gson().toJson(daysSelected),
                        ),
                    )
                },
            )
        }
    }
}