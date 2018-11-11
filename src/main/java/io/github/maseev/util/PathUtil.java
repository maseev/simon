package io.github.maseev.util;

import static java.lang.String.format;

import java.io.File;

public final class PathUtil {

  private PathUtil() {
  }

  public static String getFolderPath(String filePath) {
    int folderSeparatorIndex = filePath.lastIndexOf(File.separatorChar);

    if (folderSeparatorIndex == -1) {
      throw new IllegalArgumentException(format("invalid file path: %s", filePath));
    }

    return filePath.substring(0, folderSeparatorIndex);
  }

  public static String generateFilePath(String folderPath, String filename) {
    return folderPath + File.separatorChar + filename;
  }

  public static String generateFolderName(String dstFolderPath, String imageURL) {
    return dstFolderPath + File.separatorChar + extractImageNameFromURL(imageURL);
  }

  public static String extractFilename(String imageURL) {
    final int slashIndex = imageURL.lastIndexOf('/');

    if (slashIndex == -1) {
      throw new IllegalArgumentException(format("unable to extract image file name: %s", imageURL));
    }

    return imageURL.substring(slashIndex + 1);
  }

  public static String append(String fileName, String appender) {
    String extension = getExtension(fileName);
    String fileNameExplicit = fileName.substring(0, fileName.lastIndexOf('.'));

    return fileNameExplicit + appender + extension;
  }

  private static String extractImageNameFromURL(String imagePath) {
    int slashIndex = imagePath.lastIndexOf('/');
    int dotIndex = imagePath.lastIndexOf('.');

    if (slashIndex == -1 || dotIndex == -1) {
      throw new IllegalArgumentException(format("image path is not valid: %s", imagePath));
    }

    return imagePath.substring(slashIndex + 1, dotIndex);
  }

  private static String getExtension(String filename) {
    int extensionIndex = filename.lastIndexOf('.');

    if (extensionIndex == -1) {
      throw new IllegalArgumentException(format("unable to find the file extension: %s", filename));
    }

    return filename.substring(extensionIndex);
  }
}
