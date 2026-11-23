package com.example.kpiwonosari.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/** The four account roles defined in the design. */
enum class Role(val label: String, val greetingName: String) {
    ADMIN("Admin", "Admin"),
    STORE_MANAGER("Store Manager", "User"),
    FRONTLINER("Frontliner", "User"),
    PRODUKSI("Produksi", "User"),
}

/** Every destination in the app. */
sealed interface Screen {
    data object Splash : Screen
    data object Login : Screen

    // Shared by every role
    data object Dashboard : Screen
    data object InputCapaian : Screen
    data object Profil : Screen
    data object Riwayat : Screen
    data class DetailRiwayat(val index: Int) : Screen

    // Admin
    data object TinjauRevisi : Screen
    data object KelolaAkun : Screen
    data object KelolaKpi : Screen
    data object ApproveLaporan : Screen
    data object DetailRingkasanDivisi : Screen
    data object RekapKaryawan : Screen
    data object RekapBagian : Screen
    data object RincianHarian : Screen

    // Store manager
    data object DashboardTim : Screen
    data object ApprovalTim : Screen
    data object DetailTraining : Screen
}

/** Tiny back-stack navigator; avoids pulling in a navigation library for a demo app. */
class Navigator {
    val stack = mutableStateListOf<Screen>(Screen.Splash)
    var role by mutableStateOf(Role.ADMIN)

    val current: Screen get() = stack.last()

    fun push(screen: Screen) {
        stack.add(screen)
    }

    /** Navigate to a top-level menu destination, resetting the stack to the dashboard. */
    fun goTop(screen: Screen) {
        stack.clear()
        stack.add(Screen.Dashboard)
        if (screen != Screen.Dashboard) stack.add(screen)
    }

    fun back(): Boolean {
        if (stack.size <= 1) return false
        stack.removeAt(stack.lastIndex)
        return true
    }

    fun login(role: Role) {
        this.role = role
        stack.clear()
        stack.add(Screen.Dashboard)
    }

    fun logout() {
        stack.clear()
        stack.add(Screen.Login)
    }
}
