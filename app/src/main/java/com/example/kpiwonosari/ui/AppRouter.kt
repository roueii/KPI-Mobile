package com.example.kpiwonosari.ui

import androidx.compose.runtime.Composable
import com.example.kpiwonosari.ui.screens.employee.ApprovalTimScreen
import com.example.kpiwonosari.ui.screens.employee.DashboardTimScreen
import com.example.kpiwonosari.ui.screens.employee.DetailRiwayatScreen
import com.example.kpiwonosari.ui.screens.employee.DetailTrainingScreen
import com.example.kpiwonosari.ui.screens.employee.EmployeeDashboardScreen
import com.example.kpiwonosari.ui.screens.employee.RiwayatScreen
import com.example.kpiwonosari.ui.screens.input.InputWizardScreen
import com.example.kpiwonosari.navigation.Navigator
import com.example.kpiwonosari.navigation.Role
import com.example.kpiwonosari.navigation.Screen
import com.example.kpiwonosari.ui.screens.admin.AdminDashboardScreen
import com.example.kpiwonosari.ui.screens.admin.ApproveLaporanScreen
import com.example.kpiwonosari.ui.screens.admin.DetailRingkasanScreen
import com.example.kpiwonosari.ui.screens.admin.KelolaAkunScreen
import com.example.kpiwonosari.ui.screens.admin.KelolaKpiScreen
import com.example.kpiwonosari.ui.screens.admin.RekapBulananScreen
import com.example.kpiwonosari.ui.screens.admin.RincianHarianScreen
import com.example.kpiwonosari.ui.screens.admin.TinjauRevisiScreen
import com.example.kpiwonosari.ui.screens.login.LoginScreen
import com.example.kpiwonosari.ui.screens.login.SplashScreen
import com.example.kpiwonosari.ui.screens.shared.ProfilScreen

/** Maps the current [Screen] (and the logged-in role) to its composable. */
@Composable
fun AppRouter(screen: Screen, nav: Navigator, onMenu: () -> Unit) {
    val back: () -> Unit = { nav.back() }
    val logout: () -> Unit = { nav.logout() }
    when (screen) {
        Screen.Splash -> SplashScreen(onContinue = { nav.push(Screen.Login) })
        Screen.Login -> LoginScreen(onLogin = { nav.login(it) })
        Screen.Dashboard -> when (nav.role) {
            Role.ADMIN -> AdminDashboardScreen(onMenu, nav::push, logout)
            else -> EmployeeDashboardScreen(
                nav.role,
                onMenu,
                onStartInput = { nav.push(Screen.InputCapaian) },
                onOpenHistory = { nav.push(Screen.DetailRiwayat(it)) },
                onOpenTeam = { nav.push(Screen.DashboardTim) },
                onLogout = logout,
            )
        }
        Screen.Profil -> ProfilScreen(nav.role, back, onMenu, logout)
        Screen.InputCapaian -> InputWizardScreen(
            nav.role,
            onClose = back,
            onSubmitted = { nav.goTop(Screen.Dashboard) },
            onOpenTraining = { nav.push(Screen.DetailTraining) },
        )
        Screen.Riwayat -> RiwayatScreen(back) { nav.push(Screen.DetailRiwayat(it)) }
        is Screen.DetailRiwayat -> DetailRiwayatScreen(nav.role, screen.index, back) { nav.push(Screen.InputCapaian) }

        // Store manager
        Screen.DashboardTim -> DashboardTimScreen(onMenu, onMyKpi = back, onApproval = { nav.push(Screen.ApprovalTim) }, onLogout = logout)
        Screen.ApprovalTim -> ApprovalTimScreen(back)
        Screen.DetailTraining -> DetailTrainingScreen(back)

        // Admin
        Screen.TinjauRevisi -> TinjauRevisiScreen(back)
        Screen.KelolaKpi -> KelolaKpiScreen(back)
        Screen.KelolaAkun -> KelolaAkunScreen(back)
        Screen.ApproveLaporan -> ApproveLaporanScreen(back)
        Screen.DetailRingkasanDivisi -> DetailRingkasanScreen(back) { nav.push(Screen.RekapKaryawan) }
        Screen.RekapKaryawan -> RekapBulananScreen(0, back, { nav.push(Screen.RincianHarian) }, logout)
        Screen.RekapBagian -> RekapBulananScreen(1, back, { nav.push(Screen.RincianHarian) }, logout)
        Screen.RincianHarian -> RincianHarianScreen(back, logout)
    }
}
