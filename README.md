# Tugas Kecil 1 Strategi Algoritma

## LinkedIn Queens Problem
Nama: Moh. Hafizh irham Perdana

NIM: 13524025

## Deskripsi Singkat
Program ini adalah aplikasi berbasis Java untuk menyelesaikan **Linkedin Queens Problem** menggunakan algoritma **Brute Force** yang dapat menjamin solusi untuk setiap permasalahan. Program dilengkapi dengan **Graphical User Interface (GUI)** yang interaktif untuk memvisualisasikan proses pencarian solusi langkah demi langkah.

**Fitur Utama:**
* **Visualisasi Backtracking:** Pengguna dapat melihat bagaimana algoritma mencoba menempatkan ratu dan melakukan *backtrack* secara *real-time*.
* **Kendala Kompleks:**
    1.  **Baris & Kolom:** Tidak boleh ada dua ratu di baris atau kolom yang sama.
    2.  **Wilayah Warna:** Papan dibagi menjadi wilayah warna (karakter input). Tidak boleh ada dua ratu di wilayah warna yang sama.
    3.  **Touch Constraint:** Ratu tidak boleh bersentuhan dengan ratu lain, baik secara vertikal, horizontal, maupun diagonal (jarak 1 petak).
* **Kontrol Kecepatan:** Slider untuk mengatur kecepatan board update.
* **Load & Save:** Kemampuan membaca konfigurasi board dari file `.txt` dan menyimpan solusi ke file eksternal.

## Requirements (Prasyarat)
Untuk menjalankan program ini, pastikan komputer telah terinstal:
* **Java Development Kit (JDK)** versi 8 atau yang lebih baru.
* Sistem Operasi: Windows, macOS, atau Linux.

## Cara Mengkompilasi
Pastikan Anda berada di direktori/folder yang sama dengan file kode sumber (`.java`). Buka terminal atau command prompt, lalu jalankan perintah berikut:

```bash
## Cara Mengkompilasi
javac -d bin src/*.java

## Cara Menjalankan
java -cp bin Main


