package io.github.maseev;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.github.maseev.util.ResourceUtil;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.junit.Test;

public class ImageDownloaderTest {

  @Test
  public void downloadingAFileMustSaveItToTheDesignatedFolder() throws IOException {
    String filename = "page_0images.html";
    String pagePath = ResourceUtil.getPath(File.separatorChar + filename);
    String dstFolder = getFolder(pagePath) + File.separatorChar + "page";
    String saveTo = dstFolder + File.separatorChar + filename;

    new File(dstFolder).mkdirs();

    new ImageDownloader().download("file://" + pagePath, saveTo);

    File file = new File(saveTo);

    assertTrue(file.exists());
    assertTrue(file.delete());

    new File(saveTo).delete();
  }

  @Test
  public void downloadingFromInvalidURLMustReturnEmptyOptionalFile() throws IOException {
    String filename = "page_0images.html";
    String pagePath = ResourceUtil.getPath(File.separatorChar + filename);
    Optional<File> file =
      new ImageDownloader().download("file://" + pagePath + "invalid", "invalid");

    assertFalse(file.isPresent());
  }

  private static String getFolder(String resourcePath) {
    int index = resourcePath.lastIndexOf(File.separatorChar);

    if (index == -1) {
      throw new IllegalArgumentException("Invalid path argument");
    }

    return resourcePath.substring(0, index);
  }
}