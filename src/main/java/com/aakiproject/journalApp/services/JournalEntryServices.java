package com.aakiproject.journalApp.services;

import com.aakiproject.journalApp.entity.JournalEntry;
import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.exception.DuplicateEntryException;
import com.aakiproject.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserServices userServices;

    @Transactional
    public void saveEntry(JournalEntry myEntry, String userName) {
        User user =  userServices.findByUserName(userName);
        if(journalEntryRepository.existsByTitle(myEntry.getTitle())) {
            throw new DuplicateEntryException("Journal entry already exists");
        }
        JournalEntry saved = journalEntryRepository.save(myEntry);
        user.getJournalEntries().add(saved);
        userServices.saveUser(user);

    }

    public void saveEntry(JournalEntry myEntry) {
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try{
            User user =  userServices.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userServices.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("An Error occurred while deleting the entry.",e);
        }
    }
}
