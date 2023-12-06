package com.techworld.admin.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingBag;

import java.util.List;

public class GeneralSettingBag extends SettingBag {

    public GeneralSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public void updateSiteLogo(String value){
        super.update("SITE_LOGO",value);
    }
}
