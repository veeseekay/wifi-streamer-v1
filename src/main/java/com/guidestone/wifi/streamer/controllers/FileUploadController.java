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

        if (null != crunchifyFiles && crunchifyFiles.get(0) != null) {

            String fileName = crunchifyFiles.get(0).getOriginalFilename();
            LOG.info("filename {}", fileName);

            if (!crunchifyFiles.get(0).isEmpty()) {
                InputStream fileInputStream = null;
                try {

                    MediaEntity mediaEntity = new MediaEntity();
                    if ("tv".equalsIgnoreCase(radios.get(0))) {
                        mediaEntity.setMediaGenre(tv);
                    } else if ("movies".equalsIgnoreCase(radios.get(0))) {
                        mediaEntity.setMediaGenre(movies);
                    } else {
                        mediaEntity.setMediaGenre(videos);
                    }
                    mediaEntity.setMediaLocation(bucket + "/" + radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename());
                    if(crunchifyFiles.get(1) != null && !crunchifyFiles.get(1).isEmpty()) {
                        LOG.info("cover image {}", crunchifyFiles.get(1).getOriginalFilename());
                        mediaEntity.setCoverImageLocation(bucket + "/" + radios.get(0) + "/" + crunchifyFiles.get(1).getOriginalFilename());
                    }
                    mediaEntity.setTitle(crunchifyFiles.get(0).getOriginalFilename());
                    mediaEntity.setMediaCategory(radios.get(0));

                    fileInputStream = crunchifyFiles.get(0).getInputStream();
                    TransferManager transferManager = new TransferManager(this.amazonS3);

                    transferManager.upload(
                            new PutObjectRequest(bucket, radios.get(0) + "/" + crunchifyFiles.get(1).getOriginalFilename(), fileInputStream, null)
                                    .withCannedAcl(CannedAccessControlList.PublicRead));

                    Upload upload = transferManager.upload(
                            new PutObjectRequest(bucket, radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename(), fileInputStream, null)
                                    .withCannedAcl(CannedAccessControlList.PublicRead));
                    LOG.info(upload.waitForUploadResult().getBucketName());

                    mediaEntities.add(mediaEntity);
                    LOG.info("successfully uploaded " + crunchifyFiles.get(0).getName() + "!");

                } catch (Exception e) {
                    LOG.info("failed to upload " + crunchifyFiles.get(0).getName() + " => " + e.getMessage());
                } finally {
                    try {
                        if (null != fileInputStream) {
                            fileInputStream.close();
                        }
                    } catch (IOException e) {
                        LOG.info("failed to close input stream ");
                    }
                }
            }

            mediaService.addMedia(mediaEntities);
            LOG.info("calling async");
            jsonEntryService.updateMediaJson();
        }
        return "landing";
    }
}