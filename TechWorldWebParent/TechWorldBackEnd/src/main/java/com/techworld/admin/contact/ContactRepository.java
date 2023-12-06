package com.techworld.admin.contact;

import com.techworld.common.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Feed, Integer> {

    @Query("select f from Feed f order by f.sentTime desc")
    public List<Feed> listAllBySentTime();
    
}
