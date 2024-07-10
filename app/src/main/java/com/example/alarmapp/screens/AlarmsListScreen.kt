package com.example.alarmapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.alarmapp.composables.AlarmsList
import com.example.alarmapp.composables.AlarmsListAppBar
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.ui.theme.AlarmAppTheme


@Composable
fun AlarmsListScreen(
    modifier: Modifier = Modifier,
    alarmsListState: List<Alarm>,
    alarmActions: AlarmActions,
    navigateToCreateAlarm: () -> Unit = {},
) {
    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize(),
            ) {
                AlarmsListAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    clear = { alarmActions.clear() },
                )
                AlarmsList(
                    alarmsListSate = alarmsListState,
                    alarmActions = alarmActions,
                    navigateToCreateAlarm = navigateToCreateAlarm,
                )
            }
        }
    }
}

val alarmsListPreview = mutableListOf<Alarm>().apply {
    for (i in 0..20) {
        val hour = String.format("%02d", i % 24)
        val minute = "00"
        val title = if (i == 0) "zero" else i.toString()
        add(Alarm(id = i, hour = hour, minute = minute, title = title, isScheduled = true))
    }
}.toList()

@Preview(
    device = Devices.TABLET,
    uiMode = Configuration.ORIENTATION_PORTRAIT,
    widthDp = 768,
    heightDp = 1024
)
@Composable
fun AlarmsListScreenPreview() {
    AlarmAppTheme {
        AlarmsListScreen(
            alarmActions = object : AlarmActions {},
            alarmsListState = alarmsListPreview,
        )
    }
}

@Preview(device = Devices.NEXUS_7)
@Composable
fun AlarmsListScreenDarkPreview() {
    AlarmAppTheme(darkTheme = true) {
        AlarmsListScreen(
            alarmActions = object : AlarmActions {},
            alarmsListState = alarmsListPreview,
        )
    }
}
