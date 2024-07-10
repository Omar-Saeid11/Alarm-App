package com.example.alarmapp.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.alarmapp.model.Alarm
import kotlinx.coroutines.launch

@Composable
fun AlarmTitle(
    modifier: Modifier = Modifier,
    alarmCreationState: Alarm,
    updateAlarmCreationState: (Alarm) -> Unit,
    updateAlarmTitleFocused: (Boolean) -> Unit,
) {
    var title by remember { mutableStateOf(alarmCreationState.title) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        if (isFocused) {
            this.launch {
                updateAlarmTitleFocused(true)
            }
        }
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            value = title,
            onValueChange = {
                title = it
                updateAlarmCreationState(alarmCreationState.copy(title = title))
            },
            label = { Text("Alarm name") },
            interactionSource = interactionSource,
        )
    }
}
