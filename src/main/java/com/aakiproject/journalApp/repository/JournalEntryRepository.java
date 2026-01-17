package com.aakiproject.journalApp.repository;

import com.aakiproject.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    boolean existsByTitle(String title);
}
