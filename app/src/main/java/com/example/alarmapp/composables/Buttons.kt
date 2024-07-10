package com.example.alarmapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.alarmapp.R

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
    navigateToAlarmsList: () -> Unit,
    save: () -> Unit,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TextButton(
                onClick = {
                    navigateToAlarmsList()
                },
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
            TextButton(
                onClick = {
                    save()
                    navigateToAlarmsList()
                },
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}
