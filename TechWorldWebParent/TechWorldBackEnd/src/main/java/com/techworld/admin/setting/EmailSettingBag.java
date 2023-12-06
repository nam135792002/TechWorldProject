package com.techworld.admin.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingBag;

import java.util.List;

public class EmailSettingBag extends SettingBag {
    public EmailSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getHost(){
        return super.getValue("MAIL_HOST");
    }

    public String getUsername(){
        return super.getValue("MAIL_USERNAME");
    }

    public int getPort(){
        return Integer.parseInt(super.getValue("MAIL_PORT"));
    }

    public String getForm(){
        return super.getValue("MAIL_FORM");
    }

    public String getPassword(){
        return super.getValue("MAIL_PASSWORD");
    }

    public String getSenderName(){
        return super.getValue("MAIL_SENDER_NAME");
    }

    public String getAuth(){
        return super.getValue("SMTP_AUTH");
    }

    public String getSecured(){
        return super.getValue("SMTP_SECURED");
    }

    public String getCustomerVerifyContent(){
        return super.getValue("CUSTOMER_VERIFY_CONTENT");
    }

    public String getCustomerVerifySubject(){
        return super.getValue("CUSTOMER_VERIFY_SUBJECT");
    }

    public String getOrderConfirmationSubject(){
        return super.getValue("ORDER_CONFIRMATION_SUBJECT");
    }

    public String getOrderConfirmationContent(){
        return super.getValue("ORDER_CONFIRMATION_CONTENT");
    }
}
