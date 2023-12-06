package com.techworld.contact;

import com.techworld.common.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Feed, Integer> {

}
