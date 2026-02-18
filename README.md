# Tugas Kecil 1 Strategi Alagoritma

## LinkedIn Queens Problem

## Deskripsi Singkat
Program adalah aplikasi berbasis Java untuk menyelesaikan variasi persoalan **Linkedin Queens Problem** menggunakan algoritma **Brute Force**. Berbeda dengan N-Queens standar, program ini menangani kendala (constraints) tambahan yang lebih kompleks.

Program dilengkapi dengan **Graphical User Interface (GUI)** yang interaktif untuk memvisualisasikan proses pencarian solusi langkah demi langkah.

**Fitur Utama:**
* **Visualisasi Backtracking:** Pengguna dapat melihat bagaimana algoritma mencoba menempatkan ratu dan melakukan *backtrack* secara *real-time*.
* **Kendala Kompleks:**
    1.  **Baris & Kolom:** Tidak boleh ada dua ratu di baris atau kolom yang sama.
    2.  **Wilayah Warna:** Papan dibagi menjadi wilayah warna (karakter input). Tidak boleh ada dua ratu di wilayah warna yang sama.
    3.  **Touch Constraint:** Ratu tidak boleh bersentuhan dengan ratu lain, baik secara vertikal, horizontal, maupun diagonal (jarak 1 petak).
* **Kontrol Kecepatan:** Slider untuk mengatur kecepatan animasi pencarian.
* **Load & Save:** Kemampuan membaca konfigurasi papan dari file `.txt` dan menyimpan solusi ke file eksternal.

## Requirements (Prasyarat)
Untuk menjalankan program ini, pastikan komputer Anda telah terinstal:
* **Java Development Kit (JDK)** versi 8 atau yang lebih baru.
* Sistem Operasi: Windows, macOS, atau Linux.

## Cara Mengkompilasi
Pastikan Anda berada di direktori/folder yang sama dengan file kode sumber (`.java`). Buka terminal atau command prompt, lalu jalankan perintah berikut:

```bash
javac *.java

## Cara Menjalankan
java Main
