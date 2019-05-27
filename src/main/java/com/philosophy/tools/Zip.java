package com.philosophy.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:19
 **/
public class Zip {
    private static Logger logger = LogManager.getLogger(Zip.class);

    private Zip() {
    }


    /**
     * 压缩文件
     *
     * @param source 文件/文件夹
     * @return 压缩的文件夹路径
     */
    public static Path zip(Path source) {
        Path target;
        if (Files.isDirectory(source)) {
            target = Paths.get(source.getParent().toAbsolutePath() + File.separator + source.getFileName() + ".zip");
        } else {
            target = Paths.get(source.getParent().toAbsolutePath() + File.separator + FilePath.getName(source) + ".zip");
        }
        ZipOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            // 如果要压缩的文件存在，就删除掉之前的压缩文件
            if (Files.exists(target)) {
                Files.delete(target);
            }
            out = new ZipOutputStream(new FileOutputStream(target.toFile()));
            bos = new BufferedOutputStream(out);
            compress(out, bos, source);
        } catch (IOException e) {
            logger.error("something wrong " + e.getMessage());
        } finally {
            Closee.close(out, bos);
            return target;
        }
    }

    /**
     * 压缩文件
     * @param out <code>ZipOutputStream</code>
     * @param bos <code>BufferedOutputStream</code>
     * @param source <code>Path</code>
     * @throws IOException 抛出异常
     */
    private static void compress(ZipOutputStream out, BufferedOutputStream bos, Path source) throws IOException {
        if (Files.isDirectory(source)) {
            // 获取文件夹名字
            // String directoryName = FileUtil.getName(source);
            File[] files = source.toFile().listFiles();
            //文件夹为空
            if (files != null && files.length == 0) {
                out.putNextEntry(new ZipEntry(source.toFile().getName() + "/"));
            } else {
                for (File f : files) {
                    compress(out, bos, f.toPath());
                }
            }
        } else {
            if (Files.exists(source)) {
                out.putNextEntry(new ZipEntry(source.toFile().getName()));
                FileInputStream fis = new FileInputStream(source.toFile());
                BufferedInputStream bis = new BufferedInputStream(fis);
                int tag;
                // 将源文件写入zip文件中
                while ((tag = bis.read()) != -1) {
                    bos.write(tag);
                }
                Closee.close(bis);
            } else {
                logger.error("file not exist, cannot zip");
                throw new IOException("file not exist, cannot zip");
            }
        }
    }
}
