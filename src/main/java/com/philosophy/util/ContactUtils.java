package com.philosophy.util;

import com.philosophy.contact.CSV;
import com.philosophy.contact.Contact;
import com.philosophy.contact.VCF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ContactUtils {
    private static Logger logger = LogManager.getLogger(ContactUtils.class);
    private static Contact contact = new Contact();

    private ContactUtils(){}

    public static void createCSVContact(CSV csv, Path path) {
        List<String[]> contents = contact.genCSV(csv);
        CSVUtils util = new CSVUtils();
        util.write(path, contents);
    }

    public static void createVCFContact(VCF vcf, Path path) throws IOException {
        List<String[]> contents = contact.genVCF(vcf);
        TxtUtil.write(path, contents);
    }
}
