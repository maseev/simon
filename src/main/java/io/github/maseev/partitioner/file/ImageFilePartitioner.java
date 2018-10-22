package io.github.maseev.partitioner.file;

import static io.github.maseev.util.PathUtil.append;
import static io.github.maseev.util.PathUtil.extractFilename;
import static io.github.maseev.util.PathUtil.generateFilePath;
import static io.github.maseev.util.PathUtil.generateFolderName;

import io.github.maseev.ImageDownloader;
import io.github.maseev.partitioner.image.ImagePartition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageFilePartitioner {

  private static final Logger log = LogManager.getLogger();

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
      String dstFolder = generateFolderName(imagesFolder, imagePartition.getBigImage());
      String bigImageFilename = extractFilename(imagePartition.getBigImage());
      String thumbnailFilename = extractFilename(imagePartition.getThumbnail());
      String bigImageDstPath = generateFilePath(dstFolder, bigImageFilename);
      String thumbnailDstPath;

      if (bigImageFilename.equals(thumbnailFilename)) {
        thumbnailDstPath = generateFilePath(dstFolder, append(thumbnailFilename, "thumbnail"));
      } else {
        thumbnailDstPath = generateFilePath(dstFolder, thumbnailFilename);
      }

      new File(dstFolder).mkdirs();

      File bigImage = imageDownloader.download(imagePartition.getBigImage(), bigImageDstPath);
      File thumbnail = imageDownloader.download(imagePartition.getThumbnail(), thumbnailDstPath);

      partitions.add(new ImageFilePartition(bigImage, thumbnail));

      log.info("image and its thumbnail have been downloaded; image url: {}",
        imagePartition.getBigImage());
    }

    return partitions;
  }
}
