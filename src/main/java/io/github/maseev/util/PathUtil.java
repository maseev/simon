package io.github.maseev.util;

import java.io.File;

public final class PathUtil {

  private PathUtil() {
  }

  public static String getImageFolder(String dstFolderPath, String imageURL) {
    return dstFolderPath + File.separatorChar + getImageName(imageURL);
  }

  private static String getImageName(String imagePath) {
    int slashIndex = imagePath.lastIndexOf('/');
    int dotIndex = imagePath.lastIndexOf('.');

    if (slashIndex == -1 || dotIndex == -1) {
      throw new IllegalArgumentException(String.format("image path is not valid: %s", imagePath));
    }

    return imagePath.substring(slashIndex + 1, dotIndex);
  }
}
