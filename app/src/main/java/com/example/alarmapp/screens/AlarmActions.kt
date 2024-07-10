package com.example.alarmapp.screens

import com.example.alarmapp.model.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm) {}
    fun update(alarm: Alarm) {}
    fun remove(alarm: Alarm) {}
    fun save() {}
    fun clear() {}
}