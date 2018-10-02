package io.github.maseev;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class ImageDownloaderUtil {

  private ImageDownloaderUtil() {
  }

  public static Path download(String imagePath, String saveTo) throws IOException {
    URL url = new URL(imagePath);
    Path path = Paths.get(saveTo);

    try (InputStream input = url.openConnection().getInputStream()) {
      Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
    }

    return path;
  }
}
