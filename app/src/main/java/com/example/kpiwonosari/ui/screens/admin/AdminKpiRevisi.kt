package com.example.kpiwonosari.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.Chip
import com.example.kpiwonosari.ui.components.HDivider
import com.example.kpiwonosari.ui.components.OutlineButton
import com.example.kpiwonosari.ui.components.PillTabs
import com.example.kpiwonosari.ui.components.PrimaryButton
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary50
import com.example.kpiwonosari.ui.theme.Primary700
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.Warning500
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

// ---------------------------------------------------------------------------------------------
// Kelola KPI
// ---------------------------------------------------------------------------------------------

private sealed interface KpiItem {
    val name: String
}
private data class NumberItem(override val name: String, val note: String) : KpiItem
private data class CheckItem(override val name: String) : KpiItem
private data class KpiGroup(val name: String, val items: List<KpiItem>, val showCount: Boolean = false)

private val kpiDivisions = listOf("Store Manager", "Frontliner", "Produksi", "Driver", "Sales / Marketing", "SPV Area")

private fun groupsFor(division: Int): List<KpiGroup> = when (division) {
    0 -> listOf(
        KpiGroup("Operasional toko", listOf(NumberItem("Omzet harian", "Minimum: Rp 5.000.000"), NumberItem("Waste produk", "Maksimal 3%"))),
        KpiGroup("Kepemimpinan tim", listOf(CheckItem("Briefing pagi"), CheckItem("Evaluasi shift"), CheckItem("Training karyawan")), true),
        KpiGroup("Promosi & event", emptyList()),
    )
    1 -> listOf(
        KpiGroup("Penjualan", listOf(NumberItem("Jumlah transaksi", "Minimum: 50 transaksi"))),
        KpiGroup("Pelayanan", listOf(CheckItem("Menyapa pelanggan"), CheckItem("Menawarkan produk"), CheckItem("Product knowledge")), true),
        KpiGroup("Display & kebersihan", emptyList()),
    )
    else -> listOf(
        KpiGroup("Produktivitas produksi", listOf(NumberItem("Jumlah roti diproduksi", "Minimum: belum diisi"))),
        KpiGroup("Kualitas produk", listOf(NumberItem("Produk reject", "Maksimal 2%"))),
        KpiGroup(
            "Kepatuhan SOP",
            listOf(
                CheckItem("Menimbang sesuai resep"),
                CheckItem("Mengikuti waktu fermentasi"),
                CheckItem("Menggunakan APD"),
                CheckItem("Mencatat batch produksi"),
            ),
            true,
        ),
        KpiGroup("Kebersihan area produksi", emptyList()),
    )
}

@Composable
fun KelolaKpiScreen(onBack: () -> Unit) {
    var division by remember { mutableIntStateOf(2) }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Kelola KPI", "Atur hal yang dicatat tiap bagian", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PillTabs(kpiDivisions, division, { division = it })
            val groups = groupsFor(division)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                groups.forEachIndexed { i, g ->
                    // Last group starts collapsed, like "Kebersihan area produksi" in the design
                    KpiGroupCard(g, initiallyOpen = i != groups.lastIndex)
                }
            }
            Row(Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                AppIcon(R.drawable.ic_nav_arrow_right, 20.dp)
                Text("Nonaktif (0)", style = jakarta(14, FontWeight.Medium, 20), color = Neutral500, modifier = Modifier.padding(start = 8.dp))
            }
            PrimaryButton("＋ Tambah hal yang dicatat", onClick = {}, height = 48.dp, shadow = false)
            Text("Geser tab untuk melihat bagian lainnya.", style = jakarta(12, lineHeight = 18), color = Neutral500)
        }
    }
}

@Composable
private fun KpiGroupCard(group: KpiGroup, initiallyOpen: Boolean) {
    var open by remember(group.name) { mutableStateOf(initiallyOpen && group.items.isNotEmpty()) }
    Column(Modifier.fillMaxWidth().box(if (open) White else Secondary50, Neutral300, 16.dp)) {
        Row(
            Modifier.fillMaxWidth().background(Secondary50).clickable { open = !open }.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(group.name, style = jakarta(16, FontWeight.Bold, 24), color = Neutral900)
                if (group.showCount) Text("${group.items.size} hal", style = jakarta(12, lineHeight = 18), color = Neutral500)
            }
            AppIcon(if (open) R.drawable.ic_nav_arrow_up else R.drawable.ic_nav_arrow_down, 20.dp)
        }
        if (open) {
            group.items.forEach { item ->
                HDivider()
                when (item) {
                    is NumberItem -> Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(item.name, style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral900, modifier = Modifier.weight(1f))
                            MoreButton()
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(item.note, style = jakarta(12, lineHeight = 18), color = Neutral500, modifier = Modifier.weight(1f))
                            Box(Modifier.width(102.dp).box(Primary50, radius = 6.dp).padding(vertical = 4.dp), contentAlignment = Alignment.Center) {
                                Text("Angka", style = jakarta(12, FontWeight.Medium, 18), color = Primary700)
                            }
                        }
                    }
                    is CheckItem -> Row(
                        Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(item.name, style = jakarta(14, FontWeight.Medium, 20), color = Neutral900, modifier = Modifier.weight(1f))
                        Box(Modifier.width(60.dp).box(Primary50, radius = 6.dp).padding(vertical = 4.dp, horizontal = 8.dp)) {
                            Text("Ceklis", style = jakarta(12, FontWeight.Medium, 18), color = Primary700)
                        }
                        MoreButton()
                    }
                }
            }
            if (group.items.any { it is CheckItem }) {
                HDivider()
                Row(
                    Modifier.fillMaxWidth().clickable { }.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    AppIcon(R.drawable.ic_plus_orange, 18.dp)
                    Text("Tambah di kelompok ini", style = jakarta(14, FontWeight.SemiBold, 20), color = Primary700)
                }
            }
        }
    }
}

@Composable
private fun MoreButton() {
    Box(Modifier.size(32.dp).clickable { }, contentAlignment = Alignment.Center) {
        AppIcon(R.drawable.ic_more_horiz, 20.dp)
    }
}

// ---------------------------------------------------------------------------------------------
// Tinjau Revisi
// ---------------------------------------------------------------------------------------------

private data class RevisionCase(
    val name: String,
    val division: String,
    val pending: String,
    val overdue: Boolean,
    val reason: String,
    val date: String,
    val photo: String? = null,
)

private val revisionCases = listOf(
    RevisionCase(
        "Budi Santoso", "Frontliner", "Menggantung 3 hari (Overdue > 48 Jam)", true,
        "Foto bukti kurang jelas, pencahayaan tidak cukup untuk memvalidasi kondisi display.",
        "22 Okt 2024", "Foto_Display_Pagi_22Okt.jpg",
    ),
    RevisionCase(
        "Joko Prasetyo", "Produksi", "Menggantung 18 jam", false,
        "Foto bukti oven tidak menunjukkan kondisi suhu dan waktu pemanggangan dengan jelas.",
        "22 Okt 2024",
    ),
    RevisionCase(
        "Agus Hermawan", "Driver", "Menggantung 1 hari (26 Jam)", false,
        "Foto bukti pengantaran outlet Kaliurang tidak menunjukkan tanda tangan supervisor penerima.",
        "22 Okt 2024",
    ),
)

private val frontlinerChecklist = listOf(
    "Penjualan", "Pelayanan", "Product Knowledge", "Akurasi Kasir", "Display & Kebersihan", "Kepatuhan SOP & Disiplin",
)

@Composable
fun TinjauRevisiScreen(onBack: () -> Unit) {
    val filters = listOf("Semua (3)", "Frontliner", "Produksi", "Driver", "Sales/Marketing", "SPV")
    var filter by remember { mutableIntStateOf(0) }
    val handled = remember { mutableStateListOf<String>() }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Tinjau Revisi", "Laporan yang sudah disetujui Store Manager", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(Modifier.size(36.dp).box(Warning500, radius = 8.dp), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_warning_circle_white, 20.dp)
                }
                Text("${revisionCases.size} Karyawan Perlu Revisi", style = jakarta(16, FontWeight.Bold, 20), color = Neutral800)
            }
            PillTabs(filters, filter, { filter = it }, boldSelected = true)
            val visible = revisionCases.filter { filter == 0 || filters[filter].startsWith(it.division) }
            visible.forEachIndexed { i, c -> RevisionCard(c, expandable = i == 0 && filter == 0 || c.photo != null, handled = c.name in handled) { handled.add(c.name) } }
            if (visible.isEmpty()) {
                Text("Tidak ada revisi untuk bagian ini.", style = jakarta(13), color = Neutral500)
            }
        }
    }
}

@Composable
private fun RevisionCard(c: RevisionCase, expandable: Boolean, handled: Boolean, onHandled: () -> Unit) {
    var expanded by remember(c.name) { mutableStateOf(true) }
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Secondary500).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(c.name, style = jakarta(16, FontWeight.Bold), color = Neutral800)
                Chip(c.division, Secondary100, Secondary500, size = 10, weight = FontWeight.Bold, vertical = 2.dp)
            }
            Chip(
                "Diminta SM", Secondary100, Secondary500, radius = 4.dp, weight = FontWeight.Bold,
                horizontal = 10.dp, leading = { AppIcon(R.drawable.ic_warning_circle_brown12, 12.dp) },
            )
        }
        Row(
            Modifier.fillMaxWidth().box(Secondary100, Neutral300, 8.dp).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AppIcon(R.drawable.ic_clock, 14.dp)
            Text(c.pending, style = jakarta(12, if (c.overdue) FontWeight.Bold else FontWeight.SemiBold), color = Neutral800)
        }
        Column(
            Modifier.fillMaxWidth().box(Secondary100, Neutral300, 8.dp).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text("Alasan Revisi dari Store Manager:", style = jakarta(11, FontWeight.Bold), color = Secondary500)
            Text(c.reason, style = jakarta(12, lineHeight = 18), color = Neutral800)
            Text(c.date, style = jakarta(11), color = Neutral400)
        }
        if (c.photo != null && expanded) {
            Row(
                Modifier.fillMaxWidth().box(Secondary100, Neutral300, 8.dp).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(Modifier.size(56.dp).box(Secondary200, radius = 8.dp), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_media_image, 24.dp)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(c.photo, style = jakarta(12, FontWeight.Bold), color = Neutral800)
                    Text("Diunggah: 22 Okt, 19:40 WIB", style = jakarta(11), color = Neutral400)
                    Text(
                        "Ketuk untuk lihat foto",
                        style = jakarta(11, FontWeight.Bold).copy(textDecoration = TextDecoration.Underline),
                        color = Warning500,
                    )
                }
            }
            Column(
                Modifier.fillMaxWidth().box(Secondary100, radius = 8.dp).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("STATUS CHECKLIST HARIAN", style = jakarta(11, FontWeight.Bold), color = Secondary500)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    frontlinerChecklist.forEach { ChecklistStatusRow(it, ok = true) }
                    ChecklistStatusRow("Foto bukti", ok = false)
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (c.photo != null) {
                OutlineButton(
                    if (expanded) "Sembunyikan Rincian" else "Lihat Rincian",
                    onClick = { expanded = !expanded },
                    radius = 8.dp,
                    textSize = 13,
                    leading = {
                        AppIcon(
                            R.drawable.ic_chevron_up,
                            14.dp,
                            if (expanded) Modifier else Modifier.graphicsRotate180(),
                        )
                    },
                )
            }
            when {
                handled -> OutlineButton(
                    "Sudah ditindaklanjuti",
                    onClick = {},
                    radius = 8.dp,
                    textSize = 13,
                    textColor = Success500,
                )
                c.photo != null -> OutlineButton(
                    "Tandai sudah ditegur/ditindaklanjuti manual",
                    onClick = onHandled,
                    background = Success500,
                    textColor = White,
                    radius = 8.dp,
                    textSize = 13,
                    leading = { AppIcon(R.drawable.ic_check_circle_white, 14.dp) },
                )
                else -> OutlineButton(
                    "Tandai sudah ditindaklanjuti manual",
                    onClick = onHandled,
                    textColor = Secondary500,
                    radius = 8.dp,
                    textSize = 13,
                    leading = { AppIcon(R.drawable.ic_check_circle_brown, 14.dp) },
                )
            }
        }
    }
}

private fun Modifier.graphicsRotate180(): Modifier = this.then(
    Modifier.graphicsLayer(rotationZ = 180f),
)

@Composable
private fun ChecklistStatusRow(label: String, ok: Boolean) {
    Row(
        Modifier.fillMaxWidth().height(35.dp).box(if (ok) White else Secondary50, radius = 6.dp).padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(if (ok) R.drawable.ic_check_circle_green else R.drawable.ic_warning_circle_amber, 14.dp)
        Text(
            label,
            style = jakarta(12, if (ok) FontWeight.Normal else FontWeight.Bold),
            color = Neutral800,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
        )
        Text(if (ok) "Sesuai" else "Perlu Revisi", style = jakarta(11, FontWeight.Bold), color = if (ok) Secondary500 else Warning500)
    }
}
