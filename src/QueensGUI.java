import java.awt.*;
import java.io.File;
import java.util.Hashtable; 
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class QueensGUI extends JFrame {
    private BoardPanel boardPanel;
    private JLabel statusLabel;
    private JTextField fileInputDisplay; 
    private JButton loadButton, saveButton, solveButton;
    private JSlider speedSlider;
    private Board currentBoard;
    private Solver currentSolver;
    private Thread solverThread;

    private long lastTimeMs = 0;
    private long lastIterations = 0;
    private final int[] DELAY_OPTIONS = {1, 5, 10, 20, 50, 100};
    private final String DEFAULT_DIR = "test"; 

    public QueensGUI() {
        setTitle("N-Queens Solver & Saver");
        setSize(600, 780); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        File directory = new File(DEFAULT_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        JPanel mainControlPanel = new JPanel();
        mainControlPanel.setLayout(new BoxLayout(mainControlPanel, BoxLayout.Y_AXIS));
        mainControlPanel.setBorder(new EmptyBorder(15, 20, 15, 20)); 
        mainControlPanel.setBackground(Color.WHITE); 

        // LOAD & SAVE
        JPanel filePanel = new JPanel(new BorderLayout(10, 0));
        filePanel.setBackground(Color.WHITE);
        
        fileInputDisplay = new JTextField(" Belum ada file...");
        fileInputDisplay.setEditable(false);
        fileInputDisplay.setBackground(new Color(245, 245, 245)); 
        fileInputDisplay.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        fileInputDisplay.setPreferredSize(new Dimension(200, 35));
        
        JPanel fileButtons = new JPanel(new GridLayout(1, 2, 5, 0));
        fileButtons.setBackground(Color.WHITE);

        loadButton = new JButton("Buka");
        loadButton.setFocusPainted(false);
        loadButton.setBackground(new Color(230, 230, 230)); 
        
        saveButton = new JButton("Simpan");
        saveButton.setFocusPainted(false);
        saveButton.setBackground(new Color(230, 230, 230));
        saveButton.setEnabled(false); 

        fileButtons.add(loadButton);
        fileButtons.add(saveButton);
        
        filePanel.add(new JLabel("File: "), BorderLayout.WEST);
        filePanel.add(fileInputDisplay, BorderLayout.CENTER);
        filePanel.add(fileButtons, BorderLayout.EAST);

        // PANEL SLIDER & MULAI
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); 

        speedSlider = new JSlider(0, DELAY_OPTIONS.length - 1, 2);
        speedSlider.setBackground(Color.WHITE);
        speedSlider.setPreferredSize(new Dimension(200, 50)); 
        
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 0; i < DELAY_OPTIONS.length; i++) {
            JLabel lbl = new JLabel(String.valueOf(DELAY_OPTIONS[i]));
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
            labelTable.put(i, lbl);
        }
        
        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setSnapToTicks(true);
        speedSlider.setToolTipText("Kecepatan (ms)");

        JPanel sliderContainer = new JPanel(new BorderLayout());
        sliderContainer.setBackground(Color.WHITE);
        JLabel titleSpeed = new JLabel("Speed (ms)", SwingConstants.CENTER);
        titleSpeed.setFont(new Font("SansSerif", Font.BOLD, 11));
        sliderContainer.add(titleSpeed, BorderLayout.NORTH);
        sliderContainer.add(speedSlider, BorderLayout.CENTER);

        solveButton = new JButton("MULAI");
        solveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        solveButton.setBackground(new Color(33, 150, 243)); 
        solveButton.setForeground(Color.WHITE);
        solveButton.setFocusPainted(false);
        solveButton.setEnabled(false);
        solveButton.setPreferredSize(new Dimension(100, 45));

        actionPanel.add(sliderContainer);
        actionPanel.add(solveButton);

        //STATUS BAR
        statusLabel = new JLabel("Silakan buka file.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusLabel.setForeground(Color.GRAY);
        statusLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        mainControlPanel.add(filePanel);
        mainControlPanel.add(actionPanel);
        mainControlPanel.add(statusLabel);

        add(mainControlPanel, BorderLayout.SOUTH);

        //LISTENER SLIDER
        speedSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting() && currentSolver != null) {
                int index = source.getValue();
                int realDelay = DELAY_OPTIONS[index];
                currentSolver.setDelay(realDelay);
            }
        });

        // LISTENER LOAD
        loadButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser(new File(DEFAULT_DIR)); 
            
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try {
                    lastTimeMs = 0;
                    lastIterations = 0;

                    currentBoard = IOUtils.readBoard(f.getPath());
                    boardPanel.setBoard(currentBoard);
                    
                    fileInputDisplay.setText(" " + f.getName());
                    solveButton.setEnabled(true);
                    saveButton.setEnabled(false); 
                    
                    statusLabel.setText("File dimuat. Ukuran: " + currentBoard.getSize() + "x" + currentBoard.getSize());
                    
                    solveButton.setText("MULAI");
                    solveButton.setBackground(new Color(33, 150, 243)); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error membaca file: " + ex.getMessage());
                }
            }
        });

        // LISTENER SAVE
        saveButton.addActionListener(e -> {
            if (currentBoard == null) return;

            JFileChooser fc = new JFileChooser(new File(DEFAULT_DIR));
            fc.setDialogTitle("Simpan Solusi");
            
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                
                if (!f.getName().toLowerCase().endsWith(".txt")) {
                    f = new File(f.getParentFile(), f.getName() + ".txt");
                }

                IOUtils.saveSolution(f.getPath(), currentBoard, lastTimeMs, lastIterations);
                
                JOptionPane.showMessageDialog(this, "Solusi berhasil disimpan!\nFile: " + f.getName());
            }
        });

        // LISTENER SOLVE
        solveButton.addActionListener(e -> {
            if (currentBoard == null) return;
            
            loadButton.setEnabled(false);
            saveButton.setEnabled(false); 
            solveButton.setEnabled(false);
            solveButton.setBackground(Color.GRAY);
            statusLabel.setText("Sedang mencari solusi...");

            solverThread = new Thread(() -> {
                int initialDelay = DELAY_OPTIONS[speedSlider.getValue()];
                currentSolver = new Solver(initialDelay);
                
                currentSolver.setListener((b, i) -> SwingUtilities.invokeLater(() -> {
                    boardPanel.repaint();
                    statusLabel.setText("Iterasi ke-" + i);
                }));

                long start = System.currentTimeMillis();
                boolean found = currentSolver.solve(currentBoard, 0);
                long time = System.currentTimeMillis() - start;

                SwingUtilities.invokeLater(() -> {
                    loadButton.setEnabled(true);
                    solveButton.setEnabled(true);
                    solveButton.setBackground(new Color(33, 150, 243));
                    
                    if (found) {
                        lastTimeMs = time;
                        lastIterations = currentSolver.getIterations();
                        
                        saveButton.setEnabled(true); 
                        
                        statusLabel.setText("Selesai! Waktu: " + time + " ms. Total Iterasi: " + lastIterations);
                        JOptionPane.showMessageDialog(this, "Solusi Ditemukan!\nWaktu: " + time + "ms");
                    } else {
                        statusLabel.setText("Tidak ada solusi.");
                        JOptionPane.showMessageDialog(this, "Tidak ada solusi.");
                        saveButton.setEnabled(false);
                    }
                    boardPanel.repaint();
                });
            });
            solverThread.start();
        });
    }
}