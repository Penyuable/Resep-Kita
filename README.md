<div align="center">
  <h1>🍳 ResepKita.id - Android Recipe App</h1>
  <p>Aplikasi Android modern untuk menemukan, menyimpan, dan berbagi resep masakan favorit Anda!</p>
</div>

<br />

## 📱 Tentang Proyek
**ResepKu** adalah aplikasi native Android yang dirancang dengan antarmuka yang bersih dan modern untuk memberikan pengalaman terbaik dalam menjelajahi berbagai resep kuliner. Aplikasi ini mengkonsumsi REST API yang handal yang dibangun menggunakan Laravel, memberikan data secara real-time dan cepat.

## ✨ Fitur Utama
- 🔍 **Pencarian Cerdas**: Cari resep masakan dengan cepat berdasarkan nama, kategori, atau bahan utama.
- 📖 **Detail Resep yang Jelas**: Menampilkan instruksi memasak langkah demi langkah dan daftar bahan yang tertata rapi.
- ❤️ **Favoritkan Resep**: Simpan resep favorit Anda secara lokal/cloud untuk diakses kembali kapan saja.
- 🌗 **Dark/Light Mode**: Mendukung tema gelap dan terang yang elegan menyesuaikan dengan preferensi sistem Anda.

## 🛠️ Tech Stack
Aplikasi ini dikembangkan menggunakan teknologi dan arsitektur Android modern terbaik saat ini:

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white" alt="Laravel Backend" />
</div>

<br />

## 🚀 Cara Instalasi (Clone & Run)

Ikuti langkah-langkah di bawah ini untuk menjalankan aplikasi ini di komputer / environment lokal Anda:

### Prasyarat
- [Android Studio](https://developer.android.com/studio) (versi Ladybug atau yang terbaru direkomendasikan)
- Git terinstal di komputer Anda
- Server Backend Laravel sudah berjalan (Jika Anda juga mengelola backend-nya)

### Langkah-langkah

1. **Clone Repository ini:**
   Buka terminal/command prompt dan jalankan perintah berikut:
   ```bash
   git clone https://github.com/username-anda/repo-resep-android.git
   ```

2. **Buka Proyek di Android Studio:**
   - Jalankan Android Studio.
   - Pilih menu **File** > **Open...**
   - Cari dan pilih folder hasil clone (`repo-resep-android`).

3. **Sinkronisasi Gradle (Gradle Sync):**
   - Tunggu beberapa saat hingga Android Studio selesai mengunduh semua *dependencies* dan menyelesaikan proses *Gradle Build*.

4. **Konfigurasi URL API Backend:**
   - Buka file konfigurasi API Anda (biasanya di `Constants.kt`, `build.gradle`, atau `local.properties`).
   - Masukkan Base URL backend Laravel Anda. 
   - *Catatan: Jika Anda menjalankan Laravel di localhost dan menggunakan Emulator Android, gunakan IP `http://10.0.2.2:8000` (bukan `localhost` atau `127.0.0.1`).*

5. **Jalankan Aplikasi:**
   - Pilih *device* (Emulator atau perangkat fisik) di toolbar Android Studio.
   - Klik tombol **Run** (▶️ hijau) atau gunakan *shortcut* `Shift + F10`.

## 🤝 Cara Berkontribusi
Kontribusi dari komunitas sangat terbuka! Jika Anda memiliki ide, menemukan *bug*, atau ingin menambahkan fitur baru, silakan lakukan langkah berikut:

1. Lakukan *Fork* pada repository ini.
2. Buat branch untuk fitur Anda (`git checkout -b feature/FiturBaruKeren`).
3. Lakukan *Commit* pada perubahan Anda (`git commit -m 'Menambahkan Fitur Baru Keren'`).
4. *Push* ke branch tersebut (`git push origin feature/FiturBaruKeren`).
5. Buat **Pull Request**.

## 📄 Lisensi
Proyek ini didistribusikan di bawah **Lisensi MIT**. Lihat file `LICENSE` untuk informasi lebih lanjut.

---
<div align="center">
  <i>Dibuat dengan ❤️ oleh [Nama/Username Anda]</i>
</div>
