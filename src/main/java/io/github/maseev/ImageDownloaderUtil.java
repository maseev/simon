package io.github.maseev;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public final class ImageDownloaderUtil {

  private ImageDownloaderUtil() {
  }

  public static File download(String imagePath, String saveTo) throws IOException {
    URL url = new URL(imagePath);
    Path path = Paths.get(saveTo);

    try (InputStream input = url.openConnection().getInputStream()) {
      Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
    }

    return path.toFile();
  }

  public static List<File> download(List<String> imagePaths, String saveTo) throws IOException {
    List<File> files = new ArrayList<>(imagePaths.size());

    for (String imagePath : imagePaths) {
      files.add(download(imagePath, saveTo));
    }

    return files;
  }
}
