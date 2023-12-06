package com.techworld.contact;

import com.techworld.common.entity.Feed;
import com.techworld.common.entity.FeedBackStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactService {

    @Autowired private ContactRepository contactRepository;

    public Feed save(Feed feed){
        feed.setSentTime(new Date());
        feed.setFeedBackStatus(FeedBackStatus.NEW);
        return contactRepository.save(feed);
    }
}
