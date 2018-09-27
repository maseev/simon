package io.github.maseev;

import java.util.List;

public class ImagePartition {

  private final String bigImage;
  private final String thumbnail;
  private final List<String> details;

  public ImagePartition(String bigImage, String thumbnail, List<String> details) {
    this.bigImage = bigImage;
    this.thumbnail = thumbnail;
    this.details = details;
  }

  public String getBigImage() {
    return bigImage;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public List<String> getDetails() {
    return details;
  }
}
