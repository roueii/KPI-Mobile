package com.example.kpiwonosari.ui.screens.input

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.screens.admin.KpiCheckbox
import com.example.kpiwonosari.ui.screens.admin.dashedBorder
import com.example.kpiwonosari.ui.theme.Error600
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.SecondaryCard
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

private val Zinc900 = Color(0xFF18181B)
private val Zinc500 = Color(0xFF71717A)
private val Zinc800 = Color(0xFF27272A)
private val Zinc200 = Color(0xFFE4E4E7)
private val Zinc400 = Color(0xFFA1A1AA)
private val Gray900 = Color(0xFF111827)
private val Gray600 = Color(0xFF4B5563)
private val Gray500 = Color(0xFF6B7280)
private val Gray400 = Color(0xFF9CA3AF)
private val Gray50 = Color(0xFFF9FAFB)
private val Slate900 = Color(0xFF0F172A)
private val Slate600 = Color(0xFF475569)
private val Rust = Color(0xFF9A3412)
private val Stone900 = Color(0xFF1C1917)

/** Shared "Close icon + step progress" header of every Isi Capaian frame. */
@Composable
private fun WizardHeader(role: Role, step: Int, total: Int, stepName: String, onClose: () -> Unit) {
    Column(Modifier.fillMaxWidth().background(White)) {
        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 21.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(44.dp).box(Neutral50, Neutral300).clickable(onClick = onClose),
                    contentAlignment = Alignment.Center,
                ) { AppIcon(R.drawable.ic_cancel, 24.dp) }
                Box(Modifier.weight(1f))
                Box(Modifier.box(Secondary50, Secondary200, 4.dp).padding(horizontal = 9.dp, vertical = 3.dp)) {
                    Text(role.label.uppercase(), style = jakarta(11, FontWeight.Medium, 16), color = Secondary500)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(total) { i ->
                        val seg = Modifier.weight(1f).height(8.dp)
                        when {
                            i == step -> Box(
                                seg.clip(CircleShape).border(1.5.dp, Primary500, CircleShape).padding(1.5.dp)
                                    .clip(CircleShape).background(White).padding(1.dp).clip(CircleShape).background(Primary500),
                            )
                            i < step -> Box(seg.clip(CircleShape).background(Primary500))
                            else -> Box(seg.clip(CircleShape).background(White).border(1.dp, Neutral300, CircleShape))
                        }
                    }
                }
                Row(Modifier.fillMaxWidth()) {
                    Text("Langkah ${step + 1} dari $total", style = jakarta(12, FontWeight.Bold, 16), color = Zinc900, modifier = Modifier.weight(1f))
                    Text(stepName, style = jakarta(12, FontWeight.Medium, 16), color = Neutral500)
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
    }
}

@Composable
private fun IndicatorCard(
    step: WizardStep,
    stepIndex: Int,
    checked: Map<String, Boolean>,
    onToggle: (String) -> Unit,
    onOpenTraining: () -> Unit,
) {
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Primary500, 12.dp).border(1.dp, Primary500, RoundedCornerShape(12.dp)).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        if (step.mandatoryStyle) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("INDIKATOR PENILAIAN", style = jakarta(12, FontWeight.Bold, 16, 0.3), color = Gray900, modifier = Modifier.weight(1f))
                    WajibBadge("WAJIB")
                }
                Text(step.mandatoryNote, style = jakarta(12, lineHeight = 18), color = Gray600)
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Indikator Penilaian", style = jakarta(16, FontWeight.Bold, 20), color = Gray900)
                Text("Centang indikator yang dipenuhi hari ini", style = jakarta(12, lineHeight = 16), color = Gray600)
            }
        }
        step.groups.forEachIndexed { gi, group ->
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (group.label != null) {
                    Text(
                        group.label.uppercase(),
                        style = jakarta(10, FontWeight.Bold, 14, 0.3),
                        color = Gray900,
                    )
                }
                group.items.forEachIndexed { ii, item ->
                    val key = "$stepIndex-$gi-$ii"
                    val on = checked[key] ?: false
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = if (step.mandatoryStyle) 0.dp else 96.dp)
                            .box(White, Rust, 12.dp)
                            .border(0.5.dp, Rust, RoundedCornerShape(12.dp))
                            .clickable { onToggle(key) }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        KpiCheckbox(on, Modifier.padding(top = 2.dp))
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(item.title, style = jakarta(14, FontWeight.Bold, 16), color = Slate900)
                            Text(item.description, style = jakarta(12, lineHeight = 16), color = Slate600)
                            if (item.opensTraining) {
                                Text(
                                    "Lihat & catat training →",
                                    style = jakarta(12, FontWeight.SemiBold, 16).copy(textDecoration = TextDecoration.Underline),
                                    color = Primary500,
                                    modifier = Modifier.padding(top = 4.dp).clickable(onClick = onOpenTraining),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WajibBadge(text: String) {
    Box(Modifier.box(SecondaryCard, Rust, 4.dp).padding(horizontal = 9.dp, vertical = 3.dp)) {
        Text(text, style = jakarta(10, FontWeight.Bold, 15, -0.25), color = Rust)
    }
}

@Composable
private fun PhotoSection(role: Role, photos: List<String>, onAdd: () -> Unit, onRemove: (Int) -> Unit) {
    Column(
        Modifier.fillMaxWidth().box(Secondary50, Primary500, 12.dp).border(1.dp, Primary500, RoundedCornerShape(12.dp)).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Unggah Bukti Foto", style = jakarta(16, FontWeight.Bold, 20), color = Gray900)
                Text(
                    buildAnnotatedString {
                        append("Dokumentasi  ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Medium)) { append(role.label) }
                    },
                    style = jakarta(12, lineHeight = 16),
                    color = Gray600,
                )
            }
            WajibBadge("WAJIB SEBELUM LANJUT")
        }
        Column(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 140.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Gray50)
                .dashedBorder(Rust, 12.dp, 2.dp)
                .clickable(onClick = onAdd)
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier.size(48.dp).clip(CircleShape).background(White).border(1.dp, Gray400, CircleShape),
                contentAlignment = Alignment.Center,
            ) { AppIcon(R.drawable.ic_camera, 24.dp) }
            Text("Ketuk untuk unggah foto", style = jakarta(14, FontWeight.Bold, 20), color = Gray900, modifier = Modifier.padding(top = 10.dp, bottom = 2.dp))
            Text("Format JPG/PNG, maks. 5MB.", style = jakarta(12, lineHeight = 16), color = Gray500, textAlign = TextAlign.Center)
        }
        if (photos.isNotEmpty()) {
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                photos.forEachIndexed { i, name ->
                    Row(
                        Modifier.widthIn(max = 200.dp).box(Gray50, Rust, 12.dp).padding(9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Box(
                            Modifier.size(32.dp).box(Color(0xFFE5E7EB), Color(0xFFD1D5DB), 8.dp),
                            contentAlignment = Alignment.Center,
                        ) { AppIcon(R.drawable.ic_image_16, 16.dp) }
                        Text(
                            name, style = jakarta(11, FontWeight.SemiBold, 16), color = Color(0xFF1F2937),
                            maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f, fill = false),
                        )
                        Box(Modifier.size(24.dp).clip(RoundedCornerShape(4.dp)).clickable { onRemove(i) }, contentAlignment = Alignment.Center) {
                            AppIcon(R.drawable.ic_x_14, 14.dp)
                        }
                    }
                }
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(top = 6.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppIcon(R.drawable.ic_eye_16, 16.dp)
            Text("Lihat contoh foto yang benar", style = jakarta(12, FontWeight.SemiBold, 16), color = Color(0xFF374151))
        }
    }
}

@Composable
private fun Notice(content: androidx.compose.ui.text.AnnotatedString, error: Boolean) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Secondary50).dashedBorder(if (error) Error600 else Rust, 12.dp).padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(R.drawable.ic_warning_circle_dark16, 16.dp)
        Text(content, style = jakarta(11, lineHeight = 16), color = if (error) Error600 else Stone900)
    }
}

private fun displayName(context: android.content.Context, uri: Uri): String {
    context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c ->
        if (c.moveToFirst()) return c.getString(0)
    }
    return uri.lastPathSegment ?: "foto.jpg"
}

@Composable
fun InputWizardScreen(
    role: Role,
    onClose: () -> Unit,
    onSubmitted: () -> Unit,
    onOpenTraining: () -> Unit,
) {
    val steps = remember(role) { stepsFor(role) }
    var step by remember { mutableIntStateOf(0) }
    val checked = remember { mutableStateMapOf<String, Boolean>() }
    val photos = remember { mutableStateMapOf<Int, List<String>>() }
    var showError by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scroll = rememberScrollState()

    LaunchedEffect(step) { scroll.scrollTo(0); showError = false }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()) {
            photos[step] = (photos[step] ?: emptyList()) + uris.map { displayName(context, it) }
            showError = false
        }
    }

    val current = steps[step]
    val last = step == steps.lastIndex
    fun canContinue() = !current.requiresPhoto || (photos[step]?.isNotEmpty() == true)

    Column(Modifier.fillMaxSize().background(White).statusBarsPadding().navigationBarsPadding()) {
        WizardHeader(role, step, steps.size, if (current.title == "Kepatuhan SOP & Disiplin") "Kepatuhan SOP" else current.title, onClose)
        Column(
            Modifier.verticalScroll(scroll).padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(Modifier.padding(top = 9.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(current.title, style = jakarta(24, FontWeight.ExtraBold, 32, -0.6), color = Zinc900)
                Text(current.subtitle, style = jakarta(12, lineHeight = 16), color = Zinc500, modifier = Modifier.widthIn(max = 230.dp))
            }
            IndicatorCard(current, step, checked, { checked[it] = !(checked[it] ?: false) }, onOpenTraining)
            if (current.requiresPhoto) {
                PhotoSection(
                    role,
                    photos[step] ?: emptyList(),
                    onAdd = { picker.launch("image/*") },
                    onRemove = { i -> photos[step] = (photos[step] ?: emptyList()).filterIndexed { j, _ -> j != i } },
                )
            }
            Notice(
                when {
                    last -> buildAnnotatedString {
                        append("Pastikan semua data input harian telah sesuai sebelum mengirim capaian. Laporan yang sudah dikirim akan langsung diverifikasi oleh ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(if (role == Role.STORE_MANAGER) "Admin" else "Store Manager") }
                    }
                    current.requiresPhoto -> buildAnnotatedString {
                        append("Semua indikator penilaian dan 1 foto bukti wajib dilengkapi sebelum Anda dapat melangkah ke tahap ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Selanjutnya.") }
                    }
                    else -> buildAnnotatedString { append("Ketuk baris untuk menandai ceklis selesai dikerjakan.") }
                },
                error = showError,
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.fillMaxWidth().height(1.dp).background(Zinc200))
                Row(Modifier.padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        Modifier.width(93.dp).heightIn(min = 48.dp).clip(RoundedCornerShape(12.dp)).background(White)
                            .border(2.dp, Neutral300, RoundedCornerShape(12.dp))
                            .clickable { if (step == 0) onClose() else step-- },
                        contentAlignment = Alignment.Center,
                    ) { Text("Kembali", style = jakarta(14, FontWeight.Bold, 20), color = Zinc800) }
                    Row(
                        Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp)
                            .shadow(9.dp, RoundedCornerShape(12.dp), ambientColor = Primary500.copy(0.3f), spotColor = Primary500.copy(0.3f))
                            .box(Primary500)
                            .clickable {
                                if (!canContinue()) {
                                    showError = true
                                    Toast.makeText(context, "Unggah minimal 1 foto bukti terlebih dahulu", Toast.LENGTH_SHORT).show()
                                } else if (last) {
                                    submitted = true
                                } else {
                                    step++
                                }
                            },
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(if (last) "Kirim Semua" else "Lanjut", style = jakarta(14, FontWeight.Bold, 20), color = White)
                        if (!last) AppIcon(R.drawable.ic_arrow_right_white, 16.dp)
                    }
                }
                if (last) {
                    Text(
                        "Simpan sebagai Draf Sementara",
                        style = jakarta(11, lineHeight = 16).copy(textDecoration = TextDecoration.Underline),
                        color = Neutral500,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable {
                            Toast.makeText(context, "Draf tersimpan", Toast.LENGTH_SHORT).show()
                            onClose()
                        }.padding(top = 4.dp),
                    )
                } else {
                    Text(
                        "Langkah ${step + 1} dari ${steps.size}",
                        style = jakarta(11, lineHeight = 16),
                        color = Zinc400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                    )
                }
            }
        }
    }

    if (submitted) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = { submitted = false; onSubmitted() }) {
                    Text("Kembali ke Beranda", style = jakarta(14, FontWeight.Bold), color = Primary500)
                }
            },
            title = { Text("Capaian terkirim", style = jakarta(18, FontWeight.Bold)) },
            text = {
                Text(
                    "Laporan hari ini sudah dikirim dan menunggu verifikasi ${if (role == Role.STORE_MANAGER) "Admin" else "Store Manager"}.",
                    style = jakarta(14, lineHeight = 20),
                )
            },
            containerColor = White,
        )
    }
}
