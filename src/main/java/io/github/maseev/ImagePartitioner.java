package io.github.maseev;

import java.util.ArrayList;
import java.util.Iterator;
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

    String bigImage = null;
    String thumbnail = null;
    List<String> details = new ArrayList<>();

    Iterator<Element> elementIterator = elements.iterator();

    while (elementIterator.hasNext()) {
      Element element = elementIterator.next();
      String hrefUrl = element.absUrl("href");

      if (bigImage == null) {
        bigImage = hrefUrl;
      } else if (!bigImage.equals(hrefUrl)) {
        partitions.add(new ImagePartition(bigImage, thumbnail, details));
        details = new ArrayList<>();
      }

      for (Element image : element.children()) {
        String srcUrl = image.absUrl("src");

        if (isDetailImage(srcUrl)) {
          details.add(srcUrl);
        } else {
          thumbnail = srcUrl;
        }
      }

      if (!elementIterator.hasNext()) {
        partitions.add(new ImagePartition(bigImage, thumbnail, details));
      }
    }

    return partitions;
  }

  private static boolean isDetailImage(String url) {
    return url.contains("detalj");
  }
}
