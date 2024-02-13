package tugasbesar;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

record Kamar(String jenis, int nomor, boolean isOccupied, String harga) {}

record RiwayatPemesanan(String nama, String nomorHP, String jenisKamar, int nomorKamar) {
    static Kamar[] kamarArray; // Menyimpan array kamar

    Kamar kamar() {
        for (Kamar kamar : kamarArray) {
            if (kamar.nomor() == nomorKamar) {
                return kamar;
            }
        }
        return null;
    }
}

record Tamu(String nama, String nomorHP, Kamar kamar) {}

public class Kelompok7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RiwayatPemesanan.kamarArray = new Kamar[]{
                new Kamar("Suite", 101, false,"66"),
                new Kamar("Deluxe", 102, false,"55"),
                new Kamar("Standard", 103, false,"44"),
                new Kamar("Economy", 104, false,"33"),
                new Kamar("Basic", 105, false,"22")
        };

        LinkedList<RiwayatPemesanan> riwayatPemesananList = new LinkedList<>();
        Queue<Tamu> antrianReservasi = new LinkedList<>();

        int totalAkhir;
        int pilihan = -1;
        while (pilihan != 0) {
            System.out.println("\n=== Aplikasi Hotel ===");
            System.out.println("1. Cek Ketersediaan Kamar");
            System.out.println("2. Pesan Kamar");
            System.out.println("3. Lihat Antrian Pemesanan");
            System.out.println("4. Countdown");
            System.out.println("5. Riwayat Transaksi");
            System.out.println("6. Cari kamar");
            System.out.println("0. Keluar");
            System.out.print("Masukkan pilihan Anda: ");
            pilihan = scanner.nextInt();

            if (pilihan == 1) {
                System.out.println("\n=== Ketersediaan Kamar ===");
                for (Kamar kamar : RiwayatPemesanan.kamarArray) {
                    System.out.println("Kamar " + kamar.nomor() + ": " + (kamar.isOccupied() ? "Terisi" : "Kosong")+" --"+" Jenis Kamar :"+ kamar.jenis()+" --"+" Harga Kamar :"+ kamar.harga());
                }

            } else if (pilihan == 2) {
                scanner.nextLine();
                            System.out.println("Masukkan Data Diri");
                            String namaPemesan;
                            do {
                                System.out.print("Masukkan Nama : ");
                                namaPemesan = scanner.nextLine().trim();
                                if (!namaPemesan.matches("[a-zA-Z]+")) {
                                    System.out.println("\033[31mNama hanya boleh mengandung huruf. Masukkan Nama yang valid.\033[0m");
                                }
                            } while (!namaPemesan.matches("[a-zA-Z]+"));

                            String NoHp;
                            boolean phoneNumberExists = false;
                            do {
                                System.out.print("Masukkan Nomor Handphone : ");
                                NoHp = scanner.nextLine().trim();
                                if (!NoHp.matches("\\d+")) {
                                    System.out.println("\033[31mNomor Handphone hanya boleh mengandung angka. Masukkan Nomor Handphone yang valid.\033[0m");
                                } else {
                                    final String phoneNumber = NoHp;
                                    phoneNumberExists = riwayatPemesananList.stream().anyMatch(anggota -> anggota.nomorHP().equals(phoneNumber));
                                    if (phoneNumberExists) {
                                        System.out.println("\033[31mNomor Handphone sudah terdaftar. Masukkan Nomor Handphone yang berbeda.\033[0m");
                                    }
                                }
                            } while (!NoHp.matches("\\d+") || phoneNumberExists);
                            
                        System.out.println("\n=== Ketersediaan Kamar ===");
                        for (Kamar kamar : RiwayatPemesanan.kamarArray) {
                        System.out.println("Kamar " + kamar.nomor() + ": " + (kamar.isOccupied() ? "Terisi" : "Kosong") + " -- Jenis Kamar : " + kamar.jenis() + " -- Harga Kamar : " + kamar.harga());}
                        
                System.out.print("Masukkan nomor kamar yang ingin dipesan (1-5): ");
                int nomorKamar = scanner.nextInt();
                scanner.nextLine();

                if (nomorKamar >= 1 && nomorKamar <= 5) {
                    Kamar kamar = RiwayatPemesanan.kamarArray[nomorKamar - 1];
                    if (!kamar.isOccupied()) {
                        RiwayatPemesanan.kamarArray[nomorKamar - 1] = new Kamar(kamar.jenis(), kamar.nomor(), true, kamar.harga());
                        Tamu tamu = new Tamu(namaPemesan, NoHp, kamar);
                        System.out.println("Selamat, " + tamu.nama() + "! Kamar " + tamu.kamar().nomor() + " telah dipesan.");
                        riwayatPemesananList.add(new RiwayatPemesanan(tamu.nama(), tamu.nomorHP(), kamar.jenis(), kamar.nomor()));
                        int hargaKamar = Integer.parseInt(kamar.harga());
                        totalAkhir = hargaKamar;
                        System.out.println("\n\033[32mTotal yang harus dibayar : Rp." + totalAkhir + "\033[0m");
                        System.out.print("Masukkan jumlah uang: ");
                        int jumlahUang = scanner.nextInt();

                        while (jumlahUang < totalAkhir) {
                            System.out.println("\033[31mJumlah uang tidak mencukupi. Masukkan jumlah uang yang cukup.\033[0m");
                            System.out.print("Masukkan jumlah uang: ");
                            jumlahUang = scanner.nextInt();
                        }
                        int kembalian = jumlahUang - totalAkhir;
                        
                        System.out.println("\n=== Struk Pembayaran ===");
                        System.out.println("Nama: " + tamu.nama());
                        System.out.println("Nomor HP: " + tamu.nomorHP());
                        System.out.println("Nomor Kamar: " + tamu.kamar().nomor());
                        System.out.println("Jenis Kamar: " + tamu.kamar().jenis());
                        System.out.println("Harga Kamar: " + tamu.kamar().harga());
                        System.out.println("Total Pembayaran: " + totalAkhir);
                        System.out.println("Jumlah Uang: " + jumlahUang);
                        System.out.println("Kembalian: " + kembalian);
                        System.out.println("Terima kasih telah menginap di hotel kami!");
                        
                    } else {
                        System.out.println("Maaf, kamar " + kamar.nomor() + " sudah terisi.");
                        boolean jawabanValid = false;
                        while (!jawabanValid) {
                            System.out.print("Apakah Anda ingin dimasukkan ke dalam antrian? (ya/tidak): ");
                            String jawaban = scanner.nextLine();
                            if (jawaban.equalsIgnoreCase("ya")) {
                                antrianReservasi.offer(new Tamu(namaPemesan, NoHp, kamar));
                                System.out.println("Anda telah dimasukkan ke dalam antrian pemesanan.");
                                jawabanValid = true;
                            } else if (jawaban.equalsIgnoreCase("tidak")) {
                                System.out.println("Silakan pilih kamar lain yang masih kosong.");
                                jawabanValid = true;
                            } else {
                                System.out.println("Masukan tidak valid. Silakan pilih 'ya' atau 'tidak'.");
                            }
                        }
                    }
                } else {
                    System.out.println("Nomor kamar tidak valid.");
                }

            } else if (pilihan == 3) {
                System.out.println("\n=== Antrian Pemesanan ===");
                if (antrianReservasi.isEmpty()) {
                    System.out.println("Antrian kosong.");
                } else {
                    for (Tamu tamu : antrianReservasi) {
                        System.out.println(tamu.nama() + " (Nomor Kamar " + tamu.kamar().nomor() + ")"+"Nomor HP : "+tamu.nomorHP());
                    }
                }
                
            } else if (pilihan == 4) {
                // Countdown
                System.out.println("\nCountdown dimulai...");
                for (int i = 5; i >= 0; i--) {
                    System.out.println("Sisa waktu: " + i + " detik");
                    try {
                        Thread.sleep(1000);  // Menunggu 1 detik (1000 milidetik)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Countdown selesai. Pengosongan kamar dimulai...");
                // Mengosongkan semua kamar setelah countdown selesai
                for (int j = 0; j < RiwayatPemesanan.kamarArray.length; j++) {
                    RiwayatPemesanan.kamarArray[j] = new Kamar(RiwayatPemesanan.kamarArray[j].jenis(), RiwayatPemesanan.kamarArray[j].nomor(), false,RiwayatPemesanan.kamarArray[j].harga());
                }
                System.out.println("Semua kamar telah dikosongkan.");
                // Memasukkan tamu dari antrian ke kamar yang sesuai
                while (!antrianReservasi.isEmpty()) {
                    Tamu tamuBerikutnya = antrianReservasi.poll();
                    int nomorKamar = -1;
                    // Mencari kamar kosong dengan jenis yang sesuai
                    for (int k = 0; k < RiwayatPemesanan.kamarArray.length; k++) {
                        if (!RiwayatPemesanan.kamarArray[k].isOccupied() && RiwayatPemesanan.kamarArray[k].jenis().equals(tamuBerikutnya.kamar().jenis())) {
                            nomorKamar = k;
                            break;
                        }
                    }
                    if (nomorKamar != -1) {
                        RiwayatPemesanan.kamarArray[nomorKamar] = new Kamar(RiwayatPemesanan.kamarArray[nomorKamar].jenis(), RiwayatPemesanan.kamarArray[nomorKamar].nomor(), true,RiwayatPemesanan.kamarArray[nomorKamar].harga());
                        System.out.println("Selamat, " + tamuBerikutnya.nama() + "! Kamar " + RiwayatPemesanan.kamarArray[nomorKamar].nomor() + " telah dipesan.");
                        
            
                        System.out.println("\n=== Struk Pembayaran ===");
                        System.out.println("Nama: " + tamuBerikutnya.nama());
                        System.out.println("Nomor HP: " + tamuBerikutnya.nomorHP());
                        System.out.println("Nomor Kamar: " + tamuBerikutnya.kamar().nomor());
                        System.out.println("Jenis Kamar: " + tamuBerikutnya.kamar().jenis());
                        System.out.println("Harga Kamar: " + tamuBerikutnya.kamar().harga());
                        double totalHarga = Double.parseDouble(tamuBerikutnya.kamar().harga());
                        System.out.println("Total Pembayaran: " + totalHarga);
                        System.out.println("Terima kasih telah menginap di hotel kami!");
                    } else {
                        System.out.println("Maaf, tidak ada kamar kosong yang sesuai dengan preferensi tamu " + tamuBerikutnya.nama());
                    }
                }
                System.out.println("Pengisian kamar dari antrian selesai.");
                
            } else if (pilihan == 5) {
                // Riwayat Transaksi
                if (!riwayatPemesananList.isEmpty()) {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);
                    System.out.println("Riwayat Transaksi");
                    String leftAlignFormat = "| %-15s | %-13s | %-13s | %-11d | %-20s%n";
                    System.out.format("+-----------------+---------------+---------------+-------------+-----------------------+%n");
                    System.out.format("|       Name      |     Nomor HP  |  Jenis Kamar  | Nomor Kamar |     Tanggal Checkin   |%n");
                    System.out.format("+-----------------+---------------+---------------+-------------+-----------------------+%n");
                    for (RiwayatPemesanan riwayat : riwayatPemesananList) {
                        System.out.format(leftAlignFormat, riwayat.nama(), riwayat.nomorHP(), riwayat.jenisKamar(), riwayat.nomorKamar(), formattedDate);
                    }
                    System.out.format("+-----------------+---------------+---------------+-------------+-----------------------+%n");
                } else {
                    System.out.println("Belum ada riwayat transaksi");
                }
                
            } else if (pilihan == 6) {
                // Cari Kamar
                System.out.print("Masukkan harga kamar: ");
                String MaxHarga = scanner.next();
                System.out.println("\n=== Ketersediaan Kamar Dengan Harga Di Bawah " + MaxHarga + " ===");
                boolean found = false;
                for (Kamar kamar : RiwayatPemesanan.kamarArray) {
                    if (Double.parseDouble(kamar.harga()) <= Double.parseDouble(MaxHarga)) {
                        found = true;
                        System.out.println("Kamar " + kamar.nomor() + ": " + (kamar.isOccupied() ? "Terisi" : "Kosong") + " -- Jenis Kamar: " + kamar.jenis() + " -- Harga Kamar: " + kamar.harga());
                    }
                }
                if (!found) {
                    System.out.println("Maaf, tidak ada kamar dengan harga di bawah " + MaxHarga);
                }
            } else if (pilihan == 0) {
                System.out.println("Terima kasih, program berakhir.");
            }
        }
    }
}
