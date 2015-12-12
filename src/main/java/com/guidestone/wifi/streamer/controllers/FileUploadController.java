package com.guidestone.wifi.streamer.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.guidestone.wifi.streamer.entities.MediaEntity;
import com.guidestone.wifi.streamer.model.MediaUpload;
import com.guidestone.wifi.streamer.services.JsonEntryService;
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

    @Autowired
    JsonEntryService jsonEntryService;

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String uploadMedia(@ModelAttribute("uploadForm") MediaUpload uploadForm,
            Model model){

        List<MediaEntity> mediaEntities = new ArrayList<>();
        List<MultipartFile> crunchifyFiles = uploadForm.getFiles();
        List<String> radios = uploadForm.getRadios();
        String movies = uploadForm.getMovies();
        String tv = uploadForm.getTv();
        String videos = uploadForm.getVideos();

        LOG.info("size {}", crunchifyFiles.size());
        LOG.info("mov {}, tv {}, vid  {}", movies, tv, videos);

        int i = 0;

        if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
            for (MultipartFile file : crunchifyFiles) {

                String fileName = file.getOriginalFilename();
                LOG.info("filename {}", fileName);

                if (!file.isEmpty()) {
                    InputStream fileInputStream = null;
                    try {

                        MediaEntity mediaEntity = new MediaEntity();
                        if("tv".equalsIgnoreCase(radios.get(i))) {
                            mediaEntity.setMediaGenre(tv);
                        } else if("movies".equalsIgnoreCase(radios.get(i))){
                            mediaEntity.setMediaGenre(movies);
                        } else {
                            mediaEntity.setMediaGenre(videos);
                        }
                        mediaEntity.setMediaLocation(bucket + "/" + radios.get(i) + "/" + file.getOriginalFilename());
                        mediaEntity.setTitle(file.getOriginalFilename());
                        mediaEntity.setMediaCategory(radios.get(i));

                        fileInputStream = file.getInputStream();
                        TransferManager transferManager = new TransferManager(this.amazonS3);
                        //Upload upload = transferManager.upload(bucket, radios.get(i) + "/" + file.getOriginalFilename(), fileInputStream, null);

                        Upload upload = transferManager.upload(
                                new PutObjectRequest(bucket, radios.get(i) + "/" + file.getOriginalFilename(), fileInputStream, null)
                                        .withCannedAcl(CannedAccessControlList.PublicRead));
                        LOG.info(upload.waitForUploadResult().getBucketName());

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
            LOG.info("calling async");
            jsonEntryService.updateMediaJson();
        }
        return "landing";
    }
}