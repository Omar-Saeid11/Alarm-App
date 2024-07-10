package com.example.alarmapp.composables

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.screens.AlarmActions
import com.example.alarmapp.util.checkDate
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun AlarmsList(
    modifier: Modifier = Modifier,
    alarmActions: AlarmActions,
    alarmsListSate: List<Alarm>,
    navigateToCreateAlarm: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    BoxWithConstraints(modifier = modifier) {
        LazyColumn(
            state = scrollState,
        ) {
            items(items = alarmsListSate, key = { alarmItem -> alarmItem.id }) { item ->
                var isScheduled by rememberSaveable { mutableStateOf(item.isScheduled) }
                val removeAlarm = SwipeAction(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inversePrimary,
                        )
                    },
                    isUndo = false,
                    background = MaterialTheme.colorScheme.error,
                    onSwipe = { alarmActions.remove(alarm = item) },
                )
                SwipeableActionsBox(
                    endActions = listOf(removeAlarm),
                    backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.background,
                    swipeThreshold = maxWidth / 2,
                ) {
                    Alarm(
                        alarm = item,
                        onScheduledChange = {
                            isScheduled = it
                            val description =
                                if (item.description.any { char -> char.isDigit() }) item.checkDate() else item.description
                            alarmActions.update(
                                item.copy(
                                    isScheduled = isScheduled,
                                    description = description,
                                ),
                            )
                        },
                        updateAlarmCreationState = { alarmActions.updateAlarmCreationState(it) },
                        navigateToCreateAlarm = navigateToCreateAlarm,
                    )
                }
            }
        }
    }
}