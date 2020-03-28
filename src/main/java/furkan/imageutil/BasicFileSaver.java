package furkan.imageutil;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BasicFileSaver {
    private byte[] bytes;
    private String fileName;

    public BasicFileSaver(byte[] bytes) {
        this(bytes, null);
    }

    public BasicFileSaver(byte[] bytes, String fileName) {
        this.bytes = bytes;
        this.fileName = fileName;
    }

    public void save() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if(fileName != null) {
            jfc.setSelectedFile(
                    new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + File.separator + fileName));
        }
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            Files.write(selectedFile.toPath(), this.bytes);
        } else {
            JOptionPane.showMessageDialog(null, "İşlem iptal edildi");
        }
    }
}
