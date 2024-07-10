package com.example.alarmapp.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapp.composables.AlarmPicker
import com.example.alarmapp.composables.Buttons
import com.example.alarmapp.composables.CustomizeAlarmEvent
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.ui.theme.AlarmAppTheme
import com.example.alarmapp.ui.theme.Black100
import com.example.alarmapp.ui.theme.Blue100
import com.example.alarmapp.util.components.imePaddingIfTrue

@Composable
fun CreateAlarmScreen(
    modifier: Modifier = Modifier,
    alarmCreationState: Alarm,
    alarmActions: AlarmActions,
    navigateToAlarmsList: () -> Unit = {},
) {
    val cardContainerColor by animateColorAsState(
        targetValue = if (isSystemInDarkTheme()) Black100 else Blue100,
        label = ""
    )
    val isAlarmTitleFocused = rememberSaveable { mutableStateOf(false) }

    Surface(modifier = modifier, color = cardContainerColor) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val alarmPickerPaddingStart =
                if (maxWidth > 400.dp) {
                    dimensionResource(id = com.intuit.sdp.R.dimen._60sdp)
                } else {
                    dimensionResource(id = com.intuit.sdp.R.dimen._35sdp)
                }

            AlarmPicker(
                modifier = Modifier
                    .padding(start = alarmPickerPaddingStart, top = maxHeight / 6),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = { alarmActions.updateAlarmCreationState(it) },
                cardContainerColor = cardContainerColor,
                updateAlarmTitleFocused = { newValue ->
                    isAlarmTitleFocused.value = newValue
                },
            )

            CustomizeAlarmEvent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .imePaddingIfTrue(condition = isAlarmTitleFocused.value)
                    .background(color = MaterialTheme.colorScheme.surface),
                alarmCreationState = alarmCreationState,
                updateAlarmCreationState = { alarmActions.updateAlarmCreationState(it) },
                updateAlarmTitleFocused = { newValue ->
                    isAlarmTitleFocused.value = newValue
                },
            )

            Buttons(
                modifier = Modifier.align(Alignment.BottomCenter),
                navigateToAlarmsList = navigateToAlarmsList,
                save = { alarmActions.save() },
            )
        }
    }
}


@Preview(device = Devices.PIXEL_4_XL)
@Composable
private fun CreateAlarmPreview() {
    AlarmAppTheme {
        CreateAlarmScreen(
            alarmCreationState = Alarm(),
            alarmActions = object : AlarmActions {},
        )
    }
}

@Preview(
    device = Devices.TABLET,
    uiMode = Configuration.ORIENTATION_PORTRAIT,
    widthDp = 768,
    heightDp = 1024
)
@Composable
private fun CreateAlarmDarkPreview() {
    AlarmAppTheme(darkTheme = true) {
        CreateAlarmScreen(
            alarmCreationState = Alarm(),
            alarmActions = object : AlarmActions {},
        )
    }
}