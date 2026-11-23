package com.example.kpiwonosari.ui.screens.shared

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.PrimaryButton
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.SecondaryCard
import com.example.kpiwonosari.ui.theme.Success500
import com.example.kpiwonosari.ui.theme.TextStrong
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

private val RoleChipColor = Color(0xFF9A3412)

/** Header with back button, title and menu button (used by Profil / Riwayat pages). */
@Composable
fun TitleMenuHeader(title: String, onBack: () -> Unit, onMenu: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xCCF7F9FF))
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            Modifier.size(44.dp).box(Secondary50, Secondary200).clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) { AppIcon(R.drawable.ic_arrow_left, 24.dp) }
        Text(title, style = jakarta(16, FontWeight.SemiBold, 20), color = TextStrong, modifier = Modifier.weight(1f))
        Box(
            Modifier.size(44.dp).box(Neutral50, Secondary200).clickable(onClick = onMenu),
            contentAlignment = Alignment.Center,
        ) { AppIcon(R.drawable.ic_menu, 20.dp) }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text.uppercase(),
        style = jakarta(11, FontWeight.SemiBold, 14, 0.55),
        color = Neutral500,
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 8.dp),
    )
}

@Composable
fun ProfilScreen(role: Role, onBack: () -> Unit, onMenu: () -> Unit, onLogout: () -> Unit) {
    var email by remember { mutableStateOf("User@capaian.id") }
    var editOpen by remember { mutableStateOf(false) }
    var newEmail by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(Neutral50).statusBarsPadding().navigationBarsPadding().imePadding()) {
        TitleMenuHeader("Profil", onBack, onMenu)
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(31.dp),
        ) {
            // Profile summary card
            Column(
                Modifier.fillMaxWidth().box(Secondary50, Neutral300).padding(vertical = 21.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    Modifier.size(80.dp).clip(CircleShape).background(Secondary50).border(1.dp, Secondary200, CircleShape),
                    contentAlignment = Alignment.Center,
                ) { AppIcon(R.drawable.ic_user_profile, 38.dp) }
                Spacer(Modifier.height(12.dp))
                Text("User", style = jakarta(20, FontWeight.Bold, 28, -0.2), color = Neutral800)
                Spacer(Modifier.height(8.dp))
                Box(Modifier.box(SecondaryCard, RoleChipColor, 9999.dp).padding(horizontal = 12.dp, vertical = 4.dp)) {
                    Text(role.label.uppercase(), style = jakarta(11, FontWeight.SemiBold, 14, 0.275), color = RoleChipColor)
                }
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(Primary500))
                    Text("Akun Aktif", style = jakarta(12, lineHeight = 16), color = Neutral800)
                }
            }

            Column {
                SectionLabel("Informasi Akun")
                Row(
                    Modifier.fillMaxWidth().box(Secondary50, Neutral300).heightIn(min = 52.dp).padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) { AppIcon(R.drawable.ic_mail, 21.dp) }
                    Spacer(Modifier.width(12.dp))
                    Text("Email", style = jakarta(14, lineHeight = 20), color = Neutral800, modifier = Modifier.weight(1f))
                    Text(email, style = jakarta(14, FontWeight.Medium, 20), color = Neutral900, textAlign = TextAlign.End, maxLines = 1)
                }
            }

            Column {
                SectionLabel("Ubah Email")
                Column(Modifier.fillMaxWidth().box(Secondary50, Neutral300)) {
                    Row(
                        Modifier.fillMaxWidth().clickable { editOpen = !editOpen }.heightIn(min = 52.dp).padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) { AppIcon(R.drawable.ic_mail, 21.dp) }
                        Spacer(Modifier.width(12.dp))
                        Text(email, style = jakarta(14, lineHeight = 20), color = Neutral800, modifier = Modifier.weight(1f))
                        AppIcon(if (editOpen) R.drawable.ic_nav_arrow_up else R.drawable.ic_nav_arrow_down, 20.dp)
                    }
                    if (editOpen) {
                        Box(Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(1.dp).background(Neutral300))
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Email Baru", style = jakarta(12, FontWeight.SemiBold, 16), color = Neutral500)
                                Row(
                                    Modifier.fillMaxWidth().height(44.dp).box(White, Neutral300, 8.dp).padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    AppIcon(R.drawable.ic_mail_input, 20.dp)
                                    Box(Modifier.weight(1f)) {
                                        if (newEmail.isEmpty()) Text("Masukkan email baru", style = jakarta(14, lineHeight = 20), color = Neutral500)
                                        BasicTextField(
                                            newEmail, { newEmail = it }, singleLine = true,
                                            textStyle = jakarta(14, lineHeight = 20).copy(color = Neutral900),
                                            cursorBrush = SolidColor(Primary500),
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                            modifier = Modifier.fillMaxWidth(),
                                        )
                                    }
                                }
                            }
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)) {
                                Box(
                                    Modifier.box(White, Neutral300, 8.dp).clickable { editOpen = false; newEmail = "" }
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                ) { Text("Batal", style = jakarta(14, FontWeight.SemiBold, 20), color = Neutral800) }
                                Box(
                                    Modifier.box(Success500, radius = 8.dp).clickable {
                                        if (newEmail.contains("@")) email = newEmail
                                        editOpen = false
                                        newEmail = ""
                                    }.padding(horizontal = 16.dp, vertical = 10.dp),
                                ) { Text("Simpan", style = jakarta(14, FontWeight.Bold, 20), color = White) }
                            }
                        }
                    }
                }
            }

            Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PrimaryButton(
                    "Keluar (Logout)",
                    onClick = onLogout,
                    height = 48.dp,
                    textSize = 14,
                    leading = { AppIcon(R.drawable.ic_log_out, 20.dp) },
                )
                Column(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("CAPAIAN.ID", style = jakarta(12, lineHeight = 16), color = Neutral500)
                    Text(
                        "Sistem Pemantauan Kinerja Karyawan Toko Roti Wonosari",
                        style = jakarta(11, FontWeight.SemiBold, 14, 0.44),
                        color = Neutral500,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        }
    }
}
