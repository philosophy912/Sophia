package com.philosophy.base.util;

import com.philosophy.base.common.Closee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;


/**
 * @author lizhe
 * @date 2019/10/10:14:48
 */
@Slf4j
public class ZipUtils {

    /**
     * 压缩单个文件
     *
     * @param path                   文件
     * @param fileName               压缩后的文件
     * @param zipArchiveOutputStream zip流
     */
    private static void zipFile(Path path, String fileName,
                                ZipArchiveOutputStream zipArchiveOutputStream) {
        InputStream inputStream = null;
        try {
            ZipArchiveEntry entry = new ZipArchiveEntry(path.toFile(), fileName);
            zipArchiveOutputStream.putArchiveEntry(entry);
            inputStream = Files.newInputStream(path);
            byte[] buffer = new byte[1024 * 5];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                zipArchiveOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("for some reason zip file failed, please check it now. Error message is  {}", e.getMessage());
        } finally {
            Closee.close(inputStream);
        }
    }

    /**
     * 压缩文件
     *
     * @param path               压缩的文件
     * @param zipFile            压缩后的文件名
     * @param deleteFileAfterZip 是否删除被压缩的文件
     * @return 是否压缩成功
     */
    private static boolean compressFile(Path path, Path zipFile, boolean deleteFileAfterZip) {
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(Files.newOutputStream(zipFile));
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
            String fileName = path.getFileName().toString();
            log.debug("now zip file {}", fileName);
            zipFile(path, fileName, zipArchiveOutputStream);
            zipArchiveOutputStream.closeArchiveEntry();
            zipArchiveOutputStream.finish();
            // 删除文件
            if (deleteFileAfterZip) {
                deleteFileAfterZip(path);
            }
        } catch (IOException e) {
            log.error("for some reason zip file failed, please check it now. Error message is  {}", e.getMessage());
            return false;
        } finally {
            Closee.close(zipArchiveOutputStream);
        }
        return true;
    }

    /**
     * 压缩文件夹
     *
     * @param path               压缩的文件夹
     * @param zipFile            压缩后的文件名
     * @param deleteFileAfterZip 是否删除被压缩的文件
     * @return 是否压缩成功
     */
    private static boolean compressDirectory(Path path, Path zipFile, boolean deleteFileAfterZip) {
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(Files.newOutputStream(zipFile));
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
            ZipArchiveOutputStream finalZipArchiveOutputStream = zipArchiveOutputStream;
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileName = file.toString().replace(path.toString(), "").substring(1);
                    log.debug("now zip file {}", fileName);
                    zipFile(file, fileName, finalZipArchiveOutputStream);
                    return FileVisitResult.CONTINUE;
                }
            });
            zipArchiveOutputStream.closeArchiveEntry();
            zipArchiveOutputStream.finish();
            // 删除文件
            if (deleteFileAfterZip) {
                deleteFileAfterZip(path);
            }
        } catch (IOException e) {
            log.error("for some reason zip file failed, please check it now. Error message is  {}", e.getMessage());
        } finally {
            Closee.close(zipArchiveOutputStream);
        }
        return true;
    }

    /**
     * 是否删除文件
     *
     * @param path 被压缩的文件
     * @throws IOException IO异常
     */
    private static void deleteFileAfterZip(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            FileUtils.deleteDirectory(path.toFile());
        } else {
            Files.delete(path);
        }
    }

    /**
     * 压缩文件夹或者文件
     *
     * @param path               要压缩的文件或者文件夹
     * @param zipFile            压缩后生成的文件
     * @param deleteFileAfterZip 是否删除压缩的文件/文件夹
     * @return 是否压缩成功
     */
    public static boolean compress(Path path, Path zipFile, boolean deleteFileAfterZip) {
        if (Files.isDirectory(path)) {
            return compressDirectory(path, zipFile, deleteFileAfterZip);
        } else {
            return compressFile(path, zipFile, deleteFileAfterZip);
        }
    }

    /**
     * 压缩文件列表(仅支持文件，不支持文件夹)
     *
     * @param pathList           文件列表
     * @param zipFile            压缩后生成的文件
     * @param deleteFileAfterZip 是否删除压缩的文件/文件夹
     * @return 是否压缩成功
     */
    public static boolean compress(List<Path> pathList, Path zipFile, boolean deleteFileAfterZip) {
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(Files.newOutputStream(zipFile));
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
            for (Path path : pathList) {
                if (Files.isDirectory(path)) {
                    log.debug("path [{}] is not file", path);
                } else {
                    String fileName = path.getFileName().toString();
                    log.debug("now zip file {}", fileName);
                    zipFile(path, fileName, zipArchiveOutputStream);
                }
            }
            zipArchiveOutputStream.closeArchiveEntry();
            zipArchiveOutputStream.finish();
            // 删除文件
            if (deleteFileAfterZip) {
                for (Path path : pathList) {
                    Files.deleteIfExists(path);
                }
            }
        } catch (IOException e) {
            log.error("for some reason zip file failed, please check it now. Error message is  {}", e.getMessage());
            return false;
        } finally {
            Closee.close(zipArchiveOutputStream);
        }
        return true;
    }

    /**
     * 解压缩Zip文件到文件夹（递归解压）
     *
     * @param zipFile 压缩包
     * @param folder  要解压缩到的目的文件夹
     * @return 是否压缩成功
     */
    public static boolean decompress(Path zipFile, Path folder) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ZipArchiveInputStream zipArchiveInputStream = null;
        ArchiveEntry entry;
        try {
            inputStream = Files.newInputStream(zipFile);
            zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");
            while (null != (entry = zipArchiveInputStream.getNextEntry())) {
                String fileName = entry.getName();
                Path entryFile = Paths.get(folder.toAbsolutePath().toString() + File.separator + fileName);
                if (entry.isDirectory()) {
                    Files.createDirectories(entryFile);
                } else {
                    Path parent = entryFile.getParent();
                    log.debug("parent folder is {}", parent);
                    if (!Files.exists(parent)) {
                        Files.createDirectories(parent);
                    }
                    byte[] buffer = new byte[1024 * 5];
                    outputStream = Files.newOutputStream(entryFile);
                    int len;
                    while ((len = zipArchiveInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            log.error("for some reason unzip file failed, please check it now. Error message is  {}", e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            Closee.close(inputStream, outputStream, zipArchiveInputStream);
        }
        return true;
    }

}
