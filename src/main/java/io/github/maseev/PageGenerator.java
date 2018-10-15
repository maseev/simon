package io.github.maseev;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.github.maseev.partitioner.file.ImageFilePartition;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PageGenerator {

  private final List<ImageFilePartition> imageFilePartitions;

  public PageGenerator(List<ImageFilePartition> imageFilePartitions) {
    this.imageFilePartitions = imageFilePartitions;
  }

  public void generate() throws IOException {
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("page.mustache");

    mustache.execute(new BufferedWriter(new FileWriter("index.html")),
      imageFilePartitions).flush();
  }
}
