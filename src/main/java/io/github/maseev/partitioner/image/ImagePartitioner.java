package io.github.maseev.partitioner.image;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImagePartitioner {

  private final Elements elements;

  public ImagePartitioner(Elements elements) {
    this.elements = elements;
  }

  public List<ImagePartition> partition() {
    List<ImagePartition> partitions = new ArrayList<>();

    for (Element anchorElement : elements) {
      String bigImage = anchorElement.absUrl("href");
      String thumbnail = getThumbnail(anchorElement);

      if (isNotDetailImage(thumbnail)) {
        partitions.add(new ImagePartition(bigImage, thumbnail));
      }
    }

    return partitions;
  }

  private static String getThumbnail(Element anchorElement) {
    for (Element image : anchorElement.children()) {
      String srcUrl = image.absUrl("src");

      if (isThumbnail(srcUrl)) {
        return srcUrl;
      }
    }

    return null;
  }

  private static boolean isThumbnail(String url) {
    return !url.contains("detalj");
  }

  private static boolean isNotDetailImage(String thumbnail) {
    // all 'detail' images point to the same 'big' image, we need to skip them
    return thumbnail != null;
  }
}
