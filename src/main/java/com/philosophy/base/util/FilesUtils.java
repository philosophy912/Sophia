package com.philosophy.base.util;

import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @date 2019/10/11:16:02
 */
@Slf4j
public class FilesUtils extends FileUtils {
    /**
     * 获取当前路径
     *
     * @return 当前的路径
     */
    public static String getCurrentPath() {
        return System.getProperty("user.dir");
    }


    /**
     * 获取文件/文件夹大小
     *
     * @param path 文件/文件夹
     * @return 大小(bytes)
     * @throws IOException IO异常
     */
    public static long size(Path path) throws IOException {
        long size = 0L;
        List<Long> sizes = new ArrayList<>();
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    sizes.add(Files.size(file));
                    return FileVisitResult.CONTINUE;
                }
            });
            for (Long s : sizes) {
                size += s;
            }
        } else {
            return Files.size(path);
        }
        return size;
    }

    /**
     * 获取文件名以及扩展名
     *
     * @param path 文件名字
     * @return Pair<String, String> Pair<文件名, 扩展名>
     * @throws IOException 当文件是文件夹的时候抛出IO异常
     */
    public static Pair<String, String> getFileNameAndExtension(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            throw new IOException(path + " is not file");
        }
        String fullName = path.getFileName().toString();
        int pointIndex = fullName.lastIndexOf(".");
        return new Pair<>(fullName.substring(0, pointIndex), fullName.substring(pointIndex + 1));
    }


    /**
     * 获取扩展名
     *
     * @param path 文件名字
     * @return 扩展名
     * @throws IOException 当文件是文件夹的时候抛出IO异常
     */
    public static String getExtension(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            throw new IOException(path + " is not file");
        }
        String fullName = path.getFileName().toString();
        return fullName.substring(fullName.lastIndexOf(".") + 1);
    }

    /**
     * 删除文件（文件存在的时候才删除）
     *
     * @param paths 文件列表
     */
    public static void deleteFiles(Path... paths) throws IOException {
        for (Path path : paths) {
            if (Files.exists(path)) {
                log.debug("delete file [{}]", path);
                Files.delete(path);
            }
        }
    }
}
