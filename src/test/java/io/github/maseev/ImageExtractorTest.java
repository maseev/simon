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
  public void extractImagesFromThePageOneThreeMustReturn4Elements() throws IOException {
    Document doc = DocumentParserUtil.parse("/page_1image_3details.html");
    Elements elements = new ImageExtractor(doc).extract();

    assertThat(elements.size(), is(equalTo(4)));
  }

  @Test
  public void extractImagesFromThePageOneZeroMustReturn1Element() throws IOException {
    Document doc = DocumentParserUtil.parse("/page_1image_0details.html");
    Elements elements = new ImageExtractor(doc).extract();

    assertThat(elements.size(), is(equalTo(1)));
  }

  @Test
  public void extractImagesFromThePageZeroMustReturn0Elements() throws IOException {
    Document doc = DocumentParserUtil.parse("/page_0images.html");
    Elements elements = new ImageExtractor(doc).extract();

    assertThat(elements.size(), is(equalTo(0)));
  }
}