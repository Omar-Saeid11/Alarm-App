package com.example.alarmapp.composables

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.alarmapp.R
import com.example.alarmapp.util.components.ClockAppBar


@Composable
fun AlarmsListAppBar(
    modifier: Modifier = Modifier,
    clear: () -> Unit,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    ClockAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.alarm),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.clear_alarms)) },
                    onClick = {
                        clear()
                        showMenu = false
                    },
                )
            }
        },
    )
}
