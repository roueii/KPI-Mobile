package com.example.kpiwonosari.ui.screens.admin

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Screen
import com.example.kpiwonosari.ui.components.AppFooter
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.Chip
import com.example.kpiwonosari.ui.components.DashboardHeader
import com.example.kpiwonosari.ui.components.ProgressBar
import com.example.kpiwonosari.ui.components.SectionTitle
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.SecondaryCard
import com.example.kpiwonosari.ui.theme.TextMuted
import com.example.kpiwonosari.ui.theme.TextStrong
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

/** Small grey "›" chevron used on dashboard rows/cards. */
@Composable
fun SmallChevron() {
    Box(Modifier.size(4.dp, 6.dp), contentAlignment = Alignment.Center) {
        // asset is drawn pointing left; the design flips it to point right
        AppIcon(
            R.drawable.ic_chevron_small,
            6.dp,
            Modifier.size(6.dp, 7.5.dp).graphicsLayer(scaleX = -1f),
        )
    }
}

/** One "Perlu Perhatian" row: icon tile, title/subtitle, big count and chevron. */
@Composable
fun AttentionRow(
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
    count: String,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .box(White, Neutral300, 14.dp)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(40.dp).box(Secondary100), contentAlignment = Alignment.Center) { AppIcon(icon, 20.dp) }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = jakarta(14, FontWeight.SemiBold, 20), color = TextStrong)
            Text(subtitle, style = jakarta(12, lineHeight = 16), color = TextMuted)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(count, style = jakarta(20, FontWeight.Bold, 24), color = TextStrong)
            SmallChevron()
        }
    }
}

/** Shortcut card in the "Menu" grid. */
@Composable
fun ShortcutCard(
    @DrawableRes icon: Int,
    iconSize: Dp,
    title: String,
    modifier: Modifier = Modifier,
    badge: String? = null,
    meta: String? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier
            .box(SecondaryCard, Secondary200, 16.dp)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(32.dp).box(Secondary50, Secondary500), contentAlignment = Alignment.Center) {
                AppIcon(icon, iconSize)
            }
            Spacer(Modifier.weight(1f))
            if (badge != null) Chip(badge, Primary500, White)
            if (meta != null) Text(meta, style = jakarta(12, lineHeight = 16), color = TextMuted)
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(title, style = jakarta(14, FontWeight.Bold, 20), color = TextStrong, modifier = Modifier.weight(1f), maxLines = 1)
            SmallChevron()
        }
    }
}

data class DivisionProgress(val name: String, val ratio: String, val fraction: Float)

val adminDivisions = listOf(
    DivisionProgress("Store Manager (2 staf)", "2/2", 1f),
    DivisionProgress("Frontliner (8 staf)", "5/6", 286f / 346f),
    DivisionProgress("Produksi (7 staf)", "6/8", 244f / 346f),
    DivisionProgress("Driver (1 staf)", "2/3", 274f / 346f),
    DivisionProgress("Sales & Marketing (1 staf)", "1/2", 175f / 346f),
    DivisionProgress("SPV (1 staf)", "1/2", 174f / 346f),
)

/** Brown-outlined card used for dashboard sections. */
@Composable
fun SectionCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { content() }
}

@Composable
fun DivisionRows(divisions: List<DivisionProgress>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        divisions.forEach { d ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Text(d.name, style = jakarta(13, FontWeight.SemiBold, 18), color = TextStrong, modifier = Modifier.weight(1f))
                    Text(d.ratio, style = jakarta(13, FontWeight.Bold, 18), color = TextStrong)
                }
                ProgressBar(d.fraction)
            }
        }
    }
}

@Composable
fun ProgressLegend() {
    Row(
        Modifier.fillMaxWidth().box(Secondary50, Secondary200, 12.dp).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LegendDot(Primary500, "Tercapai")
        Spacer(Modifier.weight(1f))
        LegendDot(Secondary200, "Proses")
    }
}

@Composable
fun LegendDot(color: androidx.compose.ui.graphics.Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(Modifier.size(10.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Text(label, style = jakarta(12, lineHeight = 16), color = TextMuted)
    }
}

@Composable
fun AdminDashboardScreen(
    onMenu: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Neutral50)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DashboardHeader("Halo, Admin", "Admin", onMenu)
        Column(
            Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            SectionCard {
                SectionTitle("Perlu Perhatian", "Ringkasan tindakan hari ini") {
                    Chip("Prioritas", Secondary100, Secondary500)
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AttentionRow(
                        R.drawable.ic_user_badge_check,
                        "Menunggu Approval Final",
                        "Sudah disetujui Store Manager",
                        "6",
                    ) { onNavigate(Screen.ApproveLaporan) }
                    AttentionRow(
                        R.drawable.ic_page_edit,
                        "Perlu Revisi",
                        "Belum ditindaklanjuti > 2 hari",
                        "3",
                    ) { onNavigate(Screen.TinjauRevisi) }
                }
            }
            SectionCard {
                SectionTitle("Ringkasan Divisi", "6 divisi · 23 karyawan")
                DivisionRows(adminDivisions)
                ProgressLegend()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .box(Primary500)
                        .clickable { onNavigate(Screen.DetailRingkasanDivisi) }
                        .padding(vertical = 13.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Lihat Detail Rekap Bulanan", style = jakarta(13, FontWeight.SemiBold), color = White)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Menu Admin", style = jakarta(18, FontWeight.Bold, 24), color = TextStrong, modifier = Modifier.weight(1f))
                    Text("Pintasan Cepat", style = jakarta(13, lineHeight = 16), color = TextMuted)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShortcutCard(R.drawable.ic_page_search, 18.dp, "Tinjau Revisi", Modifier.weight(1f), badge = "3 aktif") {
                        onNavigate(Screen.TinjauRevisi)
                    }
                    ShortcutCard(R.drawable.ic_group, 20.dp, "Kelola Akun", Modifier.weight(1f), meta = "23 staf") {
                        onNavigate(Screen.KelolaAkun)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShortcutCard(R.drawable.ic_okrs, 18.dp, "Kelola KPI", Modifier.weight(1f), meta = "6 divisi") {
                        onNavigate(Screen.KelolaKpi)
                    }
                    ShortcutCard(R.drawable.ic_user_badge_check_18, 18.dp, "Approve Laporan", Modifier.weight(1f), badge = "6 antrean") {
                        onNavigate(Screen.ApproveLaporan)
                    }
                }
            }
        }
        AppFooter(onLogout)
    }
}
