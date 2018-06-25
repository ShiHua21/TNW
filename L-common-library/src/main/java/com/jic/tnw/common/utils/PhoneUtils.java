package com.jic.tnw.common.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.jic.tnw.common.exception.PhoneNumberIllegalException;

/**
 * Created by lee5hx on 2018/2/5.
 */
public class PhoneUtils {
    public static Boolean isMobilePhoneNo(String phoneNo, String country) {
        Boolean rt = true;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number;
        try {
            number = phoneUtil.parseAndKeepRawInput(phoneNo, country);
            PhoneNumberUtil.PhoneNumberType numberType = phoneUtil.getNumberType(number);
            if (!phoneUtil.isValidNumber(number) || !"MOBILE".equals(numberType.toString())) {
                rt = false;
            }
        } catch (Exception e) {
            rt = false;
        }
        return rt;
    }




    public static Phonenumber.PhoneNumber getPhoneNumber(String phoneNo, String country) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = null;
        try {
            number = phoneUtil.parseAndKeepRawInput(phoneNo, country);
            PhoneNumberUtil.PhoneNumberType numberType = phoneUtil.getNumberType(number);
            if (!phoneUtil.isValidNumber(number) || !"MOBILE".equals(numberType.toString())) {
                throw new PhoneNumberIllegalException();
            }
        } catch (NumberParseException e) {
            throw new PhoneNumberIllegalException();
        }
        return number;
    }
}
