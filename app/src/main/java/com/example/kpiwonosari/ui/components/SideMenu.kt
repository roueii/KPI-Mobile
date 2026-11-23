package com.example.kpiwonosari.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.navigation.Screen
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Primary200
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.jakarta

data class MenuItem(
    @DrawableRes val icon: Int,
    val iconSize: Dp,
    val label: String,
    val screen: Screen,
)

fun menuItemsFor(role: Role): List<MenuItem> {
    val home = MenuItem(R.drawable.ic_home, 16.dp, "Beranda", Screen.Dashboard)
    val profil = MenuItem(R.drawable.ic_profile_circle, 16.dp, "Profil", Screen.Profil)
    val riwayat = MenuItem(R.drawable.m_clock, 16.dp, "Riwayat", Screen.Riwayat)
    return when (role) {
        Role.ADMIN -> listOf(
            home,
            MenuItem(R.drawable.m_page_search, 18.dp, "Tinjau Revisi", Screen.TinjauRevisi),
            MenuItem(R.drawable.m_group, 18.dp, "Kelola Akun", Screen.KelolaAkun),
            MenuItem(R.drawable.m_okrs, 18.dp, "Kelola KPI", Screen.KelolaKpi),
            MenuItem(R.drawable.m_user_badge_check, 18.dp, "Approve Laporan", Screen.ApproveLaporan),
            profil,
        )
        else -> listOf(
            home,
            MenuItem(R.drawable.ic_page_edit_dark, 16.dp, "Input Capaian", Screen.InputCapaian),
            riwayat,
            profil,
        )
    }
}

/** Slide-in navigation panel matching the "Menu" frames (340dp wide). */
@Composable
fun SideMenu(
    visible: Boolean,
    role: Role,
    current: Screen,
    onClose: () -> Unit,
    onSelect: (Screen) -> Unit,
) {
    AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
        Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.72f)).tapNoRipple(onClose))
    }
    AnimatedVisibility(
        visible,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it },
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .width(340.dp)
                .background(Neutral50)
                .border(1.dp, Neutral300)
                .tapNoRipple { }
                .safeDrawingPadding(),
        ) {
            Row(
                Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Text("CAPAIAN.ID", style = jakarta(16, FontWeight.Bold, 22, -0.4), color = Neutral800)
                    Text("KPI Toko Roti Wonosari", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral500)
                }
                Box(
                    Modifier.size(44.dp).box(Neutral100, Neutral300).clickable(onClick = onClose),
                    contentAlignment = Alignment.Center,
                ) { AppIcon(R.drawable.ic_cancel, 24.dp) }
            }
            Row(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .box(Secondary50, Secondary200)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(Modifier.size(40.dp).clip(CircleShape).background(Primary500), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_user, 24.dp)
                }
                Column {
                    Text(role.greetingName, style = jakarta(14, FontWeight.SemiBold, 20, 0.14), color = Neutral800)
                    Text(role.label, style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral500)
                }
            }
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                menuItemsFor(role).forEach { item ->
                    val active = item.screen == current
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 48.dp)
                            .box(if (active) Secondary50 else Neutral100, if (active) Primary200 else Neutral300)
                            .clickable { onSelect(item.screen) }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(Modifier.size(18.dp), contentAlignment = Alignment.Center) { AppIcon(item.icon, item.iconSize) }
                        Text(
                            item.label,
                            style = jakarta(14, FontWeight.SemiBold, 20, 0.14),
                            color = Neutral800,
                            modifier = Modifier.weight(1f).padding(start = 16.dp),
                        )
                        RowChevron()
                    }
                }
            }
            Box(Modifier.padding(16.dp).fillMaxWidth().box(Neutral100, Neutral300).padding(8.dp)) {
                Text("KPI Toko Roti Wonosari", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral500)
            }
        }
    }
}
