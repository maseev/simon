package io.github.maseev;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImageExtractor {

  private final Document document;

  public ImageExtractor(Document document) {
    this.document = document;
  }

  public Elements extract() {
    return document.select("a[href*=bilderbig]");
  }
}
