package com.aakiproject.journalApp.controller;

import com.aakiproject.journalApp.entity.JournalEntry;
import com.aakiproject.journalApp.entity.User;
import com.aakiproject.journalApp.services.JournalEntryServices;
import com.aakiproject.journalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public ResponseEntity<?>  getAllJournalEntriesOfUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userServices.findByUserName(userName) ;
        List<JournalEntry> all = user.getJournalEntries();
        if(all!= null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userServices.findByUserName(userName) ;
        myEntry.setDate(LocalDateTime.now());
        journalEntryServices.saveEntry(myEntry,userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(journalEntryServices.getAll());
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userServices.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryServices.findById(id);
            if(journalEntry.isPresent()) {
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle():oldEntry.getTitle() );
                oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent():oldEntry.getContent() );
                oldEntry.setDate(LocalDateTime.now());
                journalEntryServices.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryServices.deleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userServices.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> byId = journalEntryServices.findById(myId);
            if(byId.isPresent()) {
                return new ResponseEntity<>(byId.get(), HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
