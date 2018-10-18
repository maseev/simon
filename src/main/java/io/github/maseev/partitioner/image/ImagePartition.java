package io.github.maseev.partitioner.image;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ImagePartition)) {
      return false;
    }

    ImagePartition that = (ImagePartition) o;

    return bigImage.equals(that.bigImage) &&
      thumbnail.equals(that.thumbnail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bigImage, thumbnail);
  }
}
