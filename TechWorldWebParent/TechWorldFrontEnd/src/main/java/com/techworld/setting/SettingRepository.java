package com.techworld.setting;

import com.techworld.common.entity.Setting;
import com.techworld.common.entity.SettingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, String> {
    public List<Setting> findByCategory(SettingCategory category);

    @Query("select s from Setting s where s.category = ?1")
    public List<Setting> findByCategories(SettingCategory catOne);

    public Setting findByKey(String key);
}
