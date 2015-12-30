package com.mhchoudh.appeal.repositories;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.AppealFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppealFormatRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealFormatRepository.class);

    private static final AppealFormatRepository theRepository = new AppealFormatRepository();
    private HashMap<String, AppealFormat> backingStore = new HashMap<>(); // Default implementation, not suitable for production!

    public static AppealFormatRepository current() {
        return theRepository;
    }
    
    private AppealFormatRepository(){
        LOG.debug("SendRepository Constructor");
    }
    
    public AppealFormat get(Identifier identifier) {
        LOG.debug("Retrieving Send object for identifier {}", identifier);
        return backingStore.get(identifier.toString());
    }
    
    public AppealFormat take(Identifier identifier) {
        LOG.debug("Removing the Send object for identifier {}", identifier);
        AppealFormat send = backingStore.get(identifier.toString());
        remove(identifier);
        return send;
    }

    public Identifier store(AppealFormat send) {
        LOG.debug("Storing a new Send object");
        
        Identifier id = new Identifier();
        LOG.debug("New Send object's id is {}", id);
        
        backingStore.put(id.toString(), send);
        return id;
    }
    
    public void store(Identifier identifier, AppealFormat send) {
        LOG.debug("Storing again the Appeal object with id", identifier);
        backingStore.put(identifier.toString(), send);
    }

    public boolean has(Identifier identifier) {
        LOG.debug("Checking to see if there is a Send object associated with the id {} in the Send store", identifier);
        
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        LOG.debug("Removing from storage the Send object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, AppealFormat> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<>();
    }
}
