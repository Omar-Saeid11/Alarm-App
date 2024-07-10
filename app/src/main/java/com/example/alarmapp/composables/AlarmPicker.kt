package com.example.alarmapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.util.checkDate
import com.example.alarmapp.util.checkNumberPicker
import com.example.alarmapp.util.components.NumberPicker
import kotlinx.coroutines.launch

@Composable
 fun AlarmPicker(
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
    modifier: Modifier = Modifier,
    updateAlarmTitleFocused: (Boolean) -> Unit,
    cardContainerColor: androidx.compose.ui.graphics.Color,
) {
    val textStyle = MaterialTheme.typography.displaySmall

    var hours by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(alarmCreationState.hour))
    }
    var minutes by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(alarmCreationState.minute))
    }

    LaunchedEffect(hours, minutes) {
        this.launch {
            if (alarmCreationState.description.any { char -> char.isDigit() }) {
                updateAlarmCreationState(
                    alarmCreationState.copy(description = alarmCreationState.checkDate()),
                )
            }
        }
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            NumberPicker(
                modifier = Modifier.weight(1f),
                number = hours,
                timeUnit = "Hours",
                onNumberChange = { value ->
                    if (value.text.checkNumberPicker(maxNumber = 23)) {
                        hours = value
                        updateAlarmCreationState(alarmCreationState.copy(hour = hours.text))
                    }
                    updateAlarmTitleFocused(false)
                },
                textStyle = textStyle,
                backgroundColor = cardContainerColor,
            )

            Text(
                text = ":",
                style = textStyle,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 20.dp),
            )

            NumberPicker(
                modifier = Modifier.weight(1f),
                number = minutes,
                timeUnit = "Minutes",
                textStyle = textStyle,
                backgroundColor = cardContainerColor,
                onNumberChange = { value ->
                    if (value.text.checkNumberPicker(maxNumber = 59)) {
                        minutes = value
                        updateAlarmCreationState(alarmCreationState.copy(minute = minutes.text))
                    }
                    updateAlarmTitleFocused(false)
                },
            )
        }
    }
}