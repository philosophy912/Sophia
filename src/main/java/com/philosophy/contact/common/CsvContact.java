package com.philosophy.contact.common;

import com.philosophy.contact.api.IContact;
import com.philosophy.character.api.CharEnum;
import com.philosophy.contact.entity.Csv;
import com.philosophy.contact.entity.ContactName;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:28
 **/
@Slf4j
public class CsvContact extends Contact implements IContact {
    @Setter
    private Csv csv;
    /**
     * CSV创建的通讯录数量
     */
    @Setter
    private int csvSize = 21;

    private static final String[] CSV_TITLE_CHINESE = new String[]
            {"英文称谓", "名", "中间名", "姓", "中文称谓", "单位", "部门", "职务", "商务地址 街道", "商务地址 街道 2",
                    "商务地址 街道 3", "商务地址 市/县", "商务地址 省/市/自治区", "商务地址 邮政编码", "商务地址 国家/地区",
                    "住宅地址 街道", "住宅地址 街道 2", "住宅地址 街道 3", "住宅地址 市/县", "住宅地址 省/市/自治区",
                    "住宅地址 邮政编码", "住宅地址 国家/地区", "其他地址 街道", "其他地址 街道 2", "其他地址 街道 3",
                    "其他地址 市/县", "其他地址 省/市/自治区", "其他地址 邮政编码", "其他地址 国家/地区", "助理的电话",
                    "商务传真", "商务电话", "商务电话 2", "回电话", "车载电话", "单位主要电话", "住宅传真", "住宅电话",
                    "住宅电话 2", "ISDN", "移动电话", "其他传真", "其他电话", "寻呼机", "主要电话", "无绳电话",
                    "TTY/TDD 电话", "电报", "Internet 忙闲", "办公地点", "地点", "电子邮件地址", "电子邮件类型",
                    "电子邮件显示名称", "电子邮件 2 地址", "电子邮件 2 类型", "电子邮件 2 显示名", "电子邮件 3 地址",
                    "电子邮件 3 类型", "电子邮件 3 显示名", "附注", "工作证号码", "关键词", "记帐信息", "纪念日",
                    "经理姓名", "类别", "里程", "敏感度", "目录服务器", "配偶", "其他地址 - 邮箱", "商务地址 - 邮箱",
                    "身份证编号", "生日", "私有", "缩写", "网页", "性别", "业余爱好", "引用者", "用户 1", "用户 2",
                    "用户 3", "用户 4", "优先级", "语言", "帐户", "职业", "住宅地址 - 邮箱", "助理的姓名", "子女"};

    private static final String[] CSV_TITLE_ENGLISH = new String[]
            {"Title", "First Name", "Middle Name", "Last Name", "Suffix", "Company", "Department", "Job Title",
                    "Business Street", "Business Street 2", "Business Street 3", "Business City", "Business State",
                    "Business Postal Code", "Business Country/Region", "Home Street", "Home Street 2",
                    "Home Street 3", "Home City", "Home State", "Home Postal Code", "Home Country/Region",
                    "Other Street", "Other Street 2", "Other Street 3", "Other City", "Other State",
                    "Other Postal Code", "Other Country/Region", "Assistant's Phone", "Business Fax",
                    "Business Phone", "Business Phone 2", "Callback", "Car Phone", "Company Main Phone",
                    "Home Fax", "Home Phone", "Home Phone 2", "ISDN", "Mobile Phone", "Other Fax", "Other Phone",
                    "Pager", "Primary Phone", "Radio Phone", "TTY/TDD Phone", "Telex", "Account", "Anniversary",
                    "Assistant's Name", "Billing Information", "Birthday", "Business Address PO Box", "Categories",
                    "Children", "Company Yomi", "Directory Server", "E-mail Address", "E-mail Type",
                    "E-mail Display Name", "E-mail 2 Address", "E-mail 2 Type", "E-mail 2 Display Name",
                    "E-mail 3 Address", "E-mail 3 Type", "E-mail 3 Display Name", "Gender", "Given Yomi",
                    "Government ID Number", "Hobby", "Home Address PO Box", "Initials", "Internet Free Busy",
                    "Keywords", "Language", "Location", "Manager's Name", "Mileage", "Notes", "Office Location",
                    "Organizational ID Number", "Other Address PO Box", "Priority", "Private", "Profession",
                    "Referred By", "Sensitivity", "Spouse", "Surname Yomi", "User 1", "User 2", "User 3",
                    "User 4", "Web Page"};


    @Override
    public List<String[]> generator() {
        int maxCsvSize = 21;
        if (csvSize > maxCsvSize) {
            throw new RuntimeException("CSV_SIZE must less 21");
        }
        ContactName firstContact = csv.getFirst();
        ContactName lastContact = csv.getLast();
        String preNumber = csv.getEntity().getPreNumber();
        int contactSize = csv.getContactSize();
        CharEnum language = csv.getType();
        List<String[]> contacts = new ArrayList<>();
        for (int i = 0; i < contactSize; i++) {
            String first = genName(firstContact, i, contactSize);
            String last = genName(lastContact, i, contactSize);
            String[] numbers = genNumberSize(preNumber, csvSize);
            contacts.add(genCsvContact(first, last, numbers, language));
        }
        return contacts;
    }


    private String[] genCsvContact(String first, String last, String[] numbers, CharEnum type) {
        log.debug("first [{}], last[{}], number Size[{}] type[{}]", first, last, numbers.length, type);
        String[] strings;
        switch (type) {
            case CHINESE:
                strings = new String[CSV_TITLE_CHINESE.length];
                break;
            case ENGLISH:
                strings = new String[CSV_TITLE_ENGLISH.length];
                break;
            default:
                throw new RuntimeException("type " + type.getValue() + " is incorrect");
        }
        strings[3] = first;
        strings[1] = last;
        System.arraycopy(numbers, 0, strings, 29, numbers.length);
        return strings;
    }
}
