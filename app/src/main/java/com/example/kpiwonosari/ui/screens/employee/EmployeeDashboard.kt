package com.example.kpiwonosari.ui.screens.employee

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppFooter
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.DashboardHeader
import com.example.kpiwonosari.ui.components.ProgressBar
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral200
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary50
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.SecondaryCard
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.Warning500
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

val Red500 = Color(0xFFEF4444)
private val Tan = Color(0xFFD2A579)

enum class CatStatus(val label: String, val dot: Color) {
    DONE("Tercapai", Success500),
    EMPTY("Belum diisi", Warning500),
    FAIL("Tidak tercapai", Red500),
}

data class Category(val name: String, val done: Int, val total: Int, val status: CatStatus)

data class DashboardData(val done: Int, val total: Int, val categories: List<Category>, val ctaMeta: String)

fun dashboardFor(role: Role): DashboardData = when (role) {
    Role.STORE_MANAGER -> DashboardData(
        5, 16,
        listOf(
            Category("Penjualan", 3, 3, CatStatus.DONE),
            Category("Laba & Persediaan", 3, 4, CatStatus.DONE),
            Category("Pelayanan", 0, 2, CatStatus.EMPTY),
            Category("Operasional & SDM", 3, 5, CatStatus.FAIL),
            Category("Promosi & Event", 2, 2, CatStatus.FAIL),
        ),
        "5 kategori · 5-10 menit",
    )
    Role.PRODUKSI -> DashboardData(
        5, 16,
        listOf(
            Category("Hasil Produksi", 3, 3, CatStatus.DONE),
            Category("Waktu & Alat", 3, 4, CatStatus.DONE),
            Category("Kehadiran & Disiplin", 0, 3, CatStatus.DONE),
            Category("Kepatuhan SOP", 3, 4, CatStatus.DONE),
            Category("Kebersihan Area Kerja", 6, 6, CatStatus.EMPTY),
        ),
        "3 kategori · 5–10 menit",
    )
    else -> DashboardData(
        5, 22,
        listOf(
            Category("Penjualan", 3, 3, CatStatus.DONE),
            Category("Pelayanan", 3, 4, CatStatus.DONE),
            Category("Product Knowledge", 0, 2, CatStatus.DONE),
            Category("Akurasi Kasir", 3, 5, CatStatus.EMPTY),
            Category("Display & Kebersihan", 2, 2, CatStatus.FAIL),
            Category("Kepatuhan SOP & Disiplin", 2, 2, CatStatus.FAIL),
        ),
        "6 kategori · 5–10 menit",
    )
}

/** Circular "x/y Tuntas" progress ring (112dp, 10dp stroke, starts at 12 o'clock). */
@Composable
fun ProgressRing(done: Int, total: Int, modifier: Modifier = Modifier) {
    Box(modifier.size(112.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(112.dp)) {
            val stroke = 10.08.dp.toPx()
            val d = size.minDimension * 0.8f
            val tl = Offset((size.width - d) / 2, (size.height - d) / 2)
            drawArc(Neutral300, 0f, 360f, false, tl, Size(d, d), style = Stroke(stroke))
            val sweep = 360f * done / total.coerceAtLeast(1)
            drawArc(Success500, -90f, sweep, false, tl, Size(d, d), style = Stroke(stroke, cap = StrokeCap.Round))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$done/$total", style = jakarta(24, FontWeight.Bold, 24, -0.24), color = Neutral800)
            Text("Tuntas", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral500, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun StatusPill(status: CatStatus) {
    val bg = when (status) {
        CatStatus.DONE -> Secondary100
        CatStatus.EMPTY -> Color.Transparent
        CatStatus.FAIL -> Neutral200
    }
    Row(
        Modifier.box(bg, if (status == CatStatus.DONE) Secondary200 else Tan, 9999.dp).padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(status.dot))
        Text(status.label, style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Secondary500)
    }
}

@Composable
fun HistoryStatusPill(text: String, color: Color) {
    Box(Modifier.clip(CircleShape).background(color).padding(horizontal = 10.dp, vertical = 4.dp)) {
        Text(text, style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = White)
    }
}

/** Activity row ("Riwayat 24 Oktober 2024 / 4/16 kategori terisi" + status pill). */
@Composable
fun ActivityRow(title: String, meta: String, status: String, color: Color, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(SecondaryCard)
            .border(2.dp, Secondary200, RoundedCornerShape(12.dp)).clickable(onClick = onClick).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral900)
            Text(meta, style = jakarta(12, lineHeight = 16), color = Neutral500)
        }
        HistoryStatusPill(status, color)
    }
}

/** "KPI Saya / KPI Tim" segmented tabs used by the Store Manager. */
@Composable
fun KpiTabs(selected: Int, onSelect: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().box(Neutral100, Neutral300).padding(4.dp)) {
        listOf("KPI Saya", "KPI Tim").forEachIndexed { i, label ->
            val active = i == selected
            Row(
                Modifier
                    .weight(1f)
                    .then(if (active) Modifier.box(Secondary50, Secondary200) else Modifier.clip(RoundedCornerShape(12.dp)))
                    .clickable { onSelect(i) }
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(label, style = jakarta(14, if (active) FontWeight.Bold else FontWeight.SemiBold, 20), color = if (active) Neutral800 else Neutral500)
                if (i == 1) Box(Modifier.clip(CircleShape).background(Secondary100).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("3", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Secondary500)
                }
            }
        }
    }
}

@Composable
fun EmployeeDashboardScreen(
    role: Role,
    onMenu: () -> Unit,
    onStartInput: () -> Unit,
    onOpenHistory: (Int) -> Unit,
    onOpenTeam: () -> Unit,
    onLogout: () -> Unit,
) {
    val data = dashboardFor(role)
    Column(
        Modifier.fillMaxSize().background(Neutral50).verticalScroll(rememberScrollState()).statusBarsPadding().navigationBarsPadding(),
    ) {
        DashboardHeader("Halo, User", role.label, onMenu, Modifier.padding(horizontal = 1.dp))
        Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 40.dp)) {
            if (role == Role.STORE_MANAGER) {
                KpiTabs(0) { if (it == 1) onOpenTeam() }
                Spacer(Modifier.height(16.dp))
            }
            // Ringkasan Hari Ini
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Ringkasan Hari Ini", style = jakarta(20, FontWeight.SemiBold, 28, -0.1), color = Color(0xFF1A1C1D))
                        Text("Indikator Capaian Harian", style = jakarta(12, lineHeight = 16), color = Color(0xFF47464B), modifier = Modifier.padding(top = 9.dp))
                    }
                    Box(Modifier.box(Secondary50, Secondary200, 4.dp).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text("Hari ini, 30 Sep", style = jakarta(10, FontWeight.Medium, 16, 0.12), color = Color(0xFF1A1B1F))
                    }
                }
                Row(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(SecondaryCard)
                        .border(2.dp, Secondary200, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ProgressRing(data.done, data.total)
                    Column(Modifier.weight(1f).padding(12.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        data.categories.forEach { c ->
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                Row(Modifier.fillMaxWidth().padding(end = 8.dp)) {
                                    Text(c.name, style = jakarta(11, FontWeight.SemiBold, 16), color = Neutral800, modifier = Modifier.weight(1f), maxLines = 1)
                                    Text("${c.done}/${c.total}", style = jakarta(10, FontWeight.SemiBold, 16), color = Neutral800)
                                }
                                ProgressBar(c.done.toFloat() / c.total, height = 5.dp)
                            }
                        }
                    }
                }
                Row(
                    Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(horizontal = 8.dp, vertical = 9.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    listOf(Success500 to "Hijau: Tercapai", Warning500 to "Kuning: Belum diisi", Red500 to "Merah: Tidak tercapai").forEach { (c, t) ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(c))
                            Text(t, style = jakarta(9, FontWeight.SemiBold, 14, 0.33), color = Neutral500, maxLines = 1)
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            // Tugas Harian
            Text("Tugas Harian", style = jakarta(20, FontWeight.Bold, 28, -0.1), color = Neutral800)
            Text("Wajib diisi hari ini", style = jakarta(12, lineHeight = 16), color = Neutral500)
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 88.dp)
                    .shadow(9.dp, RoundedCornerShape(12.dp), ambientColor = Primary500.copy(0.3f), spotColor = Primary500.copy(0.3f))
                    .box(Primary500)
                    .clickable(onClick = onStartInput)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f).padding(end = 12.dp)) {
                    Text("Mulai Isi Capaian Hari Ini", style = jakarta(20, FontWeight.Bold, 28, -0.1), color = White)
                    Row(Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        AppIcon(R.drawable.ic_clock_white, 16.dp)
                        Text(data.ctaMeta, style = jakarta(12, lineHeight = 16), color = Primary50)
                    }
                }
                AppIcon(R.drawable.ic_cta_arrow, 44.dp)
            }
            Spacer(Modifier.height(8.dp))
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                    Text("Preview Status", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Color.Black, modifier = Modifier.weight(1f))
                    Text("Tersimpan otomatis", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral800)
                }
                data.categories.chunked(2).forEach { pair ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pair.forEach { c ->
                            Column(
                                Modifier.weight(1f).heightIn(min = 64.dp).clip(RoundedCornerShape(12.dp)).background(SecondaryCard)
                                    .border(2.dp, Secondary200, RoundedCornerShape(12.dp)).clickable(onClick = onStartInput).padding(8.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(c.name, style = jakarta(12, FontWeight.SemiBold, 16, 0.24), color = Neutral800, maxLines = 1)
                                Box(Modifier.padding(top = 4.dp)) { StatusPill(c.status) }
                            }
                        }
                        if (pair.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            // Capaian Terbaru
            Text("Capaian Terbaru", style = jakarta(20, FontWeight.Bold, 28, -0.1), color = Neutral800)
            Spacer(Modifier.height(8.dp))
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ActivityRow("Riwayat 24 Oktober 2024", "4/16 kategori terisi", "Menunggu", Warning500) { onOpenHistory(0) }
                ActivityRow("Riwayat 23 Oktober 2024", "10/16 kategori terisi", "Disetujui", Success500) { onOpenHistory(1) }
                ActivityRow("Riwayat 22 Oktober 2024", "14/16 kategori terisi", "Disetujui", Success500) { onOpenHistory(2) }
            }
            Spacer(Modifier.height(32.dp))
            AppFooter(onLogout)
        }
    }
}
