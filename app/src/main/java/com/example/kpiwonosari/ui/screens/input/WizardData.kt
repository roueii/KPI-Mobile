package com.example.kpiwonosari.ui.screens.input

import com.example.kpiwonosari.navigation.Role

data class Indicator(val title: String, val description: String, val opensTraining: Boolean = false)

data class IndicatorGroup(val label: String?, val items: List<Indicator>)

data class WizardStep(
    val title: String,
    val subtitle: String,
    val groups: List<IndicatorGroup>,
    val requiresPhoto: Boolean = false,
    /** "INDIKATOR PENILAIAN" + WAJIB badge style used by Produksi/SM grouped steps. */
    val mandatoryStyle: Boolean = false,
    val mandatoryNote: String = "Centang indikator yang dipenuhi hari ini sesuai standar operasional dapur produksi.",
)

private fun g(label: String?, vararg items: Pair<String, String>) =
    IndicatorGroup(label, items.map { Indicator(it.first, it.second) })

private const val SHIFT_SUB = "Input total perolehan transaksi kasir shift hari ini."
private const val CHECK_SUB = "Centang indikator yang tercapai hari ini"

val frontlinerSteps = listOf(
    WizardStep(
        "Penjualan", SHIFT_SUB,
        listOf(
            g(
                null,
                "Mencapai target omzet harian/bulanan" to "Omset toko hari ini sudah mencapai atau melampaui target yang ditetapkan",
                "Meningkatkan nilai rata-rata belanja pelanggan" to "Nilai transaksi per pelanggan hari ini lebih tinggi dari rata-rata bulan lalu",
                "Aktif menawarkan produk promo" to "Kamu sudah menawarkan produk promo kepada pelanggan yang datang hari ini",
            ),
        ),
        requiresPhoto = true,
    ),
    WizardStep(
        "Pelayanan", SHIFT_SUB,
        listOf(
            g(
                null,
                "Menyapa pelanggan" to "Setiap pelanggan yang masuk disambut dengan salam dan senyum",
                "Ramah dan komunikatif" to "Melayani dengan sikap positif dan komunikasi yang jelas sepanjang shift",
                "Cepat melayani" to "Waktu tunggu pelanggan tidak terlalu lama dan antrian tidak menumpuk",
                "Menangani komplain dengan baik" to "Setiap komplain pelanggan ditanggapi dengan tenang dan diselesaikan hari ini",
            ),
        ),
    ),
    WizardStep(
        "Product Knowledge", SHIFT_SUB,
        listOf(
            g(
                null,
                "Menguasai fungsi bahan kue" to "Bisa menjelaskan bahan-bahan yang digunakan dalam produk roti kepada pelanggan",
                "Mengetahui stok dan lokasi produk" to "Tahu produk apa yang tersedia hari ini dan di mana letaknya di toko",
                "Mampu memberi rekomendasi kepada pelanggan" to "Aktif menyarankan produk yang sesuai kebutuhan atau selera pelanggan",
            ),
        ),
    ),
    WizardStep(
        "Akurasi Kasir", SHIFT_SUB,
        listOf(
            g(
                null,
                "Tidak ada selisih kas" to "Jumlah uang fisik di laci kasir sesuai dengan total transaksi tercatat",
                "Tidak salah input harga" to "Semua harga produk diinput sesuai harga yang berlaku, tidak ada koreksi transaksi",
                "Nota sesuai transaksi" to "Struk atau nota yang diberikan ke pelanggan sesuai dengan pesanan dan pembayaran",
            ),
        ),
    ),
    WizardStep(
        "Display & Kebersihan", SHIFT_SUB,
        listOf(
            g(
                null,
                "Rak selalu rapi" to "Produk di rak tertata rapi dan tidak ada yang jatuh atau berantakan sepanjang shift",
                "Produk FIFO" to "Produk yang lebih lama dipajang di depan, produk baru di belakang",
                "Label harga lengkap" to "Semua produk di rak sudah terpasang label harga yang jelas dan benar",
                "Area kerja bersih" to "Meja kasir, area display, dan sekitar tempat kerja bersih dari sampah dan kotoran",
            ),
        ),
        requiresPhoto = true,
    ),
    WizardStep(
        "Kepatuhan SOP & Disiplin", SHIFT_SUB,
        listOf(
            g(
                "Kepatuhan SOP",
                "Menggunakan seragam lengkap" to "Hadir dengan seragam lengkap sesuai ketentuan toko hari ini",
                "Mengikuti SOP buka/tutup toko" to "Semua prosedur pembukaan atau penutupan toko sudah dijalankan sesuai urutan",
                "Menjalankan prosedur pelayanan dan keamanan" to "Prosedur layanan pelanggan dan keamanan toko diikuti sepanjang shift",
            ),
            g(
                "Disiplin",
                "Tidak terlambat" to "Hadir di lokasi dan siap memproduksi roti tepat waktu sesuai jadwal shift hari ini",
                "Mematuhi Jadwal kerja" to "Semua langkah perawatan alat harian sudah dilakukan dan tercatat",
            ),
        ),
        mandatoryStyle = true,
    ),
)

val produksiSteps = listOf(
    WizardStep(
        "Hasil Produksi", CHECK_SUB,
        listOf(
            g("Produktivitas Produksi", "Target jumlah roti sesuai rencana harian" to "Jumlah roti yang selesai diproduksi hari ini sesuai atau melampaui rencana produksi harian"),
            g("Kualitas Produk", "Produk reject maksimal 2%" to "Roti gosong, bantat, atau tidak sesuai bentuk/ ukuran tidak melebihi 2% dari total produksi hari ini"),
            g("Waste Bahan Baku", "Penggunaan bahan baku terkontrol" to "Pemakaian tepung, mentega, telur, dan bahan lain hari ini tidak melebihi takaran yang direncanakan"),
        ),
        mandatoryStyle = true,
    ),
    WizardStep(
        "Waktu & Alat", CHECK_SUB,
        listOf(
            g(
                "Waktu Produksi",
                "Produk selesai sebelum jadwal pengiriman/display" to "Semua produk sudah siap sebelum waktu pengiriman atau jam buka toko",
                "Tidak menyebabkan keterlambatan buka toko" to "Bagian produksi bukan penyebab toko terlambat beroperasi hari ini",
            ),
            g(
                "Kerusakan Alat",
                "Tidak ada kerusakan akibat kelalaian operator" to "Semua alat dipakai sesuai prosedur dan tidak ada kerusakan karena kesalahan pemakaian",
                "Perawatan harian sesuai checklist" to "Semua langkah perawatan alat harian sudah dilakukan dan tercatat",
            ),
        ),
        requiresPhoto = true,
        mandatoryStyle = true,
    ),
    WizardStep(
        "Kehadiran & Disiplin", CHECK_SUB,
        listOf(
            g(
                "Kehadiran",
                "Tidak terlambat" to "Hadir di dapur dan siap memproduksi roti tepat waktu sesuai jadwal shift hari ini",
                "Mematuhi jadwal kerja" to "Mengikuti jadwal shift dan tidak meninggalkan area produksi tanpa izin",
            ),
            g(
                "Disiplin",
                "Menggunakan seragam & APD lengkap" to "Memakai apron, penutup kepala, dan sarung tangan selama proses produksi",
            ),
        ),
        mandatoryStyle = true,
    ),
    WizardStep(
        "Kepatuhan SOP", CHECK_SUB,
        listOf(
            g(
                "Kepatuhan SOP",
                "Menimbang sesuai resep" to "Semua bahan ditimbang sesuai takaran resep standar sebelum diolah",
                "Mengikuti waktu fermentasi" to "Adonan difermentasi sesuai waktu dan suhu yang ditetapkan",
                "Menggunakan APD" to "Alat pelindung diri dipakai sepanjang proses produksi",
                "Mencatat batch produksi" to "Setiap batch produksi hari ini tercatat lengkap di lembar produksi",
            ),
        ),
        mandatoryStyle = true,
    ),
    WizardStep(
        "Kebersihan Area Kerja", "Input Harian Divisi Produksi Roti",
        listOf(
            g(
                null,
                "Meja bersih" to "Meja kerja stainless steril, bebas sisa adonan tepung & remah",
                "Oven bersih" to "Kaca deck, loyang pembakar, dan kerak dalam ruang bakar bersih",
                "Mixer bersih" to "Bowl spiral mixer, hook pengaduk, dan bodi luar dicuci kering",
                "Lantai bersih" to "Disapu dan dipel dengan disinfektan dapur tidak licin/berminyak",
                "Rak bersih" to "Cooling rack & rak penyimpanan bahan tertata rapi tanpa debu",
                "Tempat sampah tertutup" to "Plastik sampah diganti baru, tutup pedal berfungsi rapat",
            ),
        ),
        requiresPhoto = true,
        mandatoryStyle = true,
        mandatoryNote = "Centang indikator yang dipenuhi hari ini sesuai checklist sanitasi & standar kebersihan dapur produksi.",
    ),
)

val storeManagerSteps = listOf(
    WizardStep(
        "Penjualan", CHECK_SUB,
        listOf(
            g(
                null,
                "Pencapaian target omset ≥100%" to "Total omset hari ini sudah mencapai atau melampaui target harian toko",
                "Pertumbuhan omset vs bulan lalu ≥5%" to "Tren penjualan minggu ini lebih tinggi dibanding periode yang sama bulan lalu",
                "Rata-rata belanja (Average Basket) naik ≥10%" to "Nilai transaksi per pelanggan hari ini lebih tinggi dari rata-rata bulan lalu",
            ),
        ),
    ),
    WizardStep(
        "Laba & Persediaan", CHECK_SUB,
        listOf(
            g(
                "Laba & Biaya",
                "Food Cost sesuai standar ≤ target perusahaan" to "Biaya bahan baku terhadap harga jual minggu ini tidak melebihi target yang ditetapkan perusahaan",
                "Waste/produk rusak & expired ≤1% dari omset" to "Nilai roti yang dibuang, rusak, atau kedaluwarsa hari ini tidak lebih dari 1% omset",
            ),
            g(
                "Persediaan",
                "Akurasi stok ≥98%" to "Selisih antara stok fisik di toko dan catatan sistem tidak lebih dari 2%",
                "Stock opname tepat waktu 100%" to "Penghitungan stok fisik hari ini sudah dilakukan sesuai jadwal yang ditetapkan",
            ),
        ),
        mandatoryStyle = true,
    ),
    WizardStep(
        "Pelayanan", CHECK_SUB,
        listOf(
            g(
                null,
                "Kepuasan pelanggan ≥90%" to "Rating toko hari ini berada di atas 90% berdasarkan penilaian dan feedback pelanggan",
                "Komplain terselesaikan 100% dalam 1×24 jam" to "Semua komplain yang masuk hari ini sudah ditangani dan diselesaikan sebelum 24 jam",
            ),
        ),
    ),
    WizardStep(
        "Operasional & SDM", CHECK_SUB,
        listOf(
            g(
                "Operasional",
                "Display produk lengkap saat jam operasional" to "Etalase roti terisi penuh & label harga terpasang rapi",
                "SOP buka toko dijalankan" to "Sanitasi, pencatatan kas modal awal, dan uji mesin POS",
                "SOP tutup toko dijalankan" to "Rekapitulasi sisa produk, kunci chiller, dan pemadaman lampu",
            ),
            IndicatorGroup(
                "SDM & Tim",
                listOf(
                    Indicator("Briefing harian sudah dilaksanakan", "Penyampaian target penjualan harian dan fokus produk promo"),
                    Indicator("Training karyawan Minimal 1 kali/bulan", "Pelatihan standar sanitasi, pencatatan kas, dan operasional sistem POS.", opensTraining = true),
                ),
            ),
        ),
        requiresPhoto = true,
        mandatoryStyle = true,
    ),
    WizardStep(
        "Promosi & Event", CHECK_SUB,
        listOf(
            g(
                null,
                "Ada promo terjadwal hari ini?" to "Contoh: Bundling Roti Kopi Sore / Diskon Akhir Pekan",
                "Promo dieksekusi sesuai jadwal" to "Materi POP terpasang, kasir menerapkan diskon & display siap",
            ),
        ),
        requiresPhoto = true,
    ),
)

fun stepsFor(role: Role): List<WizardStep> = when (role) {
    Role.STORE_MANAGER -> storeManagerSteps
    Role.PRODUKSI -> produksiSteps
    else -> frontlinerSteps
}
