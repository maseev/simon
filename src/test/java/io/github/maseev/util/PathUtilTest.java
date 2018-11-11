package io.github.maseev.util;

import static io.github.maseev.util.PathUtil.append;
import static io.github.maseev.util.PathUtil.extractFilename;
import static io.github.maseev.util.PathUtil.generateFilePath;
import static io.github.maseev.util.PathUtil.generateFolderName;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import org.junit.Test;

public class PathUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void passingAnInvalidImageURLWithoutExtensionMustThrowAnException() {
    generateFolderName("/folder", "/image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void passingAnInvalidImageURLWithoutSlashMustThrowAnException() {
    generateFolderName("/folder", "image.jpg");
  }

  @Test
  public void passingAValidImageURLMustReturnAValidFolderPath() {
    String imageFolder = generateFolderName("/folder", "/image.jpg");

    assertThat(imageFolder, is(equalTo("/folder/image")));
  }

  @Test
  public void generatingFilePathMustReturnAValidPathWithSeparator() {
    String folder = "images";
    String filename = "image.jpg";
    char separator = File.separatorChar;

    assertThat(generateFilePath(folder, filename), is(equalTo(folder + separator + filename)));
  }

  @Test
  public void extractingFilenameFromValidURLMustReturnTheFileNameItself() {
    String imageFilename = "image.jpg";
    String imageURL = "https://simonstalenhag.se/bilder/" + imageFilename;
    String extractedFilename = extractFilename(imageURL);

    assertThat(extractedFilename, is(equalTo(imageFilename)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void extractingFilenameFromInvalidURLMustThrowException() {
    String imageFilename = "image.jpg";

    extractFilename(imageFilename);
  }

  @Test
  public void appendingToValidFileNameMustReturnChangedFileNameWithAppender() {
    String imageName = "image";
    String fileExtension = ".jpg";
    String imageFilename = imageName + fileExtension;
    String appender = "thumbnail";

    assertThat(append(imageFilename, appender), is(equalTo(imageName + appender + fileExtension)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void appendingToInvalidFileNameMustThrowException() {
    String imageName = "image";
    String appender = "thumbnail";

    append(imageName, appender);
  }
}