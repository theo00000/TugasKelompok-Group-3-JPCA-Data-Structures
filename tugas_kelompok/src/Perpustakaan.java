package tugas_kelompok;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Class Buku
class Buku {
    private String judul;
    private String jenisBuku;
    private String penulis;
    private int tahunTerbit;
    private boolean tersedia;

    public Buku(String judul, String jenisBuku, String penulis, int tahunTerbit) {
        this.judul = judul;
        this.jenisBuku = jenisBuku;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.tersedia = true;
    }

    public void infoBuku() {
        System.out.println("Judul: " + judul);
        System.out.println("Jenis: " + jenisBuku);
        System.out.println("Penulis: " + penulis);
        System.out.println("Tahun Terbit: " + tahunTerbit);
        System.out.println("Status: " + (tersedia ? "Tersedia" : "Dipinjam"));
    }

    public String getJudul() {
        return judul;
    }

    public String getJenisBuku() {
        return jenisBuku;
    }

    public String getPenulis() {
        return penulis;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean status) {
        tersedia = status;
    }
}

// Abstract Class User
abstract class User {
    protected String id;
    protected String name;
    protected String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void login() {
        System.out.println("User " + name + " logged in");
    }

    public void logout() {
        System.out.println("User " + name + " logged out");
    }

    abstract void interaksiSistem();
}

// Class Admin
class Admin extends User {
    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    void interaksiSistem() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Admin:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Lihat Buku");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    tambahBuku(scanner);
                    break;
                case 2:
                    hapusBuku(scanner);
                    break;
                case 3:
                    tampildaftarBuku();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahBuku(Scanner scanner) {
        System.out.print("Judul: ");
        String judul = scanner.nextLine();
        System.out.print("Jenis: ");
        String jenis = scanner.nextLine();
        System.out.print("Penulis: ");
        String penulis = scanner.nextLine();
        System.out.print("Tahun Terbit: ");
        int tahunTerbit = scanner.nextInt();
        scanner.nextLine();

        try {
            Perpustakaan.daftarBuku.add(new Buku(judul, jenis, penulis, tahunTerbit));
            Perpustakaan.simpanBukuKeFile();
        } catch (Exception e) {
            System.out.println("Buku berhasil ditambahkan!");
        }
    }

    private void hapusBuku(Scanner scanner) {
        System.out.print("Masukkan judul buku: ");
        String judul = scanner.nextLine();

        boolean found = false;
        Iterator<Buku> iterator = Perpustakaan.daftarBuku.iterator();
        while (iterator.hasNext()) {
            Buku buku = iterator.next();
            if (buku.getJudul().equalsIgnoreCase(judul)) {
                iterator.remove(); // Hapus dengan aman
                found = true;
                break;
            }
        }

            if (found) {
                Perpustakaan.simpanBukuKeFile();
                System.out.println("Buku berhasil dihapus!");
            } else {
                System.out.println("Buku tidak ditemukan!");
            }
    }

    private void tampildaftarBuku() {
        if (Perpustakaan.daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku yang tersedia.");
            return;
        }

        System.out.println("\nList Buku:");
        for (Buku buku : Perpustakaan.daftarBuku) {
            buku.infoBuku();
            System.out.println();
        }
    }
}

// Class Member
class Member extends User {
    public Member(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    void interaksiSistem() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Member:");
            System.out.println("1. Lihat Buku");
            System.out.println("2. Pinjam Buku");
            System.out.println("3. Kembalikan Buku");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    tampildaftarBuku();
                    break;
                case 2:
                    pinjamBuku(scanner);
                    break;
                case 3:
                    kembalikanBuku(scanner);
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tampildaftarBuku() {
        if (Perpustakaan.daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku yang tersedia.");
            return;
        }

        System.out.println("\nList Buku:");
        for (Buku buku : Perpustakaan.daftarBuku) {
            buku.infoBuku();
            System.out.println();
        }
    }

    private void pinjamBuku(Scanner scanner) {
        System.out.print("Masukkan judul buku: ");
        String judul = scanner.nextLine();

        for (Buku buku : Perpustakaan.daftarBuku) {
            if (buku.getJudul().equalsIgnoreCase(judul) && buku.isTersedia()) {
                buku.setTersedia(false);
                Perpustakaan.simpanBukuKeFile();
                System.out.println("Buku berhasil dipinjam!");
                return;
            }
        }
        System.out.println("Buku tidak tersedia atau tidak ditemukan.");
    }

    private void kembalikanBuku(Scanner scanner) {
        System.out.print("Masukkan judul buku yang dikembalikan: ");
        String judul = scanner.nextLine();

        for (Buku buku : Perpustakaan.daftarBuku) {
            if (buku.getJudul().equalsIgnoreCase(judul) && !buku.isTersedia()) {
                buku.setTersedia(true);
                Perpustakaan.simpanBukuKeFile();
                System.out.println("Buku berhasil dikembalikan!");
                return;
            }
        }
        System.out.println("Buku tidak ditemukan atau sudah tersedia.");
    }
}

// Main Class
public class Perpustakaan {
    public static ArrayList<Buku> daftarBuku = new ArrayList<>();
    private static final String FILE_PATH = "daftar_buku.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        loadBukuDariFile();

        if (daftarBuku.isEmpty()) {
            defaultBuku();
            simpanBukuKeFile();
        }

        System.out.println("\nSistem Perpustakaan\n");

        System.out.println("1. Login Admin");
        System.out.println("2. Login Member");
        System.out.print("Pilih: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        User user = (choice == 1) ? new Admin("admin1", "Admin Utama", "admin@perpus.com")
                                  : new Member("member1", "Satria", "member@gmail.com");
                                  
        user.login();
        user.interaksiSistem();
        scanner.close();
    }

    private static void defaultBuku() {
    }

    public static void simpanBukuKeFile() {
        File file = new File(FILE_PATH);
        File backupFile = new File(FILE_PATH + ".bak");

        try {
            if (file.exists()){
                if (!file.renameTo(backupFile)) {
                    System.out.println("Gagal membuat backup!");
                    return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Buku buku : daftarBuku) {
                writer.write(buku.getJudul() + ";" + buku.getJenisBuku() + ";" +
                             buku.getPenulis() + ";" + buku.getTahunTerbit() + ";" +
                             buku.isTersedia());
                writer.newLine();
            }
        }

        backupFile.delete();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data buku!");
            if (backupFile.exists()) {
                backupFile.renameTo(file);
            }
        }
    }

    public static void loadBukuDariFile() {
        daftarBuku.clear();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return; // Jika file belum ada, langsung return
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 5) {
                    String judul = data[0];
                    String jenis = data[1];
                    String penulis = data[2];
                    int tahunTerbit = Integer.parseInt(data[3]);
                    boolean tersedia = Boolean.parseBoolean(data[4]);

                    Buku buku = new Buku(judul, jenis, penulis, tahunTerbit);
                    buku.setTersedia(tersedia);
                    daftarBuku.add(buku);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data buku!");
        }
    }
    
}
