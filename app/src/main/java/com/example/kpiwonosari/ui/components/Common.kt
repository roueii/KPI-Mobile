package com.example.kpiwonosari.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kpiwonosari.R
import com.example.kpiwonosari.ui.theme.Neutral100
import com.example.kpiwonosari.ui.theme.Neutral300
import com.example.kpiwonosari.ui.theme.Neutral400
import com.example.kpiwonosari.ui.theme.Neutral500
import com.example.kpiwonosari.ui.theme.Neutral50
import com.example.kpiwonosari.ui.theme.Neutral800
import com.example.kpiwonosari.ui.theme.Neutral900
import com.example.kpiwonosari.ui.theme.Primary500
import com.example.kpiwonosari.ui.theme.Secondary200
import com.example.kpiwonosari.ui.theme.Secondary50
import com.example.kpiwonosari.ui.theme.White
import com.example.kpiwonosari.ui.theme.jakarta

/** Draws a vector/bitmap resource at its design size, keeping the colors baked into the asset. */
@Composable
fun AppIcon(@DrawableRes res: Int, size: Dp, modifier: Modifier = Modifier) {
    Image(painter = painterResource(res), contentDescription = null, modifier = modifier.size(size))
}

/** Background + 1dp border + rounded corners, the most common container style in the design. */
fun Modifier.box(
    background: Color,
    border: Color? = null,
    radius: Dp = 12.dp,
    shape: Shape = RoundedCornerShape(radius),
): Modifier {
    var m = this.clip(shape).background(background, shape)
    if (border != null) m = m.border(1.dp, border, shape)
    return m
}

/** Clickable without the ripple overflowing rounded shapes (shape is applied by [box] first). */
fun Modifier.tap(onClick: () -> Unit): Modifier = this.clickable(onClick = onClick)

@Composable
fun Modifier.tapNoRipple(onClick: () -> Unit): Modifier = this.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
    onClick = onClick,
)

/** Orange primary button (radius 12, min height 50). */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 50.dp,
    textSize: Int = 16,
    enabled: Boolean = true,
    color: Color = Primary500,
    shadow: Boolean = true,
    leading: (@Composable RowScope.() -> Unit)? = null,
) {
    val base = if (shadow) modifier.shadow(9.dp, RoundedCornerShape(12.dp), ambientColor = Primary500.copy(alpha = 0.3f), spotColor = Primary500.copy(alpha = 0.3f)) else modifier
    Row(
        modifier = base
            .fillMaxWidth()
            .heightIn(min = height)
            .box(if (enabled) color else color.copy(alpha = 0.5f))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leading?.invoke(this)
        Text(text, style = jakarta(textSize, FontWeight.SemiBold, 24), color = White, textAlign = TextAlign.Center)
    }
}

/** White outlined secondary button. */
@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Neutral800,
    background: Color = White,
    border: Color = Neutral300,
    radius: Dp = 12.dp,
    height: Dp = 48.dp,
    textSize: Int = 14,
    leading: (@Composable RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = height)
            .box(background, border, radius)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leading?.invoke(this)
        Text(text, style = jakarta(textSize, FontWeight.SemiBold), color = textColor, textAlign = TextAlign.Center)
    }
}

/** Floating dashboard header: dark avatar, greeting + role, and the menu button on the right. */
@Composable
fun DashboardHeader(
    greeting: String,
    role: String,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth().padding(15.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(16.dp), ambientColor = Color.Black.copy(0.04f), spotColor = Color.Black.copy(0.04f))
                .box(Secondary50, Secondary200, 16.dp)
                .padding(start = 20.dp, end = 10.dp)
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(32.dp).clip(CircleShape).background(Neutral800),
                contentAlignment = Alignment.Center,
            ) { AppIcon(R.drawable.ic_user, 20.dp) }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(greeting, style = jakarta(16, FontWeight.Bold, 20), color = Neutral800, maxLines = 1)
                Text(role, style = jakarta(11, FontWeight.SemiBold, 14, 0.33), color = Neutral500, maxLines = 1)
            }
            Box(
                Modifier.size(44.dp).box(Neutral50, Secondary200).clickable(onClick = onMenu),
                contentAlignment = Alignment.Center,
            ) { AppIcon(R.drawable.ic_menu, 20.dp) }
        }
    }
}

/** White top bar with brown back button, title and optional subtitle. */
@Composable
fun BackHeader(
    title: String,
    subtitle: String? = null,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
) {
    Column(modifier.fillMaxWidth().background(White)) {
        Row(
            Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                Modifier.size(44.dp).box(Secondary50, Secondary200).clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) { AppIcon(R.drawable.ic_arrow_left, 24.dp) }
            Column(Modifier.weight(1f)) {
                Text(title, style = jakarta(16, FontWeight.Bold, 22), color = Neutral900, maxLines = 1)
                if (subtitle != null) {
                    Text(subtitle, style = jakarta(12, lineHeight = 18), color = Neutral500, maxLines = 1)
                }
            }
            trailing?.invoke()
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Neutral300))
    }
}

/** Horizontally scrolling pill tabs (44dp tall). */
@Composable
fun PillTabs(
    tabs: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 44.dp,
    boldSelected: Boolean = false,
) {
    Row(
        modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        tabs.forEachIndexed { i, t ->
            val active = i == selected
            Box(
                Modifier
                    .height(height)
                    .box(if (active) Primary500 else White, if (active) null else Neutral300, 999.dp)
                    .clickable { onSelect(i) }
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    t,
                    style = jakarta(12, if (active && boldSelected) FontWeight.Bold else FontWeight.SemiBold, 18),
                    color = if (active) White else Neutral900,
                    maxLines = 1,
                )
            }
        }
    }
}

/** Rounded progress bar used by division summaries. */
@Composable
fun ProgressBar(
    fraction: Float,
    modifier: Modifier = Modifier,
    track: Color = Secondary200,
    fill: Color = Primary500,
    height: Dp = 10.dp,
) {
    Box(modifier.fillMaxWidth().height(height).clip(CircleShape).background(track)) {
        Box(
            Modifier
                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                .height(height)
                .clip(CircleShape)
                .background(fill),
        )
    }
}

/** Small rounded label chip. */
@Composable
fun Chip(
    text: String,
    background: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    radius: Dp = 9999.dp,
    size: Int = 11,
    weight: FontWeight = FontWeight.SemiBold,
    horizontal: Dp = 8.dp,
    vertical: Dp = 4.dp,
    border: Color? = null,
    leading: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier.box(background, border, radius).padding(horizontal = horizontal, vertical = vertical),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        leading?.invoke()
        Text(text, style = jakarta(size, weight, 14), color = textColor, maxLines = 1)
    }
}

/** Web-style footer shown at the bottom of dashboards. */
@Composable
fun AppFooter(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .box(Secondary50, Secondary200, 12.dp)
            .padding(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("CAPAIAN.ID", style = jakarta(12, FontWeight.Bold, 16, 0.3), color = Neutral800)
        Spacer(Modifier.height(6.dp))
        Text("Portal Manajemen Kinerja Roti Wonosari", style = jakarta(12, lineHeight = 16), color = Neutral500, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            val s = jakarta(11, FontWeight.SemiBold, 14, 0.33)
            Text("Pusat Bantuan", style = s, color = Neutral500)
            Text("•", style = s, color = Neutral500)
            Text("Kebijakan Privasi", style = s, color = Neutral500)
            Text("•", style = s, color = Neutral500)
            Text("Keluar", style = s, color = Neutral500, modifier = Modifier.clickable(onClick = onLogout))
        }
    }
}

/** Card section title + optional subtitle on the left and trailing content on the right. */
@Composable
fun SectionTitle(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    titleColor: Color = com.example.kpiwonosari.ui.theme.TextStrong,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = jakarta(18, FontWeight.Bold, 24), color = titleColor)
            if (subtitle != null) Text(subtitle, style = jakarta(13, lineHeight = 18), color = com.example.kpiwonosari.ui.theme.TextMuted)
        }
        trailing?.invoke()
    }
}

/** Grey label above form inputs (uppercase, 11sp semibold). */
@Composable
fun FieldLabel(text: String, modifier: Modifier = Modifier, color: Color = Neutral500) {
    Text(text.uppercase(), style = jakarta(11, FontWeight.SemiBold, 20, 0.55), color = color, modifier = modifier)
}

/** Thin horizontal divider. */
@Composable
fun HDivider(color: Color = com.example.kpiwonosari.ui.theme.Neutral200, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(1.dp).background(color))
}

/** Small chevron used at the end of list rows. */
@Composable
fun RowChevron(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.ic_chevron_right),
        contentDescription = null,
        modifier = modifier.size(width = 6.5.dp, height = 11.5.dp),
    )
}

/** Neutral pale input-like container. */
val InputBg = Neutral50
val InputBorder = Neutral300
val Placeholder = Neutral400
val MutedBg = Neutral100
