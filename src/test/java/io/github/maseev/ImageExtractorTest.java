package io.github.maseev;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.github.maseev.util.DocumentParserUtil;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ImageExtractorTest {

  @Test
  public void extractImagesFromThePageMustReturn4Elements() throws IOException {
    Document doc = DocumentParserUtil.parse("/page.html");
    Elements elements = new ImageExtractor(doc).extract();

    assertThat(elements.size(), is(equalTo(4)));
  }
}