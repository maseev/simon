package io.github.maseev.partitioner.file;

import static io.github.maseev.util.PathUtil.getFolderPath;
import static io.github.maseev.util.ResourceUtil.getPath;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.maseev.ImageDownloader;
import io.github.maseev.partitioner.image.ImagePartition;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class ImageFilePartitionerTest {

  @Test
  public void filePartitioningMustReturnTheCorrectFilenames() throws Exception {
    String filename = "page_0images.html";
    File file = new File(getPath(File.separatorChar + filename));
    String folderPath = getFolderPath(file.getPath());

    ImageDownloader downloader = Mockito.mock(ImageDownloader.class);
    when(downloader.download(anyString(), anyString())).thenReturn(Optional.of(file));

    ImageFilePartitioner partitioner =
      new ImageFilePartitioner(folderPath,
        singletonList(new ImagePartition(file.getPath(), file.getPath())), downloader);

    List<ImageFilePartition> imageFilePartitions = partitioner.partition();

    assertThat(imageFilePartitions.size(), is(equalTo(1)));

    ImageFilePartition imageFilePartition = imageFilePartitions.get(0);

    assertThat(imageFilePartition.getBigImage(), is(equalTo(file)));
    assertThat(imageFilePartition.getThumbnail(), is(equalTo(file)));
  }
}