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

    log.info("extracting images from the page");
    Document doc = Jsoup.connect(website).get();
    Elements anchorElements = new ImageExtractor(doc).extract();
    log.info("done");

    log.info("partitioning images");
    List<ImagePartition> imagePartitions = new ImagePartitioner(anchorElements).partition();
    log.info("done");

    log.info("downloading and partitioning images");
    List<ImageFilePartition> imageFilePartitions =
      new ImageFilePartitioner(imagesFolder, imagePartitions, new ImageDownloader()).partition();
    log.info("done");

    log.info("generating the HTML page");
    PageGenerator pageGenerator = new PageGenerator(imageFilePartitions);
    pageGenerator.generate("page.mustache");
    log.info("done");
  }
}
