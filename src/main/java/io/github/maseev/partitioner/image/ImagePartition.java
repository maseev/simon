package io.github.maseev.partitioner.image;

public class ImagePartition {

  private final String bigImage;
  private final String thumbnail;

  public ImagePartition(String bigImage, String thumbnail) {
    this.bigImage = bigImage;
    this.thumbnail = thumbnail;
  }

  public String getBigImage() {
    return bigImage;
  }

  public String getThumbnail() {
    return thumbnail;
  }
}
