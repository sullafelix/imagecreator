package furkan.imageutil;

import javax.swing.*;

public class Main {
    private static final String DEFAULT_FILE_NAME = "image";

    public static void main(String[] args) throws Exception {
        String hex = JOptionPane.showInputDialog(null, "Hex değeri giriniz:");
        if(hex == null || hex.isEmpty()) {
            JOptionPane.showMessageDialog(null, "İptal edildi!");
            return;
        }
        ImageCreator imageCreator = new HexToImageConverter(() -> hex);
        String fileName = DEFAULT_FILE_NAME + "." + imageCreator.getImageFormat();
        BasicFileSaver basicFileSaver = new BasicFileSaver(imageCreator.createImage(), fileName);
        basicFileSaver.save();
    }
}
