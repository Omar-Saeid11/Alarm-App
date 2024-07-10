package com.example.alarmapp.util.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.Screen
import com.example.alarmapp.ui.theme.AlarmAppTheme

@Preview
@Composable
private fun BottomNavigationPreview() {
    AlarmAppTheme {
        BottomNavigationBar(
            listBottomBarItems = listBottomBarItems,
            navController = rememberNavController(),
        )
    }
}

@Preview
@Composable
private fun BottomNavigationPreviewDark() {
    AlarmAppTheme(darkTheme = true) {
        BottomNavigationBar(
            listBottomBarItems = listBottomBarItems,
            navController = rememberNavController(),
        )
    }
}

data class BottomBarItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

@Composable
fun BottomNavigationBar(
    listBottomBarItems: List<BottomBarItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(modifier = modifier) {
        listBottomBarItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = item.name)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                    )
                },
            )
        }
    }
}

val listBottomBarItems = listOf(
    BottomBarItem(
        name = "Alarm",
        route = Screen.AlarmsList.route,
        icon = Icons.Default.Alarm,
    )
)
