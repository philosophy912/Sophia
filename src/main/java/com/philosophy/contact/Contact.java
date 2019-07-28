package com.philosophy.contact;

import com.philosophy.api.character.ICharacterFactory;
import com.philosophy.api.character.ECharacterType;
import com.philosophy.character.factory.MixCharacter;
import com.philosophy.character.factory.SingleCharacter;
import com.philosophy.contact.entity.ContactName;
import com.philosophy.util.CharacterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:35
 **/
public class Contact {
    private static Logger log = LogManager.getLogger(Contact.class);

    protected String genName(ContactName contactName, int i, int contactSize) {
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
        log.debug("GenName is " + result);
        return result;
    }

    protected String createCharacter(ECharacterType type, int size) {
        ICharacterFactory factory;
        if (type == ECharacterType.CHINESE || type == ECharacterType.ENGLISH || type == ECharacterType.SYMBOL || type == ECharacterType.NUMBER) {
            factory = new SingleCharacter();
        } else {
            factory = new MixCharacter();
        }
        return factory.create(type, size);
    }

    protected String[] genNumberSize(String preNumber, int size) {
        int numberSize = 11;
        if (!"".equals(preNumber)) {
            numberSize = 11 - preNumber.length();
        }
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(createCharacter(ECharacterType.NUMBER, numberSize));
        }
        int arraySize = numbers.size();
        return numbers.toArray(new String[arraySize]);
    }
}
