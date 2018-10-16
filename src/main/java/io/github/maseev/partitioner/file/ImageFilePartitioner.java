package io.github.maseev.partitioner.file;

import io.github.maseev.ImageDownloader;
import io.github.maseev.partitioner.image.ImagePartition;
import io.github.maseev.util.PathUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageFilePartitioner {

  private final String imagesFolder;
  private final List<ImagePartition> imagePartitions;
  private final ImageDownloader imageDownloader;

  public ImageFilePartitioner(String imagesFolder, List<ImagePartition> imagePartitions,
                              ImageDownloader imageDownloader) {
    this.imagesFolder = imagesFolder;
    this.imagePartitions = imagePartitions;
    this.imageDownloader = imageDownloader;
  }

  public List<ImageFilePartition> partition() throws IOException {
    List<ImageFilePartition> partitions = new ArrayList<>(imagePartitions.size());

    for (ImagePartition imagePartition : imagePartitions) {
      String dstFolder = PathUtil.getImageFolder(imagesFolder, imagePartition.getBigImage());

      File bigImage = imageDownloader.download(imagePartition.getBigImage(), dstFolder);
      File thumbnail = imageDownloader.download(imagePartition.getThumbnail(), dstFolder);

      partitions.add(new ImageFilePartition(bigImage, thumbnail));
    }

    return partitions;
  }
}
