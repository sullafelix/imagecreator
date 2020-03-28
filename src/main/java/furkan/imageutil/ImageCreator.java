package furkan.imageutil;

public interface ImageCreator {
    byte[] createImage() throws Exception;
    String getImageFormat() throws Exception;
}
