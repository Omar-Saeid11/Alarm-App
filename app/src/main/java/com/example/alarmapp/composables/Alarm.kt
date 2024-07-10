package com.example.alarmapp.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.outlined.AlarmOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.ui.theme.Black100
import com.example.alarmapp.ui.theme.Blue100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Alarm(
    alarm: Alarm,
    navigateToCreateAlarm: () -> Unit,
    onScheduledChange: (Boolean) -> Unit,
    updateAlarmCreationState: (Alarm) -> Unit,
) {
    val cardContainerColor by animateColorAsState(targetValue = if (isSystemInDarkTheme()) Black100 else Blue100)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor),
        onClick = {
            navigateToCreateAlarm()
            updateAlarmCreationState(alarm)
        },

        ) {
        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlarmInfo(
                modifier = Modifier.weight(2f),
                time = "${alarm.hour}:${alarm.minute}",
                title = alarm.title,
                isScheduled = alarm.isScheduled,
            )

            Text(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 5.dp),
                text = alarm.description.substringAfter("-"),
            )
            IconToggleButton(
                modifier = Modifier.weight(1f),
                checked = alarm.isScheduled,
                onCheckedChange = { onScheduledChange(it) },
            ) {
                if (alarm.isScheduled) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Filled.AlarmOn,
                        contentDescription = "AlarmOn",
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Outlined.AlarmOff,
                        contentDescription = "AlarmOff",
                    )
                }
            }
        }
    }
}