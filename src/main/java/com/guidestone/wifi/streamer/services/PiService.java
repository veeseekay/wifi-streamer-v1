package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.PiEntity;
import com.guidestone.wifi.streamer.exceptions.ExceptionType;
import com.guidestone.wifi.streamer.exceptions.StreamerException;
import com.guidestone.wifi.streamer.repositories.PiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PiService {

    private static final Logger LOG = LoggerFactory.getLogger(PiService.class);

    @Autowired
    private PiRepository piRepository;

    private void mergeAttributes(PiEntity pi, PiEntity piEntity) {
        if(piEntity != null) {

            if(pi.getDownloads() == null) {
                pi.setDownloads(piEntity.getDownloads());
            } else {
                if(piEntity.getDownloads() != null) {
                    pi.setDownloads(pi.getDownloads() + piEntity.getDownloads());
                }
            }
            if(pi.getActive() == null) pi.setActive(piEntity.getActive());
            if(pi.getLastChecked() == null) pi.setLastChecked(piEntity.getLastChecked());
            if(pi.getAddedOn() == null) pi.setAddedOn(piEntity.getAddedOn());
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
}
