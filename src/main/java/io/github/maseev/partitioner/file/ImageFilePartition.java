package io.github.maseev.partitioner.file;

import java.io.File;

public class ImageFilePartition {

  private final File bigImage;
  private final File thumbnail;

  public ImageFilePartition(File bigImage, File thumbnail) {
    this.bigImage = bigImage;
    this.thumbnail = thumbnail;
  }

  public File getBigImage() {
    return bigImage;
  }

  public File getThumbnail() {
    return thumbnail;
  }
}
