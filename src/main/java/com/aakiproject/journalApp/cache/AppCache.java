package com.aakiproject.journalApp.cache;

import com.aakiproject.journalApp.entity.ConfigJournalAppEntity;
import com.aakiproject.journalApp.repository.Config_Journal_AppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private Config_Journal_AppRepository config_Journal_AppRepository;

    public Map<String,String> AppCache;

    @PostConstruct
    public void init(){
        AppCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = config_Journal_AppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            AppCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());

        }
    }
}
