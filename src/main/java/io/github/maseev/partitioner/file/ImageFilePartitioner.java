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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageFilePartitioner {

  static final class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setDaemon(true);
      t.setName("ImagePartitioningTask");
      return t;
    }
  }

  private static final Logger log = LogManager.getLogger();

  private final String imagesFolder;
  private final List<ImagePartition> imagePartitions;
  private final ImageDownloader imageDownloader;
  private final ExecutorService threadPool;

  public ImageFilePartitioner(String imagesFolder, List<ImagePartition> imagePartitions,
                              ImageDownloader imageDownloader) {
    this.imagesFolder = imagesFolder;
    this.imagePartitions = imagePartitions;
    this.imageDownloader = imageDownloader;

    threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
      new DaemonThreadFactory());
  }

  public List<ImageFilePartition> partition() throws IOException, ExecutionException, InterruptedException {
    List<ImageFilePartition> partitions = new ArrayList<>(imagePartitions.size());
    List<Future<ImageFilePartition>> futures = new ArrayList<>(imagePartitions.size());

    for (ImagePartition imagePartition : imagePartitions) {
      Future<ImageFilePartition> future = threadPool.submit(() -> {
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

        log.info("image and its thumbnail have been downloaded; image url: {}",
          imagePartition.getBigImage());

        return new ImageFilePartition(bigImage, thumbnail);
      });

      futures.add(future);
    }

    for (Future<ImageFilePartition> future : futures) {
      partitions.add(future.get());
    }

    return partitions;
  }
}
