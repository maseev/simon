package io.github.maseev;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ImageDownloader {

  public Optional<File> download(String imagePath, String saveTo) throws IOException {
    URL url = new URL(imagePath);
    Path path = Paths.get(saveTo);

    try {
      try (InputStream input = new BufferedInputStream(url.openConnection().getInputStream())) {
        Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    }

    return Optional.of(path.toFile());
  }
}
