package com.philosophy.contact;

import com.philosophy.api.character.ICharacterFactory;
import com.philosophy.api.contact.IContact;
import com.philosophy.character.ECharacterType;
import com.philosophy.character.factory.MixCharacter;
import com.philosophy.character.factory.SignleCharacter;
import com.philosophy.util.CharacterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Contact implements IContact {
    private Logger logger = LogManager.getLogger(Contact.class);

    private int csvSize = 21;
    private int vcfSize = 19;


    private String[] genCSVContact(String first, String last, String[] numbers, ECharacterType type) {
        String[] strings = null;
        if (type.equals(ECharacterType.CHINESE)) {
            strings = new String[CSV_TITLE_CHINESE.length];
        } else if (type.equals(ECharacterType.ENGLISH)) {
            strings = new String[CSV_TITLE_ENGLISH.length];
        } else {
            return strings;
        }
        strings[3] = first;
        strings[1] = last;
        for (int j = 0; j < numbers.length; j++) {
            strings[29 + j] = numbers[0];
        }
        return strings;
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

    private String createCharacter(ECharacterType type, int size) {
        ICharacterFactory factory;
        if (type == ECharacterType.CHINESE || type == ECharacterType.ENGLISH || type == ECharacterType.SYMBOL || type == ECharacterType.NUMBER) {
            factory = new SignleCharacter();
        }
        factory = new MixCharacter();
        return factory.create(type, size);
    }



    private String genName(ContactName contactName, int i, int contactSize)  {
        CharacterUtils handler = new CharacterUtils();
        boolean isExt = contactName.isExt();
        int type = contactName.getType();
        int length = contactName.getLength();
        String result;
        if (isExt) {
            result = handler.setPrefixNumber(i, contactSize) + createCharacter(ECharacterType.fromValue(type), length);
        } else {
            result = createCharacter(ECharacterType.fromValue(type), length);
        }
        logger.debug("GenName is " + result);
        return result;
    }

    /**
     * get Number Generator size
     *
     * @param preNumber
     * @return
     */
    private String[] genNumberSize(String preNumber, int size)  {
        int numbersize = 11;
        if (!"".equals(preNumber)) {
            numbersize = 11 - preNumber.length();
        }
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(createCharacter(ECharacterType.NUMBER, numbersize));
        }
        return numbers.toArray(new String[numbers.size()]);
    }
    @Override
    public List<String[]> genCSV(CSV csv) {
        ContactName firstContact = csv.getFirst();
        ContactName lastContact = csv.getLast();
        String preNumber = csv.getEntity().getPreNumber();
        int contactSize = csv.getContactSize();
        ECharacterType language = csv.getType();
        List<String[]> contacts = new ArrayList<>();
        for (int i = 0; i < contactSize; i++) {
            String first = genName(firstContact, i, contactSize);
            String last = genName(lastContact, i, contactSize);
            String[] numbers = genNumberSize(preNumber, csvSize);
            contacts.add(genCSVContact(first, last, numbers, language));
        }
        return contacts;
    }

    @Override
    public List<String[]> genVCF(VCF vcf) {
        ContactName firstContact = vcf.getFirst();
        ContactName lastContact = vcf.getLast();
        String preNumber = vcf.getEntity().getPreNumber();
        int contactSize = vcf.getContactSize();
        List<String[]> contacts = new ArrayList<>();
        for (int i = 0; i < contactSize; i++) {
            String first = genName(firstContact, i, contactSize);
            String last = genName(lastContact, i, contactSize);
            String[] numbers = genNumberSize(preNumber, vcfSize);
            contacts.add(genVCFContact(first, last, numbers));
        }
        return contacts;
    }
}
