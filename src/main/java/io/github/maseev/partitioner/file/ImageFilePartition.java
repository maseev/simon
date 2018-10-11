package io.github.maseev.partitioner.file;

import java.io.File;
import java.util.List;

public class ImageFilePartition {

  private final File bigImage;
  private final File thumbnail;
  private final List<File> details;

  public ImageFilePartition(File bigImage, File thumbnail, List<File> details) {
    this.bigImage = bigImage;
    this.thumbnail = thumbnail;
    this.details = details;
  }

  public File getBigImage() {
    return bigImage;
  }

  public File getThumbnail() {
    return thumbnail;
  }

  public List<File> getDetails() {
    return details;
  }
}
