package com.example.alarmapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.alarmapp.model.Alarm

@Composable
fun CustomizeAlarmEvent(
    modifier: Modifier,
    alarmCreationState: Alarm,
    updateAlarmTitleFocused: (Boolean) -> Unit,
    updateAlarmCreationState: (Alarm) -> Unit,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                Text(
                    modifier = Modifier.padding(dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
                    text = alarmCreationState.description,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)))
            WeekDays(
                modifier = Modifier.fillMaxWidth(),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = updateAlarmCreationState,
            )
            AlarmTitle(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = updateAlarmCreationState,
                updateAlarmTitleFocused = updateAlarmTitleFocused,
            )
        }
    }
}
