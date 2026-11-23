package com.example.kpiwonosari.ui.screens.admin

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.BackHeader
import com.example.kpiwonosari.ui.components.PillTabs
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.Secondary500
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

private val InputStroke = Color(0xFF959594)

private data class Employee(
    val name: String,
    val division: String,
    val email: String,
    val password: String,
    val active: Boolean = true,
)

private val divisions = listOf("Store Manager", "Frontliner", "Produksi", "Driver", "Sales/Marketing", "SPV")

/** Dashed rounded border, used by inactive rows and the danger-zone button. */
fun Modifier.dashedBorder(color: Color, radius: Dp, width: Dp = 1.dp): Modifier = this.drawBehind {
    val w = width.toPx()
    drawRoundRect(
        color = color,
        topLeft = androidx.compose.ui.geometry.Offset(w / 2, w / 2),
        size = androidx.compose.ui.geometry.Size(size.width - w, size.height - w),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius.toPx()),
        style = Stroke(width = w, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 4.dp.toPx()))),
    )
}

@Composable
fun KelolaAkunScreen(onBack: () -> Unit) {
    val employees = remember {
        mutableStateListOf(
            Employee("Budi Santoso", "Frontliner", "budi.frontliner@capaian.id", "Budi1234"),
            Employee("Siti Rahma", "Store Manager", "siti.manager@capaian.id", "Siti1234"),
            Employee("Joko Prasetyo", "Produksi", "joko.produksi@capaian.id", "Joko1234"),
            Employee("Agus Hermawan", "Driver", "agus.driver@capaian.id", "Agus1234"),
            Employee("Dewi Lestari", "Frontliner", "dewi.frontliner@capaian.id", "Dewi1234"),
            Employee("Hendra Wijaya", "Produksi", "hendra.produksi@capaian.id", "Hendra12", active = false),
        )
    }
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableIntStateOf(0) }
    var editing by remember { mutableStateOf<String?>("Budi Santoso") }
    var adding by remember { mutableStateOf(false) }

    val counts = mapOf("Store Manager" to 2, "Frontliner" to 8, "Produksi" to 7, "Driver" to 1, "Sales/Marketing" to 1, "SPV" to 1)
    val tabs = listOf("Semua (23)") + divisions.map { "$it (${counts[it]})" }

    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding().imePadding()) {
        BackHeader("Kelola  Akun", "Laporan yang sudah disetujui Store Manager", onBack)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Search + add
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Row(
                    Modifier.weight(1f).height(44.dp).box(Neutral100, Neutral300, 14.dp).padding(start = 12.dp, end = 13.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIcon(R.drawable.ic_search, 15.dp)
                    Spacer(Modifier.width(10.dp))
                    Box(Modifier.weight(1f)) {
                        if (query.isEmpty()) Text("Cari nama karyawan...", style = jakarta(14), color = Neutral400)
                        BasicTextField(
                            query, { query = it }, singleLine = true,
                            textStyle = jakarta(14).copy(color = Neutral900),
                            cursorBrush = SolidColor(Primary500),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Row(
                    Modifier.height(44.dp).box(Primary500, radius = 14.dp).clickable { adding = true }.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    AppIcon(R.drawable.ic_plus_white, 10.5.dp)
                    Text("Tambah", style = jakarta(12, FontWeight.Bold, 16, 0.24), color = Neutral50)
                }
            }
            PillTabs(tabs, filter, { filter = it }, boldSelected = true)
            Text("DAFTAR AKUN KARYAWAN", style = jakarta(11, FontWeight.SemiBold, 14, 0.55), color = Neutral400)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                employees
                    .filter { filter == 0 || it.division == divisions[filter - 1] }
                    .filter { query.isBlank() || it.name.contains(query, ignoreCase = true) }
                    .forEach { e ->
                        val open = editing == e.name
                        EmployeeRow(e, open) { editing = if (open) null else e.name }
                        if (open) {
                            EditPanel(
                                e,
                                onClose = { editing = null },
                                onSave = { updated ->
                                    val i = employees.indexOfFirst { it.name == e.name }
                                    if (i >= 0) employees[i] = updated
                                    editing = null
                                },
                                onDeactivate = {
                                    val i = employees.indexOfFirst { it.name == e.name }
                                    if (i >= 0) employees[i] = e.copy(active = false)
                                    editing = null
                                },
                            )
                        }
                    }
            }
        }
    }

    if (adding) {
        AddEmployeeDialog(onDismiss = { adding = false }) { employees.add(0, it); adding = false }
    }
}

@Composable
private fun EmployeeRow(e: Employee, open: Boolean, onClick: () -> Unit) {
    val active = e.active
    Row(
        Modifier
            .fillMaxWidth()
            .box(if (active) Secondary50 else Color(0xFFF6F6F5), if (active) Secondary500 else Color(0xFFDFDDDB), 16.dp)
            .clickable(onClick = onClick)
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Secondary50)
                .then(if (active) Modifier.border(1.dp, Secondary500, CircleShape) else Modifier.dashedBorder(Secondary200, 20.dp)),
            contentAlignment = Alignment.Center,
        ) { AppIcon(if (active) R.drawable.ic_user_brown else R.drawable.ic_user_grey, 20.dp) }
        Column(Modifier.weight(1f)) {
            Text(e.name, style = jakarta(16, if (active) FontWeight.Bold else FontWeight.SemiBold, 22), color = if (active) Neutral900 else Neutral400)
            Text(
                e.division,
                style = jakarta(11, FontWeight.SemiBold, 14, 0.33),
                color = if (active) Secondary500 else Neutral400,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        if (active) {
            Box(Modifier.box(Primary500, Primary500, 9999.dp).padding(horizontal = 9.dp, vertical = 3.dp)) {
                Text("Aktif", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral50)
            }
        } else {
            Box(Modifier.dashedBorder(Secondary200, 9999.dp).padding(horizontal = 9.dp, vertical = 3.dp)) {
                Text("Nonaktif", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral400)
            }
        }
        if (open) {
            AppIcon(R.drawable.ic_caret_up, 10.dp, Modifier.size(10.dp, 6.17.dp))
        } else {
            AppIcon(
                if (active) R.drawable.ic_chevron_brown else R.drawable.ic_chevron_grey,
                0.dp,
                Modifier.size(6.5.dp, 11.5.dp).graphicsLayer(rotationZ = if (active) 90f else -90f),
            )
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(text, style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral400)
}

@Composable
private fun FormInput(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "",
    password: Boolean = false,
) {
    var show by remember { mutableStateOf(false) }
    Row(
        Modifier.fillMaxWidth().height(40.dp).box(White, InputStroke, 14.dp).padding(horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) Text(placeholder, style = jakarta(14), color = Neutral900)
            BasicTextField(
                value, onChange, singleLine = true,
                textStyle = jakarta(14).copy(color = Neutral900),
                cursorBrush = SolidColor(Primary500),
                visualTransformation = if (password && !show) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (password) {
            AppIcon(
                if (show) R.drawable.eye_alt else R.drawable.ic_eye_close_dark,
                24.dp,
                Modifier.clip(CircleShape).clickable { show = !show },
            )
        }
    }
}

@Composable
private fun DivisionSelect(value: String?, onChange: (String) -> Unit) {
    var open by remember { mutableStateOf(false) }
    Box {
        Row(
            Modifier.fillMaxWidth().height(40.dp).box(White, InputStroke, 14.dp).clickable { open = true }.padding(start = 13.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(value ?: "Pilih divisi", style = jakarta(14, lineHeight = 20), color = Neutral900, modifier = Modifier.weight(1f))
            AppIcon(if (open) R.drawable.ic_caret_up else R.drawable.ic_caret_down, 10.dp, Modifier.size(10.dp, 6.17.dp))
        }
        DropdownMenu(open, { open = false }) {
            divisions.forEach { d ->
                DropdownMenuItem(text = { Text(d, style = jakarta(14)) }, onClick = { onChange(d); open = false })
            }
        }
    }
}

@Composable
private fun EditPanel(e: Employee, onClose: () -> Unit, onSave: (Employee) -> Unit, onDeactivate: () -> Unit) {
    var name by remember(e.name) { mutableStateOf(e.name) }
    var email by remember(e.name) { mutableStateOf(e.email) }
    var password by remember(e.name) { mutableStateOf(e.password) }
    var division by remember(e.name) { mutableStateOf(e.division) }
    var active by remember(e.name) { mutableStateOf(e.active) }

    Column(
        Modifier.fillMaxWidth().box(Secondary50, Secondary500, 16.dp).padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column {
            Row(Modifier.fillMaxWidth().padding(bottom = 9.dp), verticalAlignment = Alignment.CenterVertically) {
                AppIcon(R.drawable.ic_page_edit_dark, 14.dp)
                Text(
                    "EDIT DATA KARYAWAN: ${e.name.uppercase()}",
                    style = jakarta(14, FontWeight.Bold, 20, 0.14),
                    color = Neutral900,
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    maxLines = 1,
                )
                Box(Modifier.size(28.dp).clip(CircleShape).clickable(onClick = onClose), contentAlignment = Alignment.Center) {
                    AppIcon(R.drawable.ic_cancel_small, 15.dp)
                }
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Nama Lengkap"); FormInput(name, { name = it }) }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Email Akun"); FormInput(email, { email = it }) }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Password Akun"); FormInput(password, { password = it }, password = true) }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Divisi Kerja"); DivisionSelect(division) { division = it } }
        Column(Modifier.padding(vertical = 4.dp)) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
            Row(Modifier.fillMaxWidth().padding(vertical = 9.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Status Akun Aktif", style = jakarta(12, FontWeight.Bold, 16, 0.24), color = Neutral900)
                    Text("Karyawan dapat login dan mengisi KPI harian", style = jakarta(12, lineHeight = 16), color = Neutral400)
                }
                Switch(active) { active = it }
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
        }
        Row(Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                Modifier.weight(1f).height(44.dp).box(White, InputStroke, 14.dp).clickable(onClick = onClose),
                contentAlignment = Alignment.Center,
            ) { Text("Batal", style = jakarta(12, FontWeight.SemiBold, 16, 0.24), color = Neutral900) }
            Box(
                Modifier.weight(1f).height(44.dp).box(Primary500, radius = 14.dp).clickable {
                    onSave(Employee(name, division, email, password, active))
                },
                contentAlignment = Alignment.Center,
            ) { Text("Simpan Perubahan", style = jakarta(12, FontWeight.Bold, 16, 0.24), color = Neutral50) }
        }
        Row(
            Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(40.dp)
                .dashedBorder(Secondary200, 14.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable(onClick = onDeactivate),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppIcon(R.drawable.ic_warning_triangle, 0.dp, Modifier.size(14.67.dp, 12.67.dp))
            Text("Nonaktifkan Akun Karyawan", style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Secondary500)
        }
    }
}

@Composable
fun Switch(checked: Boolean, onChange: (Boolean) -> Unit) {
    Box(
        Modifier
            .size(48.dp, 24.dp)
            .clip(CircleShape)
            .background(if (checked) Primary500 else Neutral300)
            .clickable { onChange(!checked) }
            .padding(2.dp),
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Box(Modifier.size(20.dp).clip(CircleShape).background(White))
    }
}

@Composable
private fun AddEmployeeDialog(onDismiss: () -> Unit, onSave: (Employee) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var division by remember { mutableStateOf<String?>(null) }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Secondary50)
                .border(2.dp, Secondary500, RoundedCornerShape(16.dp))
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column {
                Row(Modifier.fillMaxWidth().padding(bottom = 13.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Tambah Karyawan Baru", style = jakarta(16, FontWeight.Bold, 22), color = Neutral900)
                        Text("Buat kredensial akun staf bakery", style = jakarta(12, lineHeight = 16), color = Neutral400)
                    }
                    Box(
                        Modifier.size(32.dp).box(Secondary50, Neutral300).clickable(onClick = onDismiss),
                        contentAlignment = Alignment.Center,
                    ) { AppIcon(R.drawable.ic_cancel_grey, 24.dp) }
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Nama Lengkap"); FormInput(name, { name = it }, "Nama lengkap karyawan") }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Email Akun"); FormInput(email, { email = it }, "nama@capaian.id") }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Password Akun"); FormInput(password, { password = it }, "Password 8 karakter", password = true) }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { FormLabel("Divisi Kerja"); DivisionSelect(division) { division = it } }
            Row(
                Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(44.dp)
                    .box(Primary500, radius = 14.dp)
                    .clickable(enabled = name.isNotBlank() && email.isNotBlank() && division != null) {
                        onSave(Employee(name, division!!, email, password))
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppIcon(R.drawable.ic_check_white, 0.dp, Modifier.size(12.2.dp, 9.dp))
                Text("Simpan Akun Baru", style = jakarta(12, FontWeight.Bold, 16, 0.24), color = Neutral50)
            }
        }
    }
}
