package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.PiEntity;
import com.guidestone.wifi.streamer.exceptions.ExceptionType;
import com.guidestone.wifi.streamer.exceptions.StreamerException;
import com.guidestone.wifi.streamer.repositories.PiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
public class PiService {

    private static final Logger LOG = LoggerFactory.getLogger(PiService.class);

    @Autowired
    private PiRepository piRepository;

    private void mergeAttributes(PiEntity pi, PiEntity piEntity) {
        if(piEntity != null) {
            if(pi.getDownloads() != null && piEntity.getDownloads() != null) {
                pi.setDownloads(pi.getDownloads() + piEntity.getDownloads());
            }
            if(pi.getDownloads() == null) {
                pi.setDownloads(piEntity.getDownloads());
            }
            if(pi.getActive() == null) pi.setActive(1);
            if(pi.getLastChecked() == null) pi.setLastChecked(new Timestamp(new Date().getTime()));
            if(pi.getAddedOn() == null) pi.setAddedOn(piEntity.getAddedOn());
        } else {
            // new pi
            pi.setAddedOn(new Timestamp(new Date().getTime()));
            pi.setActive(1);
            pi.setLastChecked(new Timestamp(new Date().getTime()));
        }
    }

    public Object addPis(List<PiEntity> pis) {
        for(PiEntity pi : pis) {
            PiEntity piEntity = piRepository.findByMacId(pi.getMacId());
            mergeAttributes(pi, piEntity);
        }
        return piRepository.save(pis);
    }

    public Object addPi(PiEntity pi) {

        PiEntity piEntity = piRepository.findByMacId(pi.getMacId());
        mergeAttributes(pi, piEntity);

        return piRepository.save(pi);
    }

    public List<PiEntity> getPis() {
        return piRepository.findAll();
    }

    public PiEntity getPi(String id) {
        PiEntity pi = piRepository.findByMacId(id);
        if(pi == null) {
            throw new StreamerException("Pi not found").setType(ExceptionType.NOT_FOUND);
        }
        return pi;
    }


    @Scheduled(cron = "${pi.cron.expression}")
    public void checkPiStatus() {
        LOG.info("Scheduled run for pi status check started");
        List<PiEntity> pis = piRepository.findAll();
        for(PiEntity pi : pis) {
            LOG.info("time stamp is {} = {}", pi.getLastChecked(), pi.getActive());
            if((new Date().getTime() - pi.getLastChecked().getTime()) > 86400000) {
                pi.setActive(0);
                piRepository.save(pi);
            }
        }
    }
}
