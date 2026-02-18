import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } 
            catch (Exception ignored) { }

            QueensGUI gui = new QueensGUI();
            gui.setVisible(true);
        });
    }
}