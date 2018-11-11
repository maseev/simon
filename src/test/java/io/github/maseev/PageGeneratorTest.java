package io.github.maseev;

import static io.github.maseev.util.PathUtil.generateFilePath;
import static io.github.maseev.util.PathUtil.getFolderPath;
import static io.github.maseev.util.ResourceUtil.getPath;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.github.maseev.partitioner.file.ImageFilePartition;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class PageGeneratorTest {

  @Test
  public void generatedPageMustContainThePassedImageURL() throws IOException {
    String filename = "page_0images.html";
    File file = new File(getPath(File.separatorChar + filename));
    List<ImageFilePartition> partitions = singletonList(new ImageFilePartition(file, file));
    String indexFileName = "index.html";
    String saveTo = generateFilePath(getFolderPath(file.getPath()), indexFileName);

    new PageGenerator(partitions).generate("page.mustache", saveTo);

    Document document = Jsoup.parse(new File(saveTo), "UTF-8");
    Elements elements = document.select("div[class*=grid-item]");
    assertThat(elements.size(), is(equalTo(1)));

    Elements anchors = elements.select("a");
    assertThat(anchors.size(), is(equalTo(1)));

    Element anchor = anchors.get(0);
    String bigImage = anchor.attr("href");
    assertThat(bigImage, is(equalTo(file.getPath())));

    Elements images = anchor.select("img");
    assertThat(images.size(), is(equalTo(1)));

    Element image = images.get(0);
    String thumbnail = image.attr("src");
    assertThat(thumbnail, is(equalTo(file.getPath())));

    File indexFilepath = new File(saveTo);
    assertTrue(indexFilepath.delete());
  }
}