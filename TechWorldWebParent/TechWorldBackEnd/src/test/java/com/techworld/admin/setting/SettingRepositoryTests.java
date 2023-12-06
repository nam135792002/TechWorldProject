package com.techworld.admin.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class SettingRepositoryTests {
    @Autowired SettingRepository settingRepository;

    @Test
    public void testCreateGeneralSettings(){
//        Setting siteName = new Setting("SITE_NAME","Tech World", SettingCategory.GENERAL);
//        Setting siteLogo = new Setting("SITE_LOGO","TechWorld.png", SettingCategory.GENERAL);
//        Setting copyright = new Setting("COPYRIGHT","Copyright (c) 2023 Tech World Ltd.", SettingCategory.GENERAL);
//        settingRepository.saveAll(List.of(siteName, siteLogo,copyright));

        Setting setting_1 = new Setting("MAIL_HOST","smtp.gmail.com",SettingCategory.MAIL_SERVER);
        Setting setting_2 = new Setting("MAIL_PORT","123",SettingCategory.MAIL_SERVER);
        Setting setting_3 = new Setting("MAIL_USERNAME","username",SettingCategory.MAIL_SERVER);
        Setting setting_4 = new Setting("MAIL_PASSWORD","password",SettingCategory.MAIL_SERVER);
        Setting setting_5 = new Setting("MAIL_FORM","phuongnama32112002@gmail.com",SettingCategory.MAIL_SERVER);
        Setting setting_6 = new Setting("SMTP_AUTH","true",SettingCategory.MAIL_SERVER);
        Setting setting_7 = new Setting("SMTP_SECURED","true",SettingCategory.MAIL_SERVER);
        Setting setting_8 = new Setting("MAIL_SENDER_NAME","Tech World Team",SettingCategory.MAIL_SERVER);
        Setting setting_9 = new Setting("CUSTOMER_VERIFY_CONTENT","Email content",SettingCategory.MAIL_TEMPLATES);
        Setting setting_10 = new Setting("CUSTOMER_VERIFY_SUBJECT","Email subject",SettingCategory.MAIL_TEMPLATES);
//
        Setting setting_11 = new Setting("ORDER_CONFIRMATION_SUBJECT","Email content",SettingCategory.MAIL_TEMPLATES);
        Setting setting_12 = new Setting("ORDER_CONFIRMATION_CONTENT","Email subject",SettingCategory.MAIL_TEMPLATES);

        Setting setting_14 = new Setting("VNPay_TERMINAL_ID","def",SettingCategory.PAYMENT);
        Setting setting_15 = new Setting("VNPay_SECRET_KEY","def",SettingCategory.PAYMENT);
        Setting setting_16 = new Setting("VNPay_URL_TEST","def",SettingCategory.PAYMENT);

        settingRepository.saveAll(List.of(setting_16, setting_14, setting_15));
    }

    @Test
    public void testListSettingByCategory(){
        List<Setting> settings = new ArrayList<>();

        List<Setting> generalSettings = settingRepository.findByCategory(SettingCategory.GENERAL);

        settings.addAll(generalSettings);
        for(Setting setting : settings){
            System.out.println(setting.getKey());
        }
    }
}
