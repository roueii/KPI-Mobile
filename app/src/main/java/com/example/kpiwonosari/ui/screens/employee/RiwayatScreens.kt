package com.example.kpiwonosari.ui.screens.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.RowChevron
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary50
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.Warning500
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

enum class HistoryStatus(val label: String, val color: Color, val text: Color) {
    APPROVED("Disetujui", Success500, White),
    WAITING("Menunggu", Neutral400, White),
    REVISION("Revisi", Warning500, Primary50),
}

data class HistoryEntry(val dayName: String, val day: Int, val status: HistoryStatus)

val historyEntries = listOf(
    HistoryEntry("Kam", 24, HistoryStatus.APPROVED),
    HistoryEntry("Rab", 23, HistoryStatus.WAITING),
    HistoryEntry("Sel", 22, HistoryStatus.REVISION),
    HistoryEntry("Sen", 21, HistoryStatus.APPROVED),
    HistoryEntry("Min", 20, HistoryStatus.APPROVED),
    HistoryEntry("Sab", 19, HistoryStatus.REVISION),
    HistoryEntry("Jum", 18, HistoryStatus.APPROVED),
    HistoryEntry("Kam", 17, HistoryStatus.APPROVED),
)

/** Translucent header with back button, title and subtitle (Riwayat pages). */
@Composable
fun LightBackHeader(title: String, subtitle: String, onBack: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(Color(0xCCF7F9FF)).height(64.dp).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            Modifier.size(44.dp).box(Secondary50, Secondary200).clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) { AppIcon(R.drawable.ic_arrow_left, 24.dp) }
        Column {
            Text(title, style = jakarta(16, FontWeight.SemiBold, 28, -0.5), color = Neutral900)
            Text(subtitle, style = jakarta(12, lineHeight = 16), color = Neutral500)
        }
    }
}

@Composable
fun RiwayatScreen(onBack: () -> Unit, onOpen: (Int) -> Unit) {
    var filter by remember { mutableIntStateOf(0) }
    val filters = listOf<Pair<String, HistoryStatus?>>(
        "Semua (6)" to null,
        "Disetujui (3)" to HistoryStatus.APPROVED,
        "Menunggu (1)" to HistoryStatus.WAITING,
        "Revisi (2)" to HistoryStatus.REVISION,
    )
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        LightBackHeader("Riwayat", "Capaian harian saya", onBack)
        Column(Modifier.verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 24.dp)) {
            // Status counters
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(Triple(HistoryStatus.APPROVED, "3", true), Triple(HistoryStatus.WAITING, "1", false), Triple(HistoryStatus.REVISION, "2", false))
                    .forEach { (s, n, highlight) ->
                        Column(
                            Modifier.weight(1f).box(if (highlight) Secondary50 else Neutral100, if (highlight) Secondary200 else Neutral300).padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(bottom = 4.dp)) {
                                Box(Modifier.size(10.dp).clip(CircleShape).background(s.color))
                                Text(s.label, style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = if (highlight) Secondary500 else Neutral500)
                            }
                            Text(n, style = jakarta(17, FontWeight.SemiBold, 24, -0.085), color = Neutral900)
                        }
                    }
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEachIndexed { i, (label, _) ->
                    val active = i == filter
                    val (bg, border, fg) = when {
                        active -> Triple(Primary500, null, White)
                        i == 0 -> Triple(Secondary50, Secondary200, Secondary500)
                        else -> Triple(Neutral100, Neutral300, Neutral500)
                    }
                    Box(
                        Modifier.height(32.dp).box(bg, border, 9999.dp).clickable { filter = i }.padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center,
                    ) { Text(label, style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = fg) }
                }
            }
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                historyEntries.forEachIndexed { idx, e ->
                    if (filters[filter].second == null || filters[filter].second == e.status) {
                        val strong = e.status == HistoryStatus.APPROVED
                        Row(
                            Modifier.fillMaxWidth().heightIn(min = 56.dp).box(Secondary50, Secondary200).clickable { onOpen(idx) }.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Column(
                                Modifier.width(48.dp).box(if (strong) Secondary50 else Neutral100).padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(e.dayName, style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = if (strong) Secondary500 else Neutral500)
                                Text("${e.day}", style = jakarta(14, FontWeight.SemiBold, 14), color = Neutral900)
                            }
                            Column(Modifier.weight(1f)) {
                                Text("${e.day} Okt 2024", style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral900)
                                Text("4/16 Checklist terisi", style = jakarta(12, lineHeight = 16), color = Neutral500)
                            }
                            Box(Modifier.clip(CircleShape).background(e.status.color).padding(horizontal = 10.dp, vertical = 4.dp)) {
                                Text(e.status.label, style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = e.status.text)
                            }
                            RowChevron()
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Primary500).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text("Indikator Bentuk Status:", style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = Neutral500)
                Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    listOf(Success500 to "Hijau = Disetujui", Neutral400 to "Abu-abu = Menunggu", Primary500 to "Kuning = Revisi").forEach { (c, t) ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(Modifier.size(10.dp).clip(CircleShape).background(c))
                            Text(t, style = jakarta(11, lineHeight = 16), color = Neutral900)
                        }
                    }
                }
            }
        }
    }
}

private val categoryIcons = listOf(
    R.drawable.cat_cash, R.drawable.cat_headset, R.drawable.cat_light_bulb,
    R.drawable.cat_calculator, R.drawable.cat_sparks, R.drawable.cat_shield_check,
)

@Composable
fun DetailRiwayatScreen(role: Role, index: Int, onBack: () -> Unit, onFix: () -> Unit) {
    val entry = historyEntries.getOrElse(index) { historyEntries.first() }
    val revision = entry.status == HistoryStatus.REVISION
    val categories = dashboardFor(role).categories
    Column(Modifier.fillMaxSize().background(White).statusBarsPadding().navigationBarsPadding()) {
        LightBackHeader("Detail Riwayat", "Capaian harian saya", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(7.5.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Riwayat — ${entry.day} Okt", style = jakarta(22, FontWeight.ExtraBold, 27, -0.55), color = Color(0xFF18181B), modifier = Modifier.weight(1f))
                    Box(Modifier.clip(CircleShape).background(entry.status.color).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text(
                            when (entry.status) { HistoryStatus.REVISION -> "PERLU REVISI"; else -> entry.status.label.uppercase() },
                            style = jakarta(10, FontWeight.ExtraBold, 15, 0.5),
                            color = White,
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AppIcon(R.drawable.ic_clock_grey14, 14.dp)
                    Text("Dikirim ${entry.day} Okt 2026, 21:05 WIB", style = jakarta(12, lineHeight = 16), color = Color(0xFF6B7280))
                }
            }
            if (revision) {
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Secondary50)
                        .border(2.dp, Primary500, RoundedCornerShape(16.dp)).padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Column {
                        Row(Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.Top) {
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("CATATAN REVISI", style = jakarta(10, FontWeight.Bold, 15, 0.5), color = Color(0xFF6B7280))
                                Text("Diminta oleh: User (Store Manager)", style = jakarta(12, FontWeight.Bold, 16), color = Color(0xFF111827))
                            }
                            Box(Modifier.box(White, Neutral300, 4.dp).padding(horizontal = 8.dp, vertical = 2.dp)) {
                                Text("25 Okt, 08:30 WIB", style = jakarta(10, FontWeight.Medium, 15), color = Color(0xFF6B7280))
                            }
                        }
                        Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
                    }
                    Box(Modifier.fillMaxWidth().box(White, Neutral300).padding(12.dp)) {
                        Text("\"Foto bukti Penjualan kurang jelas, mohon upload ulang.\"", style = jakarta(12, FontWeight.Medium, 19), color = Color(0xFF1F2937))
                    }
                    Row(
                        Modifier.fillMaxWidth().height(44.dp).box(Primary500).clickable(onClick = onFix),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("PERBAIKI CAPAIAN", style = jakarta(12, FontWeight.Bold, 16, 0.6), color = White)
                        AppIcon(R.drawable.ic_arrow_right_white16, 16.dp)
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row {
                    Text("RINGKASAN LAPORAN HARIAN", style = jakarta(12, FontWeight.Medium, 16, 0.6), color = Neutral500, modifier = Modifier.weight(1f))
                    Text("${categories.size} Kategori", style = jakarta(11, FontWeight.SemiBold, 14, 0.44), color = Neutral500)
                }
                Column(
                    Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    categories.forEachIndexed { i, c ->
                        val flagged = revision && i == 0
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = 64.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(White)
                                .border(if (flagged) 2.dp else 1.dp, if (flagged) Primary500 else Neutral300, RoundedCornerShape(12.dp))
                                .clickable { if (flagged) onFix() }
                                .padding(start = 11.dp, end = 4.dp, top = 12.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Box(Modifier.size(40.dp).box(Secondary50), contentAlignment = Alignment.Center) {
                                AppIcon(categoryIcons[i % categoryIcons.size], 20.dp)
                            }
                            Column(Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(c.name, style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral900)
                                    if (flagged) Box(Modifier.box(Warning500, radius = 4.dp).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text("PERLU REVISI", style = jakarta(9, FontWeight.ExtraBold, 13, 0.45), color = White)
                                    }
                                }
                                Text(
                                    if (flagged) "Perlu Revisi · Foto tidak jelas" else "${c.done.coerceAtLeast(3)}/${c.total.coerceAtLeast(3)} checklist selesai",
                                    style = if (flagged) jakarta(11, FontWeight.Medium, 16) else jakarta(12, lineHeight = 16),
                                    color = if (flagged) Color(0xFF4B5563) else Neutral500,
                                )
                            }
                            AppIcon(R.drawable.ic_chevron_box32, 32.dp)
                        }
                    }
                }
            }
            if (revision) {
                Row(
                    Modifier.fillMaxWidth().box(Secondary50, Primary500).padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    AppIcon(R.drawable.ic_info_20, 20.dp, Modifier.padding(top = 2.dp))
                    Text(
                        "Catatan: Laporan ini perlu direvisi sesuai catatan Store Manager. Perubahan hanya dibuka untuk kategori yang membutuhkan perbaikan.",
                        style = jakarta(11, lineHeight = 18),
                        color = Color(0xFF4B5563),
                    )
                }
            }
        }
    }
}
