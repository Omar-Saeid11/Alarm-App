package com.example.alarmapp.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
 fun AlarmInfo(
    modifier: Modifier = Modifier,
    time: String,
    title: String,
    isScheduled: Boolean,
) {
    val textColor by animateColorAsState(
        targetValue = if (isScheduled) {
            LocalContentColor.current
        } else {
            LocalContentColor.current.copy(
                alpha = 0.5f,
            )
        },
        label = "",
    )

    Column(modifier = modifier) {
        Text(
            text = time,
            style = MaterialTheme.typography.headlineLarge,
            color = textColor,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
        )
    }
}