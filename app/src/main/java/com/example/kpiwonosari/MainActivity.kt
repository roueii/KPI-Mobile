package com.example.kpiwonosari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.graphics.Color as AndroidColor
import com.example.kpiwonosari.navigation.Navigator
import com.example.kpiwonosari.navigation.Screen
import com.example.kpiwonosari.ui.AppRouter
import com.example.kpiwonosari.ui.components.SideMenu
import com.example.kpiwonosari.ui.theme.KPIWONOSARITheme
import com.example.kpiwonosari.ui.theme.Neutral50

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT),
        )
        setContent {
            KPIWONOSARITheme { KpiApp() }
        }
    }
}

@Composable
fun KpiApp() {
    val nav = remember { Navigator() }
    var menuOpen by remember { mutableStateOf(false) }

    BackHandler(enabled = menuOpen || nav.stack.size > 1) {
        if (menuOpen) menuOpen = false else nav.back()
    }

    Box(Modifier.fillMaxSize().background(Neutral50)) {
        AnimatedContent(
            targetState = nav.current,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen",
        ) { screen ->
            AppRouter(screen, nav, onMenu = { menuOpen = true })
        }
        if (nav.current != Screen.Splash && nav.current != Screen.Login) {
            SideMenu(
                visible = menuOpen,
                role = nav.role,
                current = nav.current,
                onClose = { menuOpen = false },
                onSelect = {
                    menuOpen = false
                    nav.goTop(it)
                },
            )
        }
    }
}
