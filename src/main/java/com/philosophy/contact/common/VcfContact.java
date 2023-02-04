package com.philosophy.contact.common;

import com.philosophy.contact.api.IContact;
import com.philosophy.contact.entity.ContactName;
import com.philosophy.contact.entity.VcfEntity;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:33
 **/
@Slf4j
public class VcfContact extends Contact implements IContact {

    @Setter
    private VcfEntity vcfEntity;
    /**
     * VCF的通讯录数量
     */
    @Setter
    private int vcfSize = 19;
    private static final String[] PHONE_TYPES = {"HOME", "CELL", "WORK", "WORK;TYPE=FAX", "HOME;TYPE=FAX",
            "PAGER", "OTHER", "CALLBACK", "CAR", "COMPANY_MAIN", "ISDN", "MAIN", "OTHER_FAX", "RADIO", "TELEX",
            "TTY_TDD", "WORK_MOBILE", "WORK_PAGER", "ASSISTANT", "MMS", "self define"};

    @Override
    public List<String[]> generator() {
        log.info("generator contents");
        ContactName firstContact = vcfEntity.getFirst();
        ContactName lastContact = vcfEntity.getLast();
        String preNumber = vcfEntity.getEntity().getPreNumber();
        int contactSize = vcfEntity.getContactSize();
        List<String[]> contacts = new ArrayList<>();
        for (int i = 0; i < contactSize; i++) {
            String first = genName(firstContact, i, contactSize);
            String last = genName(lastContact, i, contactSize);
            String[] numbers = genNumberSize(preNumber, vcfSize);
            contacts.add(genVcfContact(first, last, numbers));
        }
        return contacts;
    }

    private String[] genVcfContact(String first, String last, String[] numbers) {
        List<String> list = new LinkedList<>();
        list.add("BEGIN:VCARD");
        list.add("VERSION:3.0");
        list.add("N:" + first + ";" + last + ";;;");
        list.add("FN:" + first + " " + last);
        list.add("NICKNAME:" + "");
        for (int i = 0; i < numbers.length; i++) {
            list.add("TEL;TYPE=" + PHONE_TYPES[i] + ":" + numbers[i]);
        }
        list.add("X-WDJ-STARRED:0");
        list.add("END:VCARD");
        int size = list.size();
        return list.toArray(new String[size]);
    }
}
