package io.github.maseev;

import io.github.maseev.partitioner.file.ImageFilePartition;
import io.github.maseev.partitioner.file.ImageFilePartitioner;
import io.github.maseev.partitioner.image.ImagePartition;
import io.github.maseev.partitioner.image.ImagePartitioner;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

  private static final Logger log = LogManager.getLogger();

  public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
    String website = "https://simonstalenhag.se/";
    String imagesFolder = "images";

    log.info("extracting images from the page");
    Document doc = Jsoup.connect(website).get();
    Elements anchorElements = new ImageExtractor(doc).extract();
    log.info("finished extracting images");

    log.info("partitioning images");
    List<ImagePartition> imagePartitions = new ImagePartitioner(anchorElements).partition();
    log.info("finished partitioning images");

    log.info("downloading and partitioning images");
    long start = System.nanoTime();
    List<ImageFilePartition> imageFilePartitions =
      new ImageFilePartitioner(imagesFolder, imagePartitions, new ImageDownloader()).partition();
    long end = System.nanoTime() - start;
    log.info("finished downloading and partitioning images; spent: {} seconds",
      TimeUnit.NANOSECONDS.toSeconds(end));

    log.info("generating the HTML page");
    PageGenerator pageGenerator = new PageGenerator(imageFilePartitions);
    pageGenerator.generate("page.mustache");
    log.info("done");
  }
}
