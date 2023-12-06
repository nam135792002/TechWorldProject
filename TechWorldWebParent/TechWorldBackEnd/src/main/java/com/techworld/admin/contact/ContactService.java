package com.techworld.admin.contact;

import com.techworld.common.entity.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContactService {

    @Autowired private ContactRepository contactRepository;

    public List<Feed> listAll(){
        return contactRepository.listAllBySentTime();
    }

    public Feed get(Integer id) throws FeedNotFoundException {
        try {
            return contactRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new FeedNotFoundException("Could not find any feed back with ID " + id);
        }
    }

    public Feed savedFeed(Feed feed){
        return contactRepository.save(feed);
    }
}
