package io.github.maseev.util;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class DocumentParserUtil {

  private DocumentParserUtil() {
  }

  public static Document parse(String path) throws IOException {
    return Jsoup.parse(new File(ResourceUtil.getPath(path)), "UTF-8");
  }
}
