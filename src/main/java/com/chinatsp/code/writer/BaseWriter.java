package com.chinatsp.code.writer;

import com.chinatsp.code.utils.WriterUtils;
import com.philosophy.base.util.FilesUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.chinatsp.dbc.api.IConstant.UTF8;

/**
 * @author lizhe
 * @date 2020/9/28 11:03
 **/
public abstract class BaseWriter {
    @Resource
    protected WriterUtils writerUtils;

    String TEMPLATES = "templates";
    String FTLH = ".ftlh";

    protected Map<String, Object> createMap(String fileName) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("fileName", fileName);
        map.put("createDate", getDateTime());
        return map;
    }

    private String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 根据传递的type查找template文件的文件夹位置
     *
     * @return 找打的文件夹
     */
    @SneakyThrows
    private String getTemplateFolder() {
        // 以当前文件夹下面的templates优先查找
        String folder = FilesUtils.getCurrentPath() + File.separator + TEMPLATES;
        if (Files.exists(Paths.get(folder))) {
            return folder;
        } else {
            String e = "请检查当前路径下是否存在对应的template文件夹";
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    protected Template getTemplate(String fileName) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        File file = new File(getTemplateFolder());
        configuration.setDirectoryForTemplateLoading(file);
        configuration.setDefaultEncoding(UTF8);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        // 自动增加后缀.ftlh
        if (!fileName.endsWith(FTLH)) {
            fileName = fileName + FTLH;
        }
        return configuration.getTemplate(fileName);
    }

    @SneakyThrows
    protected Writer getWriter(Path path) {
        return new OutputStreamWriter(Files.newOutputStream(path), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    protected void writeToFile(Template template, Map<String, Object> map, Path path) {
        Writer out = getWriter(path);
        template.process(map, out);
    }

}
