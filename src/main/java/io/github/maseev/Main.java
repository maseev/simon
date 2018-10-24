package io.github.maseev;

import io.github.maseev.partitioner.file.ImageFilePartition;
import io.github.maseev.partitioner.file.ImageFilePartitioner;
import io.github.maseev.partitioner.image.ImagePartition;
import io.github.maseev.partitioner.image.ImagePartitioner;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

  private static final Logger log = LogManager.getLogger();

  public static void main(String[] args) throws IOException {
    String website = "https://simonstalenhag.se/";
    String imagesFolder = "images";

    Document doc = Jsoup.connect(website).get();
    Elements anchorElements = new ImageExtractor(doc).extract();
    List<ImagePartition> imagePartitions = new ImagePartitioner(anchorElements).partition();
    List<ImageFilePartition> imageFilePartitions =
      new ImageFilePartitioner(imagesFolder, imagePartitions, new ImageDownloader()).partition();

    PageGenerator pageGenerator = new PageGenerator(imageFilePartitions);
    pageGenerator.generate("page.mustache");
  }
}
