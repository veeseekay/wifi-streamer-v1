package com.guidestone.wifi.streamer.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.guidestone.wifi.streamer.entities.MediaEntity;
import com.guidestone.wifi.streamer.model.MediaUpload;
import com.guidestone.wifi.streamer.services.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    @Autowired
    MediaService mediaService;

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String uploadMedia(@ModelAttribute("uploadForm") MediaUpload uploadForm,
            Model model){

        List<MediaEntity> mediaEntities = new ArrayList<>();
        List<MultipartFile> crunchifyFiles = uploadForm.getFiles();
        List<String> radios = uploadForm.getRadios();
        List<String> genre = uploadForm.getGenre();
        List<String> language = uploadForm.getLanguage();

        LOG.info("size {}", crunchifyFiles.size());

        int i = 0;

        if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
            for (MultipartFile file : crunchifyFiles) {

                String fileName = file.getOriginalFilename();
                LOG.info("filename {}", fileName);
                LOG.info("language {}", language.get(i));
                LOG.info("genre {}", genre.get(i));

                if (!file.isEmpty()) {
                    InputStream fileInputStream = null;
                    try {

                        MediaEntity mediaEntity = new MediaEntity();
                        mediaEntity.setMediaGenre(language.get(i));
                        mediaEntity.setMediaGenre(genre.get(i));
                        mediaEntity.setMediaLocation(bucket + "/" + radios.get(i) + "/" + file.getOriginalFilename());
                        mediaEntity.setTitle(file.getOriginalFilename());
                        mediaEntity.setMediaCategory(radios.get(i));

                        //fileInputStream = file.getInputStream();
                       // TransferManager transferManager = new TransferManager(this.amazonS3);
                        //Upload upload = transferManager.upload(bucket, radios.get(i) + "/" + file.getOriginalFilename(), fileInputStream, null);
                       // LOG.info(upload.waitForUploadResult().getBucketName());


                        mediaEntities.add(mediaEntity);
                        LOG.info("successfully uploaded " + file.getName() + "!");

                    } catch (Exception e) {
                        LOG.info("failed to upload " + file.getName() + " => " + e.getMessage());
                    } finally {
                        try {
                            if(null != fileInputStream) {
                                fileInputStream.close();
                            }
                        } catch (IOException e) {
                            LOG.info("failed to close input stream ");
                        }
                    }
                }
            }
            i++;
            mediaService.addMedia(mediaEntities);
        }
        return "landing";
    }
}