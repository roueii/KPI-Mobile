package com.example.kpiwonosari.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.ui.components.AppIcon
import com.example.kpiwonosari.ui.components.FieldLabel
import com.example.kpiwonosari.ui.components.PrimaryButton
import com.example.kpiwonosari.ui.components.box
import com.example.kpiwonosari.ui.theme.BrandDark
import com.example.kpiwonosari.ui.theme.CreamBg
import com.example.kpiwonosari.ui.theme.CreamFade
import com.example.kpiwonosari.ui.theme.Error600
import com.example.kpiwonosari.ui.theme.Neutral200
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary100
import com.example.kpiwonosari.ui.theme.Primary200
import com.example.kpiwonosari.ui.theme.Primary300
import com.example.kpiwonosari.ui.theme.Primary50
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary800
import com.example.kpiwonosari.ui.theme.Secondary900
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.Zinc400
import com.example.kpiwonosari.ui.theme.jakarta

/** Demo accounts: the email decides which role's screens are shown. */
private val demoAccounts = mapOf(
    "admin@capaian.id" to Role.ADMIN,
    "manager@capaian.id" to Role.STORE_MANAGER,
    "frontliner@capaian.id" to Role.FRONTLINER,
    "produksi@capaian.id" to Role.PRODUKSI,
)
private const val DEMO_PASSWORD = "wonosari"

/** Full-screen cream background with the faint kawung batik texture. */
@Composable
private fun KawungBackground() {
    Box(Modifier.fillMaxSize().background(CreamBg))
    Image(
        painterResource(R.drawable.kawung_texture),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize().alpha(0.06f),
    )
}

/** Orange gradient hero (520dp) with kawung overlay and bottom fade. */
@Composable
private fun HeroBand(fadeTop: Int) {
    Box(Modifier.fillMaxWidth().height(560.dp).clipToBounds()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(520.dp)
                .alpha(0.95f)
                .background(
                    Brush.verticalGradient(
                        0f to Primary500,
                        0.55f to Primary200,
                        1f to Primary100,
                    ),
                ),
        )
        Image(
            painterResource(R.drawable.kawung_texture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(520.dp).alpha(0.14f),
        )
        Box(
            Modifier
                .offset(y = fadeTop.dp)
                .fillMaxWidth()
                .height(120.dp)
                .background(Brush.verticalGradient(listOf(Color(0x00E3D6D6), CreamFade))),
        )
    }
}

@Composable
fun SplashScreen(onContinue: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val screenH = maxHeight
        KawungBackground()
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(Modifier.fillMaxWidth().height((screenH - 389.dp).coerceAtLeast(520.dp)).clipToBounds()) {
                HeroBand(fadeTop = 440)
                // brand-block: top 187dp
                Column(
                    Modifier.fillMaxWidth().padding(top = 148.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // logo-wonosari 291x168 with the image cropped as in the design
                    Box(Modifier.size(291.dp, 168.dp)) {
                        Image(
                            painterResource(R.drawable.logo_wonosari),
                            contentDescription = "Logo Roti Wonosari",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.offset(x = 17.1.dp, y = (-0.5).dp).size(256.dp, 168.3.dp),
                        )
                    }
                    Text(
                        "CAPAIAN.ID",
                        style = jakarta(44, FontWeight.Bold, 48, -1.2),
                        color = Secondary900,
                        modifier = Modifier.offset(y = (-19).dp),
                    )
                    Box(
                        Modifier
                            .offset(y = (-6).dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .border(1.dp, Color.White.copy(alpha = 0.27f), CircleShape)
                            .padding(horizontal = 18.dp, vertical = 6.dp),
                    ) {
                        Text("Pantau kinerja harianmu", style = jakarta(13, lineHeight = 18), color = Secondary800)
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp), ambientColor = Color.Black.copy(0.08f), spotColor = Color.Black.copy(0.08f))
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(White)
                    .border(1.dp, Neutral200, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .heightIn(min = 389.dp)
                    .navigationBarsPadding()
                    .padding(start = 28.dp, end = 28.dp, top = 36.dp, bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        Modifier.box(Primary50, Primary300, 999.dp).padding(horizontal = 12.dp, vertical = 4.dp),
                    ) {
                        Text(
                            "SISTEM PENILAIAN KARYAWAN",
                            style = jakarta(10, FontWeight.SemiBold, letterSpacing = 0.8),
                            color = Primary500,
                        )
                    }
                    Text(
                        "Kelola & Pantau Kinerja Tim dengan Mudah",
                        style = jakarta(22, FontWeight.Bold, 30, -0.4),
                        color = Neutral900,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        "Platform digital untuk mencatat, mengevaluasi, dan meningkatkan produktivitas karyawan setiap hari.",
                        style = jakarta(13, lineHeight = 20),
                        color = Neutral400,
                        textAlign = TextAlign.Center,
                    )
                }
                Box(
                    Modifier.fillMaxWidth().height(44.dp).box(Primary500).clickable(onClick = onContinue),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Masuk ke akun Anda", style = jakarta(13, FontWeight.SemiBold), color = White)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (Role) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    fun submit() {
        val role = demoAccounts[email.trim().lowercase()]
        emailError = role == null
        passwordError = role != null && password != DEMO_PASSWORD
        if (role != null && !passwordError) onLogin(role)
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val screenH = maxHeight
        KawungBackground()
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).imePadding()) {
            Box(Modifier.fillMaxWidth().height(413.dp)) {
                Box(Modifier.wrapContentSize(unbounded = true, align = Alignment.TopCenter)) { HeroBand(fadeTop = 400) }
                Column(
                    Modifier.fillMaxWidth().padding(top = 127.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // "image 2": chef-hat crop of the logo
                    Box(Modifier.size(144.dp, 95.dp).clipToBounds()) {
                        Image(
                            painterResource(R.drawable.logo_wonosari),
                            contentDescription = "Logo",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .wrapContentSize(unbounded = true, align = Alignment.TopStart)
                                .offset(x = (-117.9).dp, y = (-13.2).dp)
                                .requiredSize(380.5.dp, 251.4.dp),
                        )
                    }
                    Text("CAPAIAN.ID", style = jakarta(38, FontWeight.Bold, 42, -1.0), color = BrandDark)
                    Box(
                        Modifier.clip(CircleShape).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 14.dp, vertical = 5.dp),
                    ) {
                        Text("Pantau kinerja harianmu", style = jakarta(13, lineHeight = 18), color = BrandDark)
                    }
                }
            }
            val shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = (screenH - 413.dp).coerceAtLeast(496.dp))
                    .shadow(15.dp, shape, ambientColor = Color.Black.copy(0.04f), spotColor = Color.Black.copy(0.04f))
                    .clip(shape)
                    .background(White)
                    .border(1.dp, Neutral300, shape)
                    .navigationBarsPadding()
                    .padding(start = 28.dp, end = 28.dp, top = 32.dp, bottom = 48.dp),
            ) {
                Text(
                    "Masuk ke Akun",
                    style = jakarta(20, FontWeight.Bold, 28, -0.5),
                    color = Neutral800,
                    modifier = Modifier.padding(bottom = 24.dp),
                )
                FieldLabel("Email Akun", Modifier.padding(bottom = 8.dp))
                LoginInput(
                    value = email,
                    onValueChange = { email = it; emailError = false },
                    isError = emailError,
                    keyboardType = KeyboardType.Email,
                    filled = emailError || passwordError,
                )
                if (emailError) {
                    Text(
                        "Email tidak sesuai. Periksa kembali email yang Anda masukkan.",
                        style = jakarta(12, lineHeight = 18),
                        color = Error600,
                    )
                }
                Spacer(Modifier.height(16.dp))
                FieldLabel("Kata Sandi", Modifier.padding(bottom = 8.dp))
                LoginInput(
                    value = password,
                    onValueChange = { password = it; passwordError = false },
                    isError = passwordError,
                    keyboardType = KeyboardType.Password,
                    hidden = !showPassword,
                    filled = emailError || passwordError,
                    trailing = {
                        AppIcon(
                            if (showPassword) R.drawable.eye_alt else R.drawable.eye_close,
                            24.dp,
                            Modifier.clip(CircleShape).clickable { showPassword = !showPassword },
                        )
                    },
                    onDone = { submit() },
                )
                if (passwordError) {
                    Text(
                        "Kata sandi salah. Periksa kembali kata sandi yang Anda masukkan.",
                        style = jakarta(12, lineHeight = 18),
                        color = Error600,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Spacer(Modifier.height(16.dp))
                } else {
                    Spacer(Modifier.height(if (emailError) 52.dp else 43.5.dp))
                }
                PrimaryButton("Masuk", onClick = { submit() })
            }
        }
    }
}

@Composable
private fun LoginInput(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    keyboardType: KeyboardType,
    filled: Boolean,
    hidden: Boolean = false,
    trailing: (@Composable () -> Unit)? = null,
    onDone: (() -> Unit)? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp)
            .shadow(1.dp, RoundedCornerShape(12.dp), ambientColor = Color.Black.copy(0.05f), spotColor = Color.Black.copy(0.05f))
            .box(if (filled) White else Neutral50, if (isError) Error600 else Neutral300)
            .padding(start = 20.dp, end = 15.67.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f).padding(vertical = 15.dp)) {
            if (value.isEmpty()) {
                if (hidden && !filled) {
                    // closed-password placeholder: four grey dots
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                        repeat(4) { AppIcon(R.drawable.dot, 7.dp) }
                    }
                } else {
                    Text("Ketik di sini", style = jakarta(14), color = Zinc400)
                }
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = jakarta(14).copy(color = Neutral800),
                cursorBrush = SolidColor(Primary500),
                visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = if (onDone != null) ImeAction.Done else ImeAction.Next,
                ),
                keyboardActions = androidx.compose.foundation.text.KeyboardActions(onDone = { onDone?.invoke() }),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}
