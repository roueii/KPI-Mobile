package com.example.kpiwonosari.ui.screens.employee

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppFooter
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.Chip
import com.example.kpiwonosari.ui.components.DashboardHeader
import com.example.kpiwonosari.ui.components.PillTabs
import com.example.kpiwonosari.ui.components.PrimaryButton
import com.example.kpiwonosari.ui.components.ProgressBar
import com.example.kpiwonosari.ui.components.RowChevron
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.screens.admin.KpiCheckbox
import com.example.kpiwonosari.ui.screens.admin.TintedIcon
import com.example.kpiwonosari.ui.screens.admin.frontlinerKpiGroups
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral200
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary200
import com.example.kpiwonosari.ui.theme.Primary300
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

// ---------------------------------------------------------------------------------------------
// Dashboard KPI Tim
// ---------------------------------------------------------------------------------------------

private data class TeamMember(val name: String, val role: String, val status: String, val color: Color)

private val teamMembers = listOf(
    TeamMember("Budi Santoso", "Frontliner (Kasir & Display)", "Approval", Primary500),
    TeamMember("Dewi Novita", "Frontliner (Pelayanan & Kasir)", "Approval", Primary500),
    TeamMember("Agus Pratama", "Produksi (Baker Shift Pagi)", "Approval", Primary500),
    TeamMember("Rini Triana", "Produksi (Pastry & Oven)", "Disetujui", Success500),
    TeamMember("Hendra Setiawan", "Logistik & Gudang", "Disetujui", Success500),
    TeamMember("Wulan Mayasari", "Frontliner (Shift Siang)", "Belum Lapor", Neutral400),
)

@Composable
fun DashboardTimScreen(onMenu: () -> Unit, onMyKpi: () -> Unit, onApproval: () -> Unit, onLogout: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Neutral50).verticalScroll(rememberScrollState()).statusBarsPadding().navigationBarsPadding()) {
        DashboardHeader("Halo, User", Role.STORE_MANAGER.label, onMenu)
        Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            KpiTabs(1) { if (it == 0) onMyKpi() }
            // Waiting banner
            Row(
                Modifier.fillMaxWidth().box(Secondary50, Primary200).clickable(onClick = onApproval).padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(Modifier.size(40.dp).clip(CircleShape).background(Primary300), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.m_group, 22.dp)
                }
                Column(Modifier.weight(1f)) {
                    Text("3 Karyawan Menunggu Persetujuan", style = jakarta(13, FontWeight.SemiBold, 18), color = Neutral900)
                    Text("Ketuk untuk review bukti capaian hari ini", style = jakarta(11, lineHeight = 15), color = Neutral500)
                }
                Box(Modifier.size(32.dp).clip(CircleShape).background(Primary500), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_arrow_right_white, 16.dp)
                }
            }
            // Ringkasan Tim
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Secondary200).padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Ringkasan Tim Hari Ini", style = jakarta(15, FontWeight.Bold, 20), color = Neutral900, modifier = Modifier.weight(1f))
                    Text("Shift Pagi & Siang", style = jakarta(11, FontWeight.SemiBold, 14), color = Neutral800)
                }
                Row {
                    Text("5 dari 8 karyawan sudah lapor", style = jakarta(12, FontWeight.Medium, 16), color = Neutral800, modifier = Modifier.weight(1f))
                    Text("62.5%", style = jakarta(12, FontWeight.SemiBold, 16), color = Neutral800)
                }
                ProgressBar(0.625f, track = Neutral200, height = 8.dp)
                Row(Modifier.padding(top = 4.dp)) {
                    listOf(Triple("3", "Menunggu", Primary500), Triple("2", "Disetujui", Success500), Triple("3", "Belum Lapor", Neutral400)).forEach { (n, l, c) ->
                        Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(Modifier.padding(top = 5.dp).size(8.dp).clip(CircleShape).background(c))
                            Column {
                                Text(n, style = jakarta(13, FontWeight.Bold, 18), color = Neutral900)
                                Text(l, style = jakarta(10, lineHeight = 14), color = Neutral500)
                            }
                        }
                    }
                }
            }
            // Daftar karyawan
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Daftar Karyawan", style = jakarta(16, FontWeight.Bold, 22), color = Neutral900)
                Chip("8 Anggota", Secondary100, Secondary500, border = Secondary200)
                Spacer(Modifier.weight(1f))
                Text("Urutkan", style = jakarta(11, FontWeight.SemiBold, 14), color = Neutral800)
                TintedIcon(R.drawable.m_okrs, 14.dp, Neutral800)
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                teamMembers.forEach { m ->
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(SecondaryCard)
                            .border(2.dp, Secondary200, RoundedCornerShape(12.dp))
                            .clickable { if (m.status == "Approval") onApproval() }.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(Modifier.size(36.dp).clip(CircleShape).background(Secondary200), contentAlignment = Alignment.Center) {
                            AppIcon(R.drawable.ic_user_brown, 18.dp)
                        }
                        Column(Modifier.weight(1f)) {
                            Text(m.name, style = jakarta(13, FontWeight.Bold, 18), color = Neutral900)
                            Text(m.role, style = jakarta(11, lineHeight = 15), color = Neutral500)
                        }
                        HistoryStatusPill(m.status, m.color)
                        RowChevron()
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            AppFooter(onLogout)
        }
    }
}

// ---------------------------------------------------------------------------------------------
// Approval Tim
// ---------------------------------------------------------------------------------------------

private data class PendingReport(val name: String, val division: String)

private val pendingReports = listOf(
    PendingReport("Budi Santoso", "Frontliner"),
    PendingReport("Dewi Lestari", "Frontliner"),
    PendingReport("Agus Hermawan", "Driver"),
    PendingReport("Rian Hidayat", "Sales/Marketing"),
)

@Composable
fun ApprovalTimScreen(onBack: () -> Unit) {
    val filters = listOf("Semua (5)", "Frontliner", "Produksi", "Driver", "Sales/Marketing")
    var filter by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf<String?>("Budi Santoso") }
    val decisions = remember { mutableStateMapOf<String, Boolean>() }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Approval Tim", "Capaian harian Tim", onBack)
        Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PillTabs(filters, filter, { filter = it }, boldSelected = true)
            pendingReports.filter { filter == 0 || it.division == filters[filter] }.forEach { r ->
                ApprovalCard(
                    r,
                    expanded == r.name,
                    decisions[r.name],
                    onToggle = { expanded = if (expanded == r.name) null else r.name },
                    onDecide = { decisions[r.name] = it },
                )
            }
            Column(
                Modifier.fillMaxWidth().box(Neutral100, Neutral300).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text("Persetujuan Capaian Sekaligus", style = jakarta(13, FontWeight.SemiBold, 18), color = Neutral900)
                Text(
                    "Seluruh anggota tim di atas telah memenuhi verifikasi SOP minimum hari ini.",
                    style = jakarta(11, lineHeight = 16),
                    color = Neutral500,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                Row(
                    Modifier.padding(top = 8.dp).fillMaxWidth().height(44.dp).box(Success500)
                        .clickable { pendingReports.forEach { decisions[it.name] = true } },
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIcon(R.drawable.ic_check_white_small, 0.dp, Modifier.size(10.9.dp, 8.dp))
                    Text("Setujui Semua Capaian Sekaligus", style = jakarta(13, FontWeight.SemiBold, 18), color = White)
                }
            }
        }
    }
}

@Composable
private fun ApprovalCard(r: PendingReport, expanded: Boolean, decision: Boolean?, onToggle: () -> Unit, onDecide: (Boolean) -> Unit) {
    val checks = remember(r.name) { mutableStateListOf<Boolean>().apply { frontlinerKpiGroups.forEach { g -> g.items.forEachIndexed { i, _ -> add(i == 0) } } } }
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Secondary500).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(Modifier.fillMaxWidth().clickable(onClick = onToggle), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(r.name, style = jakarta(16, FontWeight.SemiBold, 22), color = Neutral800)
                Chip(r.division, Secondary100, Secondary500, vertical = 2.dp)
            }
            AppIcon(R.drawable.ic_chevron_dark, 0.dp, Modifier.size(6.5.dp, 11.5.dp).graphicsLayer(rotationZ = if (expanded) 90f else -90f))
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            AppIcon(R.drawable.ic_clock_13, 13.dp)
            Text("Pelayanan · 30 Sep 2026, 17:30 WIB", style = jakarta(11, lineHeight = 16), color = Neutral900)
        }
        Chip("Menunggu Persetujuan Anda", Secondary100, Secondary500, border = Secondary200, size = 10)
        if (expanded) {
            repeat(3) {
                Row(
                    Modifier.fillMaxWidth().box(Secondary100, Neutral300, 8.dp).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(Modifier.size(56.dp).box(Secondary200, radius = 8.dp), contentAlignment = Alignment.Center) {
                        AppIcon(R.drawable.ic_media_image, 24.dp)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Foto_Display_Pagi_22Okt.jpg", style = jakarta(12, FontWeight.Bold), color = Neutral800)
                        Text("Diunggah: 22 Okt, 19:40 WIB", style = jakarta(11), color = Neutral400)
                        Text("Ketuk untuk lihat foto", style = jakarta(11, FontWeight.Bold).copy(textDecoration = TextDecoration.Underline), color = Warning500)
                    }
                }
            }
            Column(
                Modifier.fillMaxWidth().box(Secondary100, Neutral300).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                var offset = 0
                frontlinerKpiGroups.forEach { g ->
                    val start = offset
                    offset += g.items.size
                    Row {
                        Text(g.title.uppercase(), style = jakarta(11, FontWeight.SemiBold, 16, 0.3), color = Neutral800, modifier = Modifier.weight(1f))
                        Text("${(start until offset).count { checks[it] }}/${g.items.size} Terpenuhi", style = jakarta(10, FontWeight.SemiBold, 14), color = Secondary500)
                    }
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
                Row(Modifier.fillMaxWidth().box(Neutral50).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AppIcon(R.drawable.ic_page_edit_dark, 14.dp)
                    Column {
                        Text("CATATAN DARI STORE MANAGER", style = jakarta(10, FontWeight.SemiBold, 14, 0.33), color = Neutral800)
                        Text("Isi catatan untuk dikirim ke Admin", style = jakarta(11, lineHeight = 16), color = Neutral800)
                    }
                }
            }
        }
        Row(Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            when (decision) {
                true -> DecisionNote("Capaian disetujui & diteruskan ke Admin", Success500)
                false -> DecisionNote("Dikembalikan untuk revisi", Red500)
                null -> {
                    Row(
                        Modifier.weight(1f).height(40.dp).clip(RoundedCornerShape(10.dp)).background(White)
                            .border(1.5.dp, Red500, RoundedCornerShape(10.dp)).clickable { onDecide(false) },
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(R.drawable.ic_cancel_red, 18.dp)
                        Text("Tolak (Revisi)", style = jakarta(13, FontWeight.SemiBold, 18), color = Red500)
                    }
                    Row(
                        Modifier.weight(1f).height(40.dp).box(Success500, radius = 10.dp).clickable { onDecide(true) },
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(R.drawable.ic_check_white_small, 0.dp, Modifier.size(10.9.dp, 8.dp))
                        Text("Setujui Capaian", style = jakarta(13, FontWeight.SemiBold, 18), color = White)
                    }
                }
            }
        }
    }
}

@Composable
private fun DecisionNote(text: String, color: Color) {
    Box(Modifier.fillMaxWidth().height(40.dp).box(color.copy(alpha = 0.1f), color, 10.dp), contentAlignment = Alignment.Center) {
        Text(text, style = jakarta(13, FontWeight.SemiBold), color = color)
    }
}

// ---------------------------------------------------------------------------------------------
// Training Karyawan (Detail Training + Catat Training Baru)
// ---------------------------------------------------------------------------------------------

private data class Training(val date: String, val topic: String, val people: Int)

@Composable
fun DetailTrainingScreen(onBack: () -> Unit) {
    val trainings = remember {
        mutableStateListOf(
            Training("20 Okt 2026", "Service Excellence", 8),
            Training("15 Sep 2026", "Food Safety & Hygiene", 15),
            Training("10 Agu 2026", "Basic Product Knowledge", 12),
        )
    }
    var adding by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        Column(Modifier.background(White)) {
            Row(Modifier.fillMaxWidth().height(68.dp).padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(44.dp).box(Secondary50, Secondary200).clickable(onClick = onBack), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_arrow_left, 24.dp)
                }
                Spacer(Modifier.weight(1f))
                Box(Modifier.box(Secondary50, Secondary200, 4.dp).padding(horizontal = 9.dp, vertical = 3.dp)) {
                    Text("STORE MANAGER", style = jakarta(11, FontWeight.Medium, 16), color = Secondary500)
                }
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
        }
        Column(Modifier.verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Training Karyawan", style = jakarta(22, FontWeight.Bold, 30, -0.5), color = Neutral900)
                Text("Catat training yang sudah dilaksanakan", style = jakarta(13, lineHeight = 18), color = Neutral500)
            }
            Column(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Secondary50).border(2.dp, Primary500, RoundedCornerShape(12.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Bulan Ini", style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral900, modifier = Modifier.weight(1f))
                    Box(Modifier.box(SecondaryCard, Color(0xFF9A3412), 4.dp).padding(horizontal = 8.dp, vertical = 2.dp)) {
                        Text("BULANAN", style = jakarta(10, FontWeight.Bold, 15), color = Color(0xFF9A3412))
                    }
                }
                Text("${trainings.count { it.date.contains("Okt") || it.date.contains("Nov") }.coerceAtMost(1)}/1", style = jakarta(32, FontWeight.Bold, 40), color = Neutral900)
                Text("Target: minimal 1× per bulan", style = jakarta(12, lineHeight = 16), color = Neutral500)
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
            PrimaryButton("Catat Training Baru", onClick = { adding = true }, height = 48.dp, textSize = 14)
            Column(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Secondary50).border(2.dp, Primary500, RoundedCornerShape(12.dp)).padding(16.dp),
            ) {
                Text("Riwayat Training", style = jakarta(14, FontWeight.Bold, 20), color = Neutral900, modifier = Modifier.padding(bottom = 6.dp))
                trainings.forEachIndexed { i, t ->
                    if (i > 0) Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
                    Row(Modifier.fillMaxWidth().padding(vertical = 9.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(t.date, style = jakarta(13, lineHeight = 18), color = Neutral900, modifier = Modifier.width(122.dp))
                        Text(t.topic, style = jakarta(13, lineHeight = 18), color = Neutral900, modifier = Modifier.weight(1f))
                        Text("${t.people} orang", style = jakarta(13, lineHeight = 18), color = Neutral900)
                    }
                }
            }
        }
    }
    if (adding) {
        CatatTrainingDialog(onDismiss = { adding = false }) { trainings.add(0, it); adding = false }
    }
}

@Composable
private fun CatatTrainingDialog(onDismiss: () -> Unit, onSave: (Training) -> Unit) {
    var date by remember { mutableStateOf("15 November 2026") }
    var topic by remember { mutableStateOf("") }
    var people by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Neutral100).padding(16.dp).imePadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Catat Training Baru", style = jakarta(15, FontWeight.Bold, 22), color = Neutral900, modifier = Modifier.padding(bottom = 4.dp))
            TrainingField("Tanggal Pelaksanaan", date, { date = it }, trailingCalendar = true)
            TrainingField("Topik Training", topic, { topic = it }, placeholder = "Product Knowledge Lanjutan")
            TrainingField("Jumlah Peserta", people, { people = it.filter(Char::isDigit) }, placeholder = "12", number = true)
            Spacer(Modifier.height(4.dp))
            PrimaryButton(
                "Simpan Training",
                onClick = {
                    if (topic.isNotBlank()) {
                        val short = date.replace("November", "Nov").replace("Oktober", "Okt")
                        onSave(Training(short, topic, people.toIntOrNull() ?: 0))
                    }
                },
                height = 48.dp,
                textSize = 14,
                shadow = false,
            )
        }
    }
}

@Composable
private fun TrainingField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "",
    number: Boolean = false,
    trailingCalendar: Boolean = false,
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label, style = jakarta(12, FontWeight.SemiBold, 16), color = Neutral500)
        Row(
            Modifier.fillMaxWidth().height(44.dp).box(White, Neutral200, 10.dp).padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.weight(1f)) {
                if (value.isEmpty()) Text(placeholder, style = jakarta(14), color = Neutral400)
                BasicTextField(
                    value, onChange, singleLine = true,
                    textStyle = jakarta(14).copy(color = Neutral900),
                    cursorBrush = SolidColor(Primary500),
                    keyboardOptions = KeyboardOptions(keyboardType = if (number) KeyboardType.Number else KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            if (trailingCalendar) TintedIcon(R.drawable.ic_cal, 20.dp, Neutral800)
        }
    }
}

