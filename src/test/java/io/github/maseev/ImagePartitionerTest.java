package io.github.maseev;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import io.github.maseev.util.DocumentParserUtil;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class ImagePartitionerTest {

  @Test
  public void partitioningEmptyElementsMustReturnEmptyListOfPartitions() throws IOException {
    List<ImagePartition> partitions = partition("/page_0images.html");

    assertThat(partitions.size(), is(equalTo(0)));
  }

  @Test
  public void partitioningOneElementPageMustReturnASingletonListOfPartitions() throws IOException {
    List<ImagePartition> partitions = partition("/page_1image_0details.html");

    assertThat(partitions.size(), is(equalTo(1)));

    ImagePartition partition = partitions.get(0);

    assertNotNull(partition.getBigImage());
    assertNotNull(partition.getThumbnail());
    assertThat(partition.getDetails().size(), is(equalTo(0)));
  }

  @Test
  public void partitioningPageWithOneImageAndThreeDetailsMustReturnOnePartitionWithThreeDetails() throws IOException {
    List<ImagePartition> partitions = partition("/page_1image_3details.html");

    assertThat(partitions.size(), is(equalTo(1)));

    ImagePartition partition = partitions.get(0);

    assertNotNull(partition.getBigImage());
    assertNotNull(partition.getThumbnail());
    assertThat(partitions.get(0).getDetails().size(), is(equalTo(3)));
  }

  public static List<ImagePartition> partition(String path) throws IOException {
    Document doc = DocumentParserUtil.parse(path);
    ImagePartitioner partitioner = new ImagePartitioner(new ImageExtractor(doc).extract());

    return partitioner.partition();
  }
}