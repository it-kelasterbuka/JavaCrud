import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Crud{

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:E:/Documents/database_sqlite3/Solved2.db";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertData();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateData();
                    break;
                case 4:
                    deleteData();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM barang";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|      TOKO PERALATAN SEKOLAH    |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                String Kode = rs.getString("kode");
                String NamaBrg = rs.getString("nama_barang");
                int harga = rs.getInt("harga");

                System.out.println(String.format("%s \t| %s | (%d)", Kode, NamaBrg, harga));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertData() {
        try {
            // ambil input dari user
            System.out.print("Kode Barang: ");
            String kode = input.readLine().trim();
            System.out.print("Nama Barang: ");
            String namabrg = input.readLine().trim();
            System.out.print("Harga Barang: ");
            int harga = Integer.parseInt(input.readLine().trim());
 
            // query simpan
            String sql = "INSERT INTO barang (kode, nama_barang, harga)" + "VALUES('%s', '%s', '%d')";
            sql = String.format(sql, kode, namabrg, harga);

            // simpan buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateData() {
        try {
            
            // ambil input dari user
            System.out.print("Kode yang mau diedit: ");
            String kode = input.readLine().trim();
            System.out.print("Nama Barang: ");
            String namabrg = input.readLine().trim();
            System.out.print("Harga Barang: ");
            int harga = Integer.parseInt(input.readLine().trim());

            // query update
            String sql = "UPDATE barang SET nama_barang='%s', harga='%d' WHERE kode='%s'";
            sql = String.format(sql, namabrg, harga, kode);

            // update data buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteData() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            String kode = input.readLine();
            
            // buat query hapus
            String sql = String.format("DELETE FROM barang WHERE kode='%s'", kode);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
