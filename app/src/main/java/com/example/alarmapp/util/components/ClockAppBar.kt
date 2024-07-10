package com.example.alarmapp.util.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmapp.ui.theme.AlarmAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier.height(48.dp),
        title = title,
        actions = actions,
    )
}

@Preview
@Composable
private fun ClockAppBarPreview() {
    AlarmAppTheme {
        ClockAppBar(title = { Text("Alarm") })
    }
}

@Preview
@Composable
private fun ClockAppBarPreviewDark() {
    AlarmAppTheme(darkTheme = true) {
        ClockAppBar(title = { Text("Alarm") })
    }
}

