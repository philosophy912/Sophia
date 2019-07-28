package com.philosophy.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:19
 **/
public class FilePath {
    private static Logger log = LogManager.getLogger(FilePath.class);
    private static int DIRECTORY_ONLY = 0;
    private static int DIRECTORY_AND_FILE = 1;
    private static int FILE_ONLY = 2;

    private FilePath() {
    }

    /**
     * 获取扩展名
     *
     * @param path 文件名字
     * @return 扩展名
     * @throws IOException 当传入的是文件夹则抛出异常
     */
    public static String getExtension(Path path) throws IOException {
        String fullName = path.getFileName().toString();
        if (Files.isDirectory(path)) {
            throw new IOException(path + " is not file");
        }
        return fullName.substring(fullName.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件或者文件夹的名字
     * <br>
     * 如：Path: D:\Temp 返回Temp
     * Path: D:\Temp\eclipse.epf 返回eclipse
     *
     * @param path 文件路径
     * @return 文件夹或者文件的名字
     */
    public static String getName(Path path) {
        String name;
        String fullName = path.getFileName().toString();
        if (Files.isDirectory(path)) {
            name = path.getFileName().toString();
        } else {
            name = fullName.substring(0, fullName.lastIndexOf("."));
        }
        return name;
    }

    /**
     * 获取文件/文件夹大小
     *
     * @param path
     * @return
     * @IOException
     */
    public static long size(Path path) throws IOException {
        final List<Long> sizes = new ArrayList<>();
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    sizes.add(Files.size(file));
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            sizes.add(Files.size(path));
        }
        // 求和
        Optional<Long> size = sizes.stream().reduce((s1, s2) -> s1 + s2);
        return size.get();
    }

    /**
     * 获取所有文件列表
     *
     * @param path  文件夹地址
     * @param type  遍历类型<p>仅接收DIRECTORY_ONLY/DIRECTORY_AND_FILE/FILE_ONLY三种类型
     * @param isAll 是否遍历子文件夹<br>True表示遍历子文件夹False表示只遍历当前文件夹
     * @return
     * @throws IOException
     */
    public static List<Path> list(Path path, int type, boolean isAll) throws IOException {
        List<Path> paths = new ArrayList<>();
        if (!Files.isDirectory(path)) {
            throw new IOException(path.getFileName().toString() + " is not directory");
        }
        if (isAll) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    log.info("now visit the file[{}]", file);
                    if (type == FILE_ONLY || type == DIRECTORY_AND_FILE) {
                        paths.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    log.info("now visit the folder[{}]", dir);
                    if (type == DIRECTORY_ONLY || type == DIRECTORY_AND_FILE) {
                        paths.add(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path p : stream) {
                if ((type == DIRECTORY_ONLY) && Files.isDirectory(p)) {
                    paths.add(p);
                } else if ((type == FILE_ONLY) && !Files.isDirectory(p)) {
                    paths.add(p);
                } else if (type == DIRECTORY_AND_FILE) {
                    paths.add(p);
                }
            }
        }
        return paths;
    }

    /**
     * 获取当前路径
     *
     * @return 当前所在路径
     */
    public static String getCurrentPath() {
        return System.getProperty("user.dir");
    }
}
