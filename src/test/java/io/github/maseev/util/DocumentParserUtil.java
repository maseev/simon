package io.github.maseev.util;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class DocumentParserUtil {

  private DocumentParserUtil() {
  }

  public static Document parse(String path) throws IOException {
    final String resourcePath = DocumentParserUtil.class.getResource(path).getPath();

    return Jsoup.parse(new File(resourcePath), "UTF-8");
  }
}
