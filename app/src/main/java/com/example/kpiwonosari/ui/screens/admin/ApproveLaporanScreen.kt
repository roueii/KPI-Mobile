package com.example.kpiwonosari.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.Chip
import com.example.kpiwonosari.ui.components.PillTabs
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Primary700
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.Warning500
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

private val Red500 = Color(0xFFEF4444)

/** Orange checkbox from the design system (24dp, radius 8). */
@Composable
fun KpiCheckbox(checked: Boolean, modifier: Modifier = Modifier, onChange: ((Boolean) -> Unit)? = null) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier
            .size(24.dp)
            .clip(shape)
            .then(if (checked) Modifier.background(Primary700) else Modifier.border(1.5.dp, Primary700, shape))
            .then(if (onChange != null) Modifier.clickable { onChange(!checked) } else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) AppIcon(R.drawable.ic_check_box, 20.dp)
    }
}

data class ChecklistGroup(val title: String, val items: List<String>)

val frontlinerKpiGroups = listOf(
    ChecklistGroup("Penjualan", listOf("Mencapai target omzet harian/bulanan", "Meningkatkan nilai rata-rata belanja pelanggan", "Aktif menawarkan produk promo")),
    ChecklistGroup("Pelayanan", listOf("Menyapa pelanggan", "Ramah dan komunikatif", "Cepat melayani", "Menangani komplain dengan baik")),
    ChecklistGroup("Product Knowledge", listOf("Menguasai fungsi bahan kue", "Mengetahui stok dan lokasi produk", "Mampu memberi rekomendasi kepada pelanggan")),
    ChecklistGroup("Akurasi Kasir", listOf("Tidak ada selisih kas", "Tidak salah input  harga", "Nota sesuai transaksi")),
    ChecklistGroup("Display & Kebersihan", listOf("Rak selalu rapi", "Produk FIFO", "Label harga lengkap", "Area kerja bersih")),
    ChecklistGroup(
        "Kepatuhan SOP & Disiplin",
        listOf("Menggunakan seragam lengkap", "Mengikuti SOP buka/tutup toko", "Menjalankan prosedur pelayanan dan keamanan", "Tidak terlambat", "Mematuhi Jadwal kerja"),
    ),
)

private data class Report(val name: String, val division: String, val approvedBySm: Boolean = true)

private val reports = listOf(
    Report("Budi Santoso", "Frontliner"),
    Report("Dewi Lestari", "Frontliner"),
    Report("Agus Hermawan", "Driver"),
    Report("Rian Hidayat", "Sales/Marketing"),
    Report("Ari Kurniawan", "Store Mananger", approvedBySm = false),
)

@Composable
fun ApproveLaporanScreen(onBack: () -> Unit) {
    val filters = listOf("Semua (6)", "Frontliner", "Produksi", "Driver", "Sales/Marketing", "SPV")
    var filter by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf<String?>("Budi Santoso") }
    val decided = remember { mutableStateMapOf<String, Boolean>() }

    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding().imePadding()) {
        BackHeader("Approve Laporan", "Laporan yang sudah disetujui Store Manager", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PillTabs(filters, filter, { filter = it }, boldSelected = true)
            Row(
                Modifier.padding(top = 8.dp).fillMaxWidth().box(Secondary50, Secondary500).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(Modifier.size(40.dp).box(Secondary100), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_user_badge_check_18, 18.dp)
                }
                Column {
                    Text("${reports.size - decided.size + 1} Laporan", style = jakarta(16, FontWeight.SemiBold, 22), color = Neutral800)
                    Text("Menunggu verifikasi & approval final Admin", style = jakarta(12, lineHeight = 16), color = Neutral900)
                }
            }
            reports
                .filter { filter == 0 || filters[filter] == it.division }
                .forEach { r ->
                    ReportCard(
                        r,
                        expanded = expanded == r.name,
                        decision = decided[r.name],
                        onToggle = { expanded = if (expanded == r.name) null else r.name },
                        onDecide = { decided[r.name] = it },
                    )
                }
            Row(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                AppIcon(R.drawable.ic_info_empty, 18.dp, Modifier.padding(top = 4.dp))
                Text(
                    buildAnnotatedString {
                        append("Laporan yang disetujui final akan langsung disinkronisasi ke ")
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = Neutral800)) { append("Rapor Bulanan") }
                        append(" dan menjadi basis kalkulasi bonus KPI karyawan secara otomatis.")
                    },
                    style = jakarta(12, lineHeight = 19),
                    color = Neutral900,
                )
            }
        }
    }
}

@Composable
private fun ReportCard(
    r: Report,
    expanded: Boolean,
    decision: Boolean?,
    onToggle: () -> Unit,
    onDecide: (Boolean) -> Unit,
) {
    val checks = remember(r.name) { mutableStateListOf<Boolean>().apply { frontlinerKpiGroups.forEach { g -> g.items.forEachIndexed { i, _ -> add(i == 0) } } } }
    var note by remember(r.name) { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().box(Secondary50, Secondary500).padding(16.dp)) {
        Row(Modifier.fillMaxWidth().clickable(onClick = onToggle), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(r.name, style = jakarta(16, FontWeight.SemiBold, 22), color = Neutral800)
                Chip(r.division, Secondary100, Secondary500, vertical = 2.dp)
            }
            AppIcon(
                R.drawable.ic_chevron_dark,
                0.dp,
                Modifier.size(6.5.dp, 11.5.dp).graphicsLayer(rotationZ = if (expanded) 90f else -90f),
            )
        }
        Column(Modifier.padding(top = 8.dp)) {
            if (r.approvedBySm) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AppIcon(R.drawable.ic_user_13, 13.dp)
                    Text(
                        buildAnnotatedString {
                            append("Disetujui oleh ")
                            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = Neutral800)) { append("Siti (Store Manager)") }
                        },
                        style = jakarta(12, lineHeight = 16),
                        color = Neutral900,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AppIcon(R.drawable.ic_clock_13, 13.dp)
                    Text("24 Okt, 09:15", style = jakarta(10, lineHeight = 16), color = Neutral900)
                }
            } else {
                Text("Perlu persetujuan oleh Admin", style = jakarta(12, lineHeight = 16), color = Neutral900)
            }
        }
        if (expanded) {
            Row(
                Modifier.padding(top = 8.dp).fillMaxWidth().box(Secondary100, Neutral300, 8.dp).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(Modifier.size(56.dp).box(Secondary200, radius = 8.dp), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_media_image, 24.dp)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Foto_Display_Pagi_22Okt.jpg", style = jakarta(12, FontWeight.Bold), color = Neutral800)
                    Text("Diunggah: 22 Okt, 19:40 WIB", style = jakarta(11), color = Neutral400)
                    Text(
                        "Ketuk untuk lihat foto",
                        style = jakarta(11, FontWeight.Bold).copy(textDecoration = TextDecoration.Underline),
                        color = Warning500,
                    )
                }
            }
            Column(
                Modifier.padding(top = 8.dp).fillMaxWidth().box(Secondary100, Neutral300).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                var offset = 0
                frontlinerKpiGroups.forEach { g ->
                    val start = offset
                    offset += g.items.size
                    val done = (start until offset).count { checks[it] }
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(g.title.uppercase(), style = jakarta(12, FontWeight.SemiBold, 16, 0.3), color = Neutral800, modifier = Modifier.weight(1f))
                        Text("$done/${g.items.size} Terpenuhi", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Secondary500)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        g.items.forEachIndexed { i, item ->
                            val idx = start + i
                            Row(
                                Modifier.fillMaxWidth().box(Neutral50).clickable { checks[idx] = !checks[idx] }.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                KpiCheckbox(checks[idx])
                                Text(item, style = jakarta(12, FontWeight.SemiBold, 16, 0.24), color = Neutral800)
                            }
                        }
                    }
                }
                Row(
                    Modifier.fillMaxWidth().box(Neutral50).padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AppIcon(R.drawable.ic_page_edit_14, 14.dp)
                    Column {
                        Text("CATATAN STORE MANAGER", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral800)
                        Text(
                            "\"Kerja Budi sangat tertib hari ini, display rapi dan closing kasir tepat waktu tanpa selisih.\"",
                            style = jakarta(12, lineHeight = 16),
                            color = Neutral800,
                            modifier = Modifier.padding(top = 1.dp),
                        )
                    }
                }
                Column(Modifier.padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Catatan Admin / Owner (Opsional)", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral800)
                    Box(Modifier.fillMaxWidth().height(44.dp).box(Neutral50).padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart) {
                        if (note.isEmpty()) Text("Tulis catatan revisi atau apresiasi...", style = jakarta(12), color = Neutral400)
                        BasicTextField(
                            note, { note = it }, singleLine = true,
                            textStyle = jakarta(12).copy(color = Neutral800),
                            cursorBrush = SolidColor(Primary500),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
        Row(
            Modifier.padding(top = if (expanded) 24.dp else 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            when (decision) {
                true -> DecisionBanner("Laporan disetujui final", Success500)
                false -> DecisionBanner("Laporan dikembalikan untuk revisi", Red500)
                null -> {
                    Row(
                        Modifier.weight(1f).height(44.dp).clip(RoundedCornerShape(12.dp)).background(White)
                            .border(2.dp, Red500, RoundedCornerShape(12.dp)).clickable { onDecide(false) },
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(R.drawable.ic_cancel_red, 20.dp)
                        Text("Tolak (Revisi)", style = jakarta(14, FontWeight.SemiBold, 20, 0.14), color = Red500)
                    }
                    Row(
                        Modifier.weight(1f).height(44.dp).box(Success500).clickable { onDecide(true) },
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(R.drawable.ic_check_white_small, 0.dp, Modifier.size(10.9.dp, 8.dp))
                        Text(
                            "Approve Final",
                            style = if (expanded) jakarta(14, FontWeight.SemiBold, 20, 0.14) else jakarta(12, FontWeight.SemiBold, 16, 0.24),
                            color = White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DecisionBanner(text: String, color: Color) {
    Box(
        Modifier.fillMaxWidth().height(44.dp).box(color.copy(alpha = 0.1f), color),
        contentAlignment = Alignment.Center,
    ) { Text(text, style = jakarta(13, FontWeight.SemiBold), color = color) }
}
