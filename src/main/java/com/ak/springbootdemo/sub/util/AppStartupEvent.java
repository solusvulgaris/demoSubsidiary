package com.ak.springbootdemo.sub.util;

import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.data.SubsidiaryRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application Startup Event
 */
@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = Logger.getLogger(AppStartupEvent.class.getName());

    private final SubsidiaryRepository subsidiaryRepository;

    public AppStartupEvent(SubsidiaryRepository subsidiaryRepository) {
        this.subsidiaryRepository = subsidiaryRepository;
    }

    /**
     * On Startup Message
     *
     * @param event application startup event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Subsidiary> subsidiaries = this.subsidiaryRepository.findAll();
        logger.log(Level.INFO, "<-------    DEMO Subsidiaries Application HAS STARTED    ------->");
        logger.log(Level.INFO, "<-------    HERE is the List of all Subsidiaries existing in the DB:");
        subsidiaries.forEach(s -> logger.log(Level.INFO, s.toString()));
    }

}
