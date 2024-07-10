package com.example.alarmapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.model.Alarm
import com.example.alarmapp.screens.AlarmViewModel
import com.example.alarmapp.screens.AlarmsListScreen
import com.example.alarmapp.screens.CreateAlarmScreen
import com.example.alarmapp.ui.theme.AlarmAppTheme
import com.example.alarmapp.util.components.BottomNavigationBar
import com.example.alarmapp.util.components.CheckExactAlarmPermission
import com.example.alarmapp.util.components.RequestNotificationPermission
import com.example.alarmapp.util.components.listBottomBarItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AlarmAppTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()
                val sdkVersion = Build.VERSION.SDK_INT
                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons,
                    )
                    onDispose {}
                }
                if (sdkVersion == Build.VERSION_CODES.S || sdkVersion == Build.VERSION_CODES.S_V2) {
                    CheckExactAlarmPermission()
                } else if (sdkVersion >= Build.VERSION_CODES.TIRAMISU) {
                    RequestNotificationPermission()
                }
                ClockApp()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun ClockApp() {
    val navController = rememberNavController()
    var isFloatButtonVisible by rememberSaveable { (mutableStateOf(true)) }
    var isBottomBarVisible by rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val alarmViewModel: AlarmViewModel = viewModel()

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        this.launch {
            isFloatButtonVisible = when (navBackStackEntry?.destination?.route) {
                Screen.AlarmsList.route -> true
                else -> false
            }
            isBottomBarVisible = when (navBackStackEntry?.destination?.route) {
                Screen.CreateAlarm.route -> false
                else -> true
            }
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                BottomNavigationBar(
                    modifier = Modifier.navigationBarsPadding(),
                    listBottomBarItems = listBottomBarItems,
                    navController = navController,
                )
            }
        },

        floatingActionButton = {
            AnimatedVisibility(
                visible = isFloatButtonVisible,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "AddAlarm"
                    },
                    text = { Text(text = stringResource(id = R.string.add_alarm)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddAlarm,
                            contentDescription = null,
                        )
                    },
                    onClick = {
                        alarmViewModel.updateAlarmCreationState(Alarm())
                        navController.navigate(Screen.CreateAlarm.route)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            }
        },
        content = {
            Navigation(navController = navController, modifier = Modifier.padding(it))
        },
    )
}

@OptIn(ExperimentalTime::class)
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val alarmViewModel: AlarmViewModel = viewModel()
    val alarmsListState by alarmViewModel.alarmsListState.observeAsState()
    val alarmCreationState = alarmViewModel.alarmCreationState

    NavHost(navController = navController, startDestination = Screen.AlarmsList.route) {
        composable(
            route = Screen.AlarmsList.route,
            deepLinks = Screen.alarmListDeepLink,
        ) {
            alarmsListState?.let {
                AlarmsListScreen(
                    modifier = modifier,
                    alarmActions = alarmViewModel,
                    alarmsListState = it,
                    navigateToCreateAlarm = { navController.navigate(Screen.CreateAlarm.route) },
                )
            }
        }


        composable(Screen.CreateAlarm.route) {
            CreateAlarmScreen(
                modifier = modifier,
                alarmActions = alarmViewModel,
                alarmCreationState = alarmCreationState,
                navigateToAlarmsList = { navController.navigate(Screen.AlarmsList.route) },
            )
        }
    }
}