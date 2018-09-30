package io.github.maseev.util;

public final class ResourceUtil {

  private ResourceUtil() {
  }

  public static String getPath(String path) {
    return DocumentParserUtil.class.getResource(path).getPath();
  }
}
