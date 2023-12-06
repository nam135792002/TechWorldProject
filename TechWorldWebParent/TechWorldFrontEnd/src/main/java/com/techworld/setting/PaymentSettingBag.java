package com.techworld.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingBag;

import java.util.List;

public class PaymentSettingBag extends SettingBag {
    public PaymentSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getUrl(){
        return super.getValue("VNPay_URL_TEST");
    }

    public String getClientID(){
        return super.getValue("VNPay_TERMINAL_ID");
    }

    public String getClientSecret(){
        return super.getValue("VNPay_SECRET_KEY");
    }
}
