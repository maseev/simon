package io.github.maseev.util;

import static io.github.maseev.util.PathUtil.getImageFolder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PathUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void passingAnInvalidImageURLWithoutExtensionMustThrowAnException() {
    getImageFolder("/folder", "/image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void passingAnInvalidImageURLWithoutSlashMustThrowAnException() {
    getImageFolder("/folder", "image.jpg");
  }

  @Test
  public void passingAValidImageURLMustReturnAValidFolderPath() {
    final String imageFolder = getImageFolder("/folder", "/image.jpg");

    assertThat(imageFolder, is(equalTo("/folder/image")));
  }
}