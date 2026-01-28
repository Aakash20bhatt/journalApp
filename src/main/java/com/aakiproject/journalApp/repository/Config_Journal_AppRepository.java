package com.aakiproject.journalApp.repository;

import com.aakiproject.journalApp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface Config_Journal_AppRepository extends MongoRepository<ConfigJournalAppEntity,ObjectId> {

}
