package io.github.maseev;

import static org.junit.Assert.assertTrue;

import io.github.maseev.util.ResourceUtil;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class ImageDownloaderTest {

  @Test
  public void test() throws IOException {
    String pagePath = ResourceUtil.getPath("/page_0images.html");
    String saveTo = getFolder(pagePath) + "/tmp.html";

    new ImageDownloader().download("file://" + pagePath, saveTo);

    File file = new File(saveTo);

    assertTrue(file.exists());
    assertTrue(file.delete());
  }

  private static String getFolder(String resourcePath) {
    int index = resourcePath.lastIndexOf(File.separatorChar);

    if (index == -1) {
      throw new IllegalArgumentException("Invalid path argument");
    }

    return resourcePath.substring(0, index);
  }
}