package com.guidestone.wifi.streamer.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadPartRequest;
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
                InitiateMultipartUploadRequest initRequest = null;
                InitiateMultipartUploadResult initResponse = null;

                try {

                    MediaEntity mediaEntity = new MediaEntity();
                    if ("tv".equalsIgnoreCase(radios.get(0))) {
                        mediaEntity.setMediaGenre(tv);
                    } else if ("movies".equalsIgnoreCase(radios.get(0))) {
                        mediaEntity.setMediaGenre(movies);
                    } else {
                        mediaEntity.setMediaGenre(videos);
                    }

                    LOG.info("selected radio {}", radios.get(0));

                    mediaEntity.setMediaLocation(bucket + "/" + radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename());
                    if(crunchifyFiles.get(1) != null && !crunchifyFiles.get(1).isEmpty()) {
                        LOG.info("cover image {}", crunchifyFiles.get(1).getOriginalFilename());
                        mediaEntity.setCoverImageLocation(bucket + "/" + radios.get(0) + "/" + crunchifyFiles.get(1).getOriginalFilename());
                    }
                    mediaEntity.setTitle(crunchifyFiles.get(0).getOriginalFilename());
                    mediaEntity.setMediaCategory(radios.get(0));

                    // Step 0: Create a list of UploadPartResponse objects. You get one of these
                    // for each part upload.
                    List<PartETag> partETags = new ArrayList<>();

                    long contentLength = crunchifyFiles.get(0).getSize();
                    LOG.info("File {} has length {} bytes", crunchifyFiles.get(0).getOriginalFilename(), crunchifyFiles.get(0).getSize());

                    long partSize = 5242880; // Set part size to 5 MB.

                    // Step 1: Initialize.
                    initRequest = new
                            InitiateMultipartUploadRequest(bucket, radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename());
                    initResponse =
                            amazonS3.initiateMultipartUpload(initRequest);


                    // Step 2: Upload parts.
                    long filePosition = 0;
                    for (int i = 1; filePosition < contentLength; i++) {
                        // Last part can be less than 5 MB. Adjust part size.
                        partSize = Math.min(partSize, (contentLength - filePosition));

                        // Create request to upload a part.
                        UploadPartRequest uploadRequest = new UploadPartRequest()
                                .withBucketName(bucket).withKey(radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename())
                                .withUploadId(initResponse.getUploadId()).withPartNumber(i)
                                .withFileOffset(filePosition)
                                .withInputStream(crunchifyFiles.get(0).getInputStream())
                                .withPartSize(partSize);

                        PartETag partETag = amazonS3.uploadPart(uploadRequest).getPartETag();

                        // Upload part and add response to our list.
                        partETags.add(partETag);

                        LOG.info("partETags {}", partETag.getPartNumber());

                        filePosition += partSize;
                    }

                    // Step 3: Complete.
                    CompleteMultipartUploadRequest compRequest = new
                            CompleteMultipartUploadRequest(
                            bucket,
                            radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename(),
                            initResponse.getUploadId(),
                            partETags);

                    CompleteMultipartUploadResult result = amazonS3.completeMultipartUpload(compRequest);

                    // Step 4: add to DB
                    mediaEntities.add(mediaEntity);

                    // Step 5: make media public
                    amazonS3.setObjectAcl(bucket, radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename(), CannedAccessControlList.PublicRead);

                    // Step 6: upload media cover image
                    TransferManager transferManager = new TransferManager(this.amazonS3);

                    fileInputStream = crunchifyFiles.get(1).getInputStream();
                    Upload upload = transferManager.upload(
                            new PutObjectRequest(bucket, radios.get(0) + "/" + crunchifyFiles.get(1).getOriginalFilename(), fileInputStream, null)
                                    .withCannedAcl(CannedAccessControlList.PublicRead));
                    LOG.info(upload.waitForUploadResult().getBucketName());


                    LOG.info("successfully uploaded " + crunchifyFiles.get(0).getName() + "with id = " + result.getLocation());

                } catch (Exception e) {
                    LOG.info("failed to upload " + crunchifyFiles.get(0).getName() + " => " + e.getMessage());
                    amazonS3.abortMultipartUpload(new AbortMultipartUploadRequest(
                            bucket, radios.get(0) + "/" + crunchifyFiles.get(0).getOriginalFilename(), initResponse.getUploadId()));
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