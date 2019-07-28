package com.philosophy.contact;

import com.philosophy.api.contact.IContact;
import com.philosophy.contact.entity.ContactName;
import com.philosophy.contact.entity.VCF;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:33
 **/
public class VCFContact extends Contact implements IContact {

    private static Logger log = LogManager.getLogger(VCFContact.class);
    @Setter
    private VCF vcf;
    private static final int VCF_SIZE = 19;

    @Override
    public List<String[]> generator() {
        ContactName firstContact = vcf.getFirst();
        ContactName lastContact = vcf.getLast();
        String preNumber = vcf.getEntity().getPreNumber();
        int contactSize = vcf.getContactSize();
        List<String[]> contacts = new ArrayList<>();
        for (int i = 0; i < contactSize; i++) {
            String first = genName(firstContact, i, contactSize);
            String last = genName(lastContact, i, contactSize);
            String[] numbers = genNumberSize(preNumber, VCF_SIZE);
            contacts.add(genVCFContact(first, last, numbers));
        }
        return contacts;
    }

    private String[] genVCFContact(String first, String last, String[] numbers) {
        String[] strings = new String[28];
        strings[0] = "BEGIN:VCARD";
        strings[1] = "VERSION:3.0";
        strings[2] = "N:" + first + ";" + last + ";;;";
        strings[3] = "FN:" + first + " " + last;
        strings[4] = "NICKNAME:" + "";
        strings[5] = "TEL;TYPE=HOME:" + numbers[0];
        strings[6] = "TEL;TYPE=CELL:" + numbers[1];
        strings[7] = "TEL;TYPE=WORK:" + numbers[2];
        strings[8] = "TEL;TYPE=WORK;TYPE=FAX:" + numbers[3];
        strings[9] = "TEL;TYPE=HOME;TYPE=FAX:" + numbers[4];
        strings[10] = "TEL;TYPE=PAGER:" + numbers[5];
        strings[11] = "TEL;TYPE=OTHER:" + numbers[6];
        strings[12] = "TEL;TYPE=CALLBACK:" + numbers[7];
        strings[13] = "TEL;TYPE=CAR:" + numbers[8];
        strings[14] = "TEL;TYPE=COMPANY_MAIN:" + numbers[9];
        strings[15] = "TEL;TYPE=ISDN:" + numbers[10];
        strings[16] = "TEL;TYPE=MAIN:" + numbers[11];
        strings[17] = "TEL;TYPE=OTHER_FAX:" + numbers[12];
        strings[18] = "TEL;TYPE=RADIO:" + numbers[13];
        strings[19] = "TEL;TYPE=TELEX:" + numbers[14];
        strings[20] = "TEL;TYPE=TTY_TDD:" + numbers[15];
        strings[21] = "TEL;TYPE=WORK_MOBILE:" + numbers[16];
        strings[22] = "TEL;TYPE=WORK_PAGER:" + numbers[17];
        strings[23] = "TEL;TYPE=ASSISTANT:" + numbers[18];
        strings[24] = "TEL;TYPE=MMS:" + numbers[19];
        strings[25] = "TEL;TYPE=self define:" + numbers[20];
        strings[26] = "X-WDJ-STARRED:0";
        strings[27] = "END:VCARD";
        return strings;
    }
}
