package com.techworld.admin.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, String> {
    public List<Setting> findByCategory(SettingCategory category);
}
