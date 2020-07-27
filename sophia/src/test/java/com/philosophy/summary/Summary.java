package com.philosophy.summary;

import com.philosophy.csv.api.CsvEnum;
import com.philosophy.csv.util.CsvUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lizhe
 * @date 2020/5/25 17:44
 **/
@Slf4j
public class Summary {

    private CsvUtils csvUtils = new CsvUtils();

    @SneakyThrows
    public List<Exchange> getExchange(Path path) {
        List<Exchange> exchanges = new ArrayList<>();
        int timeRow = 3;
        int nameRow = 8;
        int amountRow = 9;
        int exchangeTypeRow = 10;
        List<String[]> contents = csvUtils.read(path, "gbk", CsvEnum.DEFAULT);
        for (String[] strings : contents) {
            Exchange exchange = new Exchange();
            if (strings.length < exchangeTypeRow) {
                continue;
            } else {
                String timeContent = strings[timeRow];
                String nameContent = strings[nameRow];
                String amountContent = strings[amountRow];
                String exchangeContent = strings[exchangeTypeRow];
                System.out.println("timeContent = " + timeContent);
                System.out.println("nameContent = " + nameContent);
                System.out.println("amountContent = " + amountContent);
                System.out.println("exchangeContent = " + exchangeContent);
                log.debug("timeContent = [{}], nameContent = [{}], amountContent =[{}], exchangeContent=[{}]",
                        timeContent, nameContent, amountContent, exchangeContent);
            }

        }
        return exchanges;
    }

    public static void main(String[] args) {
        Summary summary = new Summary();
        Path path = Paths.get("C:\\Users\\philo\\Music\\Music\\Music1\\alipay_record_20200522_1335_1.csv");
        summary.getExchange(path);
    }




}
