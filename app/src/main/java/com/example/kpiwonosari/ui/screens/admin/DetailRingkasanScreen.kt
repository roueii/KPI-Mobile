package com.example.kpiwonosari.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.Chip
import com.example.kpiwonosari.ui.components.PillTabs
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Secondary100
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.jakarta

private val approvedPeople = listOf(
    "Dewi Lestari" to "Frontliner",
    "Agus Hermawan" to "Driver",
    "Rian Hidayat" to "Sales/Marketing",
    "Ari Kurniawan" to "Store Mananger",
)

/** Card showing an employee name, division chip and "Disetujui oleh Admin" meta. */
@Composable
fun ApprovedPersonCard(name: String, division: String, approvedBy: String, onClick: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Secondary500).clickable(onClick = onClick).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(name, style = jakarta(16, FontWeight.SemiBold, 22), color = Neutral800)
                Chip(division, Secondary100, Secondary500, vertical = 2.dp)
            }
            AppIcon(R.drawable.ic_chevron_dark, 0.dp, Modifier.size(6.5.dp, 11.5.dp).graphicsLayer(rotationZ = -90f))
        }
        Column(Modifier.padding(top = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                AppIcon(R.drawable.ic_user_13, 13.dp)
                Text(
                    buildAnnotatedString {
                        append("Disetujui oleh ")
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = Neutral800)) { append(approvedBy) }
                    },
                    style = jakarta(12, lineHeight = 16),
                    color = Neutral900,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                AppIcon(R.drawable.ic_clock_13, 13.dp)
                Text("24 Okt, 09:15", style = jakarta(10, lineHeight = 16), color = Neutral900)
            }
        }
    }
}

@Composable
fun DetailRingkasanScreen(onBack: () -> Unit, onOpenRekap: () -> Unit) {
    val filters = listOf("Semua (6)", "Frontliner", "Produksi", "Driver", "Sales/Marketing", "SPV")
    var filter by remember { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding()) {
        BackHeader("Detail Ringkasan Divisi", "Laporan yang sudah disetujui Admin", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PillTabs(filters, filter, { filter = it }, boldSelected = true)
            val all = List(3) { approvedPeople }.flatten()
            all.filter { filter == 0 || it.second == filters[filter] }.forEach { (name, div) ->
                ApprovedPersonCard(name, div, "Admin", onOpenRekap)
            }
        }
    }
}
