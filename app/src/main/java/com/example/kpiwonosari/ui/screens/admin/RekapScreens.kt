package com.example.kpiwonosari.ui.screens.admin

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.components.AppFooter
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Error600
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral200
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.TextMuted
import com.example.kpiwonosari.ui.theme.TextStrong
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

// ---------------------------------------------------------------------------------------------
// Shared building blocks
// ---------------------------------------------------------------------------------------------

/** Brown-bordered card with the 18sp title / 13sp subtitle group. */
@Composable
fun RekapCard(
    title: String? = null,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (title != null) {
            Row(verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(title, style = jakarta(18, FontWeight.Bold, 24), color = TextStrong)
                    if (subtitle != null) Text(subtitle, style = jakarta(13, lineHeight = 18), color = TextMuted)
                }
                trailing?.invoke()
            }
        }
        content()
    }
}

/** White stat tile: big number + small caption. */
@Composable
fun StatTile(value: String, label: String, modifier: Modifier = Modifier, background: Color = White, border: Color = Neutral300) {
    Column(
        modifier.box(background, border, 14.dp).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(value, style = jakarta(18, FontWeight.Bold, 24), color = TextStrong)
        Text(label, style = jakarta(11, lineHeight = 14), color = TextMuted)
    }
}

/** Two-option segmented control ("Per Karyawan" / "Per Bagian"). */
@Composable
fun Segmented(options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Row(
        Modifier.fillMaxWidth().box(White, Neutral300, 12.dp).padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEachIndexed { i, o ->
            val active = i == selected
            Box(
                Modifier.weight(1f).clip(RoundedCornerShape(9.dp)).background(if (active) Primary500 else Color.Transparent)
                    .clickable { onSelect(i) }.padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(o, style = jakarta(12, if (active) FontWeight.Bold else FontWeight.SemiBold, 16), color = if (active) White else Neutral800)
            }
        }
    }
}

@Composable
fun MonthPicker(selected: Int, onSelect: (Int) -> Unit) {
    val months = listOf("Okt 2025", "Sep 2025", "Agu 2025")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Pilih bulan", style = jakarta(12, FontWeight.SemiBold, 16), color = Neutral800)
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            months.forEachIndexed { i, m ->
                val active = i == selected
                Box(
                    Modifier.height(40.dp).box(if (active) Primary500 else White, if (active) null else Neutral300, 9999.dp)
                        .clickable { onSelect(i) }.padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center,
                ) { Text(m, style = jakarta(12, if (active) FontWeight.Bold else FontWeight.SemiBold, 15), color = if (active) White else Neutral800) }
            }
            Row(
                Modifier.height(40.dp).box(Secondary50, Secondary200, 9999.dp).clickable { }.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                AppIcon(R.drawable.ic_cal, 14.dp)
                Text("Bulan lain", style = jakarta(12, FontWeight.SemiBold, 15), color = Secondary500)
            }
        }
    }
}

@Composable
fun LinkRow(text: String, onClick: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth().clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically) {
        Text(text, style = jakarta(12, FontWeight.SemiBold, 16), color = Secondary500, modifier = Modifier.weight(1f))
        AppIcon(R.drawable.ic_right_16, 16.dp)
    }
}

/** Small outlined chip ("Rupiah", "Nonaktif", "Kosong"...). */
@Composable
fun OutlineChip(text: String, color: Color = Neutral400, border: Color = Secondary200, background: Color = Color.Transparent) {
    Box(Modifier.box(background, border, 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) {
        Text(text, style = jakarta(11, FontWeight.SemiBold, 14), color = color, maxLines = 1)
    }
}

@Composable
fun StatusChip(status: DayStatus) {
    when (status) {
        DayStatus.APPROVED -> Box(Modifier.box(Success500, radius = 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) {
            Text("Disetujui", style = jakarta(11, FontWeight.SemiBold, 14), color = White)
        }
        DayStatus.WAITING -> OutlineChip("Menunggu", Neutral500, Neutral400, Neutral100)
        DayStatus.REJECTED -> OutlineChip("Ditolak", Error600, Error600, White)
        DayStatus.EMPTY -> OutlineChip("Kosong", Neutral400, Neutral300)
    }
}

enum class DayStatus { APPROVED, WAITING, REJECTED, EMPTY }

@Composable
fun InfoNote(title: String, body: String) {
    Row(
        Modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(36.dp).box(Secondary100, radius = 10.dp), contentAlignment = Alignment.Center) {
            TintedIcon(R.drawable.ic_warning_circle_brown12, 20.dp, Secondary500)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = jakarta(14, FontWeight.Bold, 20), color = TextStrong)
            Text(body, style = jakarta(12, lineHeight = 17), color = TextMuted)
        }
    }
}

/** Same asset as [AppIcon], recolored (used where the design reuses an icon in another color). */
@Composable
fun TintedIcon(@DrawableRes res: Int, size: Dp, tint: Color, modifier: Modifier = Modifier) {
    Image(painterResource(res), null, modifier.size(size), colorFilter = ColorFilter.tint(tint))
}

@Composable
fun DateStepper(label: String, onPrev: () -> Unit, onNext: () -> Unit, withIcon: Boolean = false) {
    Row(
        Modifier.fillMaxWidth().box(White, Neutral300, 14.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(32.dp).clip(CircleShape).background(Secondary50).border(1.dp, Secondary200, CircleShape).clickable(onClick = onPrev), contentAlignment = Alignment.Center) {
            AppIcon(R.drawable.ic_left_16, 16.dp)
        }
        Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
            if (withIcon) AppIcon(R.drawable.ic_cal, 16.dp)
            Text(label, style = jakarta(13, FontWeight.SemiBold, 18), color = TextStrong)
        }
        Box(Modifier.size(32.dp).clip(CircleShape).background(Secondary50).border(1.dp, Secondary200, CircleShape).clickable(onClick = onNext), contentAlignment = Alignment.Center) {
            AppIcon(R.drawable.ic_right_16, 16.dp)
        }
    }
}

private val dayNames = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

/** 1 Okt 2025 is a Wednesday. Returns e.g. "Jumat, 24 Oktober 2025". */
fun oktoberLabel(day: Int): String = "${dayNames[(day + 1) % 7]}, $day Oktober 2025"

// ---------------------------------------------------------------------------------------------
// Rekap Bulanan (Per Karyawan / Per Bagian)
// ---------------------------------------------------------------------------------------------

@Composable
fun RekapBulananScreen(initialMode: Int, onBack: () -> Unit, onOpenRincian: () -> Unit, onLogout: () -> Unit) {
    var mode by remember { mutableIntStateOf(initialMode) }
    var month by remember { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Rekap Bulanan", if (mode == 0) "Rekap per karyawan" else "Rekap per bagian", onBack)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Column(
                Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Segmented(listOf("Per Karyawan", "Per Bagian"), mode) { mode = it }
                MonthPicker(month) { month = it }
                if (mode == 0) PerKaryawanContent() else PerBagianContent(onOpenRincian)
            }
            AppFooter(onLogout)
        }
    }
}

@Composable
private fun PerBagianContent(onOpenRincian: () -> Unit) {
    var day by remember { mutableIntStateOf(24) }
    Row(
        Modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).clickable { }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Bagian", style = jakarta(11, lineHeight = 14), color = Neutral500)
            Text("Frontliner", style = jakarta(16, FontWeight.Bold, 22), color = TextStrong)
            Text("8 karyawan · 1 nonaktif", style = jakarta(12, lineHeight = 16), color = TextMuted)
        }
        AppIcon(R.drawable.ic_right_20, 20.dp)
    }
    RekapCard("Ringkasan Hari", "Oktober 2025 · 8 karyawan × 27 hari kerja") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatTile("164", "Hari terisi", Modifier.weight(1f))
            StatTile("12", "Hari susulan", Modifier.weight(1f))
            StatTile("52", "Hari kosong", Modifier.weight(1f))
        }
    }
    RekapCard("Rekap per Karyawan", "Satu baris per karyawan, kolom sama") {
        val widths = listOf(128.dp, 116.dp, 104.dp, 100.dp, 84.dp)
        val header = listOf("Karyawan", "Omzet harian (Rp)", "Transaksi", "Roti terjual (pcs)", "Terisi")
        data class Row5(val name: String, val inactive: Boolean, val cells: List<Pair<String, String>>)
        val rows = listOf(
            Row5("Dewi Lestari", true, listOf("48.260.000" to "rata 2.540.000", "1.284" to "rata 67,6", "6.410" to "rata 337,4", "22" to "3 susulan")),
            Row5("Rina Puspita", false, listOf("61.480.000" to "rata 2.562.000", "1.512" to "rata 63,0", "7.340" to "rata 305,8", "24" to "1 susulan")),
            Row5("Yoga Pratama", false, listOf("52.910.000" to "rata 2.405.000", "1.390" to "rata 63,2", "6.880" to "rata 312,7", "22" to "2 susulan")),
            Row5("Nur Aisyah", false, listOf("44.120.000" to "rata 2.206.000", "1.180" to "rata 59,0", "5.920" to "rata 296,0", "20" to "-")),
            Row5("Bagas Saputra", false, listOf("39.750.000" to "rata 2.092.000", "1.045" to "rata 55,0", "5.310" to "rata 279,5", "19" to "4 susulan")),
        )
        Column(Modifier.horizontalScroll(rememberScrollState())) {
            Row(Modifier.padding(bottom = 8.dp)) {
                header.forEachIndexed { i, h ->
                    Text(h, style = jakarta(10, FontWeight.SemiBold, 14), color = Neutral500, modifier = Modifier.width(widths[i]).padding(end = 8.dp))
                }
            }
            Box(Modifier.width(widths.fold(0.dp) { a, b -> a + b }).height(1.dp).background(Secondary200))
            rows.forEachIndexed { r, row ->
                Row(Modifier.padding(vertical = 10.dp)) {
                    Column(Modifier.width(widths[0]).padding(end = 8.dp), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Text(row.name, style = jakarta(12, FontWeight.SemiBold, 16), color = TextStrong)
                        if (row.inactive) OutlineChip("Nonaktif")
                    }
                    row.cells.forEachIndexed { i, (v, sub) ->
                        Column(Modifier.width(widths[i + 1]).padding(end = 8.dp), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                            Text(v, style = jakarta(12, FontWeight.SemiBold, 16), color = TextStrong)
                            Text(sub, style = jakarta(10, lineHeight = 13), color = Neutral500)
                        }
                    }
                }
                if (r != rows.lastIndex) Box(Modifier.width(widths.fold(0.dp) { a, b -> a + b }).height(1.dp).background(Neutral300))
            }
        }
        Text("Geser tabel ke samping untuk melihat kolom lain.", style = jakarta(11, lineHeight = 15), color = Neutral500)
        LinkRow("Lihat 3 karyawan lain")
    }
    RekapCard("Rincian Harian", "Pilih tanggal untuk melihat isian tiap karyawan") {
        DateStepper(oktoberLabel(day), { if (day > 1) day-- }, { if (day < 31) day++ })
        Row(
            Modifier.fillMaxWidth().box(Primary500).clickable(onClick = onOpenRincian).padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Buka Rincian Harian", style = jakarta(14, FontWeight.SemiBold, 20), color = White)
            AppIcon(R.drawable.ic_right_white, 18.dp)
        }
    }
}

private data class Metric(val name: String, val unit: String, val total: String, val avg: String, val note: String? = null, val inactive: Boolean = false)

@Composable
private fun PerKaryawanContent() {
    // Employee card
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Secondary200, 16.dp).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Initials("DL", 40.dp)
            Column(Modifier.weight(1f)) {
                Text("Dewi Lestari", style = jakarta(16, FontWeight.Bold, 22), color = TextStrong)
                Text("Frontliner · ID 024", style = jakarta(12, lineHeight = 16), color = TextMuted)
            }
            OutlineChip("Nonaktif")
        }
        Box(Modifier.fillMaxWidth().box(Neutral100, Neutral300, 10.dp).padding(10.dp)) {
            Text("Akun dinonaktifkan 2 Nov 2025. Data periode lama tetap ditampilkan.", style = jakarta(11, lineHeight = 15), color = TextMuted)
        }
    }
    RekapCard("Ringkasan Hari", "1 sampai 31 Oktober 2025 · 27 hari kerja") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatTile("22", "Hari terisi", Modifier.weight(1f))
            StatTile("3", "Hari susulan", Modifier.weight(1f))
            StatTile("5", "Hari kosong", Modifier.weight(1f))
        }
        Text(
            "Hari susulan sudah termasuk dalam hari terisi. Dari 22 hari terisi, 19 hari ikut dihitung.",
            style = jakarta(11, lineHeight = 15),
            color = TextMuted,
        )
    }
    val metrics = listOf(
        Metric("Omzet harian", "Rupiah", "Rp 48.260.000", "Rp 2.540.000"),
        Metric("Jumlah transaksi", "Transaksi", "1.284", "67,6"),
        Metric("Roti terjual", "Pcs", "6.410", "337,4"),
        Metric("Komplain pelanggan", "Kejadian", "7", "0,4"),
        Metric("Kebersihan etalase", "Ya/Tidak", "17 Ya", "-", "17 Ya dan 2 Tidak dari 19 hari dihitung"),
        Metric("Penjualan paket promo", "Rupiah", "Rp 2.700.000", "Rp 450.000", "Dicatat sampai 10 Okt, hanya 6 hari dihitung", inactive = true),
    )
    RekapCard(
        "Hal yang Dicatat", "Total dan rata-rata per hari",
        trailing = { Box(Modifier.box(Secondary100, radius = 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) { Text("6 hal", style = jakarta(11, FontWeight.SemiBold, 14), color = Secondary500) } },
    ) {
        metrics.forEach { m ->
            Column(
                Modifier.fillMaxWidth().box(White, Neutral300, 14.dp).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(m.name, style = jakarta(13, FontWeight.SemiBold, 18), color = TextStrong)
                    if (m.inactive) OutlineChip("Nonaktif")
                    Spacer(Modifier.weight(1f))
                    OutlineChip(m.unit, Neutral500, Neutral300)
                }
                Row {
                    Column(Modifier.weight(1f)) {
                        Text("Total", style = jakarta(10, lineHeight = 14), color = Neutral500)
                        Text(m.total, style = jakarta(13, FontWeight.Bold, 18), color = TextStrong)
                    }
                    Column(Modifier.weight(1f)) {
                        Text("Rata-rata", style = jakarta(10, lineHeight = 14), color = Neutral500)
                        Text(m.avg, style = jakarta(13, FontWeight.Bold, 18), color = TextStrong)
                    }
                }
                if (m.note != null) Text(m.note, style = jakarta(10, lineHeight = 14), color = Neutral500)
            }
        }
    }
    StatusHarianCard()
    InfoNote(
        "Aturan hitung",
        "Hanya isian Disetujui yang fotonya tidak dinyatakan Tidak sah yang ikut dihitung. Isian Menunggu dan Ditolak belum masuk total. Rata-rata dihitung dari jumlah hari yang dihitung.",
    )
}

@Composable
fun Initials(text: String, size: Dp, faded: Boolean = false) {
    Box(
        Modifier.size(size).clip(CircleShape).background(if (faded) Neutral100 else Neutral900)
            .then(if (faded) Modifier.border(1.dp, Neutral300, CircleShape) else Modifier),
        contentAlignment = Alignment.Center,
    ) { Text(text, style = jakarta(12, FontWeight.Bold), color = if (faded) Neutral400 else White) }
}

/** Calendar of October 2025 + legend + per-day status list. */
@Composable
private fun StatusHarianCard() {
    // Status per date for the sample month (1 Okt 2025 starts on Wednesday).
    val approved = setOf(1, 2, 3, 4, 6, 8, 9, 10, 11, 13, 15, 16, 17, 18, 23, 24, 25, 27, 29)
    val susulan = setOf(2, 13, 23)
    val rejected = setOf(21)
    val waiting = setOf(22, 30)
    val weekend = setOf(5, 12, 19, 26)
    RekapCard("Status Harian", "Oktober 2025 · tanggal kosong terlihat langsung") {
        Row {
            listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min").forEach {
                Text(it, style = jakarta(10, lineHeight = 14), color = Neutral500, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            }
        }
        val cells = List(2) { 0 } + (1..31).toList()
        cells.chunked(7).forEach { week ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for (i in 0 until 7) {
                    val d = week.getOrNull(i) ?: 0
                    Box(Modifier.weight(1f).aspectRatio(1f)) {
                        if (d > 0) CalendarCell(d, approved, susulan, rejected, waiting, weekend)
                    }
                }
            }
        }
        Column(
            Modifier.fillMaxWidth().box(Secondary50, Secondary200, 12.dp).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                LegendSquare(Success500, null, "Disetujui")
                LegendSquare(Neutral100, Neutral300, "Menunggu")
                LegendSquare(Error600, null, "Ditolak")
                LegendSquare(White, Neutral300, "Kosong")
            }
            Text("S = susulan", style = jakarta(10, lineHeight = 14), color = TextMuted)
        }
        Text("Rincian status", style = jakarta(13, FontWeight.Bold, 18), color = TextStrong, modifier = Modifier.padding(top = 4.dp))
        data class DayRow(val day: Int, val dayName: String, val status: DayStatus, val photo: String, val susulan: Boolean = false)
        listOf(
            DayRow(24, "Jum", DayStatus.APPROVED, "Foto: Sah"),
            DayRow(23, "Kam", DayStatus.APPROVED, "Foto: Belum diperiksa", susulan = true),
            DayRow(22, "Rab", DayStatus.WAITING, "Foto: Belum diperiksa"),
            DayRow(21, "Sel", DayStatus.REJECTED, "Foto: Tidak sah"),
            DayRow(20, "Sen", DayStatus.EMPTY, "Belum ada isian"),
        ).forEach { r ->
            Row(
                Modifier.fillMaxWidth().box(White, Neutral300, 14.dp).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${r.day}", style = jakarta(14, FontWeight.Bold, 18), color = TextStrong)
                    Text(r.dayName, style = jakarta(9, lineHeight = 12), color = Neutral500)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        StatusChip(r.status)
                        if (r.susulan) Box(Modifier.box(Secondary100, radius = 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) {
                            Text("Susulan", style = jakarta(11, FontWeight.SemiBold, 14), color = Secondary500)
                        }
                    }
                    Text(
                        r.photo,
                        style = jakarta(10, lineHeight = 14),
                        color = when (r.status) {
                            DayStatus.APPROVED -> if (r.photo == "Foto: Sah") Success500 else TextMuted
                            DayStatus.REJECTED -> Error600
                            DayStatus.EMPTY -> Neutral400
                            else -> TextMuted
                        },
                    )
                }
            }
        }
        LinkRow("Lihat 31 tanggal")
    }
}

@Composable
private fun CalendarCell(d: Int, approved: Set<Int>, susulan: Set<Int>, rejected: Set<Int>, waiting: Set<Int>, weekend: Set<Int>) {
    val shape = RoundedCornerShape(8.dp)
    val (bg, fg, dashed) = when (d) {
        in approved -> Triple(Success500, White, false)
        in rejected -> Triple(Error600, White, false)
        in waiting -> Triple(Neutral100, Neutral500, false)
        in weekend -> Triple(Color.Transparent, Neutral300, false)
        else -> Triple(White, Neutral400, true)
    }
    Box(
        Modifier
            .fillMaxSize()
            .clip(shape)
            .background(bg)
            .then(
                when {
                    dashed -> Modifier.dashedBorder(Neutral300, 8.dp)
                    d in waiting -> Modifier.border(1.dp, Neutral300, shape)
                    else -> Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$d", style = jakarta(11, FontWeight.SemiBold, 13), color = fg)
            if (d in susulan) Text("s", style = jakarta(8, FontWeight.SemiBold, 9), color = fg)
        }
    }
}

@Composable
private fun LegendSquare(color: Color, border: Color?, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(Modifier.size(10.dp).box(color, border, 2.dp))
        Text(label, style = jakarta(10, lineHeight = 14), color = TextMuted)
    }
}

// ---------------------------------------------------------------------------------------------
// Rincian Harian · Per Bagian
// ---------------------------------------------------------------------------------------------

private data class DailyEntry(
    val initials: String,
    val name: String,
    val photo: String,
    val status: DayStatus,
    val susulan: Boolean = false,
    val values: List<Triple<String, String, Boolean>> = emptyList(), // label, value, tercapai
    val note: String? = null,
)

@Composable
fun RincianHarianScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    var day by remember { mutableIntStateOf(24) }
    val entries = listOf(
        DailyEntry(
            "DL", "Dewi Lestari", "Foto: Sah", DayStatus.APPROVED,
            values = listOf(
                Triple("Omzet harian", "Rp 2.410.000", true), Triple("Jumlah transaksi", "63", true),
                Triple("Roti terjual", "318", false), Triple("Kebersihan etalase", "Ya", false), Triple("Komplain pelanggan", "0", false),
            ),
        ),
        DailyEntry(
            "RP", "Rina Puspita", "Foto: Belum diperiksa", DayStatus.APPROVED, susulan = true,
            values = listOf(
                Triple("Omzet harian", "Rp 1.780.000", false), Triple("Jumlah transaksi", "44", false),
                Triple("Roti terjual", "236", false), Triple("Kebersihan etalase", "Ya", false), Triple("Komplain pelanggan", "1", false),
            ),
            note = "Diisi susulan pada 25 Okt.",
        ),
        DailyEntry(
            "YP", "Yoga Pratama", "Foto: Belum diperiksa", DayStatus.WAITING,
            values = listOf(
                Triple("Omzet harian", "Rp 2.260.000", true), Triple("Jumlah transaksi", "58", false),
                Triple("Roti terjual", "301", false), Triple("Kebersihan etalase", "Tidak", false), Triple("Komplain pelanggan", "0", false),
            ),
            note = "Masih menunggu persetujuan, belum ikut dihitung.",
        ),
        DailyEntry(
            "BS", "Bagas Saputra", "Foto: Tidak sah", DayStatus.REJECTED,
            values = listOf(
                Triple("Omzet harian", "Rp 2.900.000", false), Triple("Jumlah transaksi", "71", false),
                Triple("Roti terjual", "380", false), Triple("Kebersihan etalase", "Ya", false), Triple("Komplain pelanggan", "0", false),
            ),
            note = "Foto dinyatakan Tidak sah. Isian tidak ikut dihitung.",
        ),
        DailyEntry("NA", "Nur Aisyah", "", DayStatus.EMPTY, note = "Belum mengisi pada tanggal ini. Ditampilkan sebagai Kosong, bukan 0."),
    )

    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Rincian Harian", "Frontliner · $day Oktober 2025", onBack)
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    Modifier.fillMaxWidth().box(White, Secondary200, 16.dp).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.weight(1f)) { DateStepper(oktoberLabel(day), { if (day > 1) day-- }, { if (day < 31) day++ }, withIcon = true) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatTile("4", "Mengisi", Modifier.weight(1f), Secondary50, Secondary200)
                    StatTile("1", "Susulan", Modifier.weight(1f), Secondary50, Secondary200)
                    StatTile("4", "Kosong", Modifier.weight(1f), Secondary50, Secondary200)
                }
                RekapCard(
                    "Isian Karyawan", "8 karyawan di bagian ini",
                    trailing = { Box(Modifier.box(Secondary100, radius = 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) { Text("$day Okt", style = jakarta(11, FontWeight.SemiBold, 14), color = Secondary500) } },
                ) {
                    entries.forEach { EntryCard(it) }
                    LinkRow("Lihat 3 karyawan lain")
                }
                InfoNote(
                    "Cara membaca halaman ini",
                    "Penanda Tercapai muncul kalau angka minimum sudah diisi pada hal tersebut. Karyawan yang tidak mengisi selalu tampil sebagai Kosong, tidak pernah dihitung sebagai 0.",
                )
            }
            AppFooter(onLogout)
        }
    }
}

@Composable
private fun EntryCard(e: DailyEntry) {
    val empty = e.status == DayStatus.EMPTY
    Column(
        Modifier
            .fillMaxWidth()
            .then(if (empty) Modifier.dashedBorder(Neutral300, 14.dp) else Modifier.box(White, Neutral300, 14.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Initials(e.initials, 32.dp, faded = empty)
            Column(Modifier.weight(1f)) {
                Text(e.name, style = jakarta(13, FontWeight.SemiBold, 18), color = if (empty) Neutral400 else TextStrong)
                if (e.photo.isNotEmpty()) {
                    Text(
                        e.photo,
                        style = jakarta(10, lineHeight = 14),
                        color = when (e.photo) { "Foto: Sah" -> Success500; "Foto: Tidak sah" -> Error600; else -> Neutral500 },
                    )
                }
            }
            StatusChip(e.status)
            if (e.susulan) Box(Modifier.box(Secondary100, radius = 9999.dp).padding(horizontal = 8.dp, vertical = 3.dp)) {
                Text("Susulan", style = jakarta(11, FontWeight.SemiBold, 14), color = Secondary500)
            }
        }
        if (e.values.isNotEmpty()) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral200))
            e.values.forEach { (label, value, ok) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(label, style = jakarta(12, lineHeight = 16), color = TextMuted, modifier = Modifier.weight(1f))
                    if (ok) {
                        TintedIcon(R.drawable.ic_check_circle_brown, 13.dp, Primary500)
                        Text("Tercapai", style = jakarta(11, FontWeight.SemiBold, 14), color = Primary500, modifier = Modifier.padding(start = 4.dp, end = 8.dp))
                    }
                    Text(value, style = jakarta(12, FontWeight.Bold, 16), color = TextStrong)
                }
            }
        }
        if (e.note != null) {
            Text(e.note, style = jakarta(10, lineHeight = 14), color = if (e.status == DayStatus.REJECTED) Error600 else Neutral500)
        }
    }
}
