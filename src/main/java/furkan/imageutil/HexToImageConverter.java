package furkan.imageutil;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Supplier;

public class HexToImageConverter implements ImageCreator {
    private final Supplier<String> imageHexSource;
    private String imageFormat;

    public HexToImageConverter(Supplier<String> imageHexSource) {
        this.imageHexSource = imageHexSource;
    }



    @Override
    public byte[] createImage() throws IOException, DecoderException {
        byte[] imageHexInBytes = Hex.decodeHex(this.imageHexSource.get());
        return hexByteArrayToImageByteArray(imageHexInBytes);
    }

    @Override
    public String getImageFormat() throws IOException, DecoderException {
        if(this.imageFormat == null) {
            byte[] imageHexInBytes = Hex.decodeHex(this.imageHexSource.get());
            ByteArrayInputStream bais = new ByteArrayInputStream(imageHexInBytes);
            this.imageFormat = getImageFormat(bais);
        }

        return this.imageFormat;
    }

    private byte[] hexByteArrayToImageByteArray(byte[] imageInByte) throws IOException, DecoderException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);
        String imageFormat = getImageFormat();
        BufferedImage image = ImageIO.read(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormat, baos);
        return baos.toByteArray();
    }

    private String getImageFormat(InputStream input) throws IOException {
        ImageInputStream stream = ImageIO.createImageInputStream(input);

        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
        if (!iter.hasNext()) {
            return null;
        }
        ImageReader reader = iter.next();
        ImageReadParam param = reader.getDefaultReadParam();
        reader.setInput(stream, true, true);
        BufferedImage bi;
        try {
            bi = reader.read(0, param);
            return reader.getFormatName().toLowerCase();
        } finally {
            reader.dispose();
            stream.close();
        }
    }
}
