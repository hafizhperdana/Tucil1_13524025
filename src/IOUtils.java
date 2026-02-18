import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static Board readBoard(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("File tidak ditemukan: " + filename);
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }

        if (lines.isEmpty()) {
            throw new IOException("File kosong!");
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        if (rows != cols) {
            System.out.println("Peringatan: Input tidak persegi (" + rows + "x" + cols + ").");
        }

        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String rowStr = lines.get(i);
            if (rowStr.length() != cols) {
                throw new IOException("Panjang baris ke-" + (i + 1) + " tidak konsisten.");
            }
            grid[i] = rowStr.toCharArray();
        }

        return new Board(grid);
    }

    public static void saveSolution(String filename, Board board, long timeMs, long iterations) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(board.toString());
            writer.write("\nUkuran board: " + board.getSize() + "x" + board.getSize() + "\n");
            writer.write("Waktu pencarian: " + timeMs + " ms\n");
            writer.write("Jumlah iterasi: " + iterations + " kasus\n");
            System.out.println("Solusi berhasil disimpan di: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi: " + e.getMessage());
        }
    }
}