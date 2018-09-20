package io.github.maseev;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ImageExtractorTest {

  @Test
  public void extractImagesFromThePageMustReturn4Elements() throws IOException {
    final String path = getClass().getResource("/page.html").getPath();
    Document doc = Jsoup.parse(new File(path), "UTF-8");
    Elements elements = new ImageExtractor(doc).extract();

    assertThat(elements.size(), is(equalTo(4)));
  }
}