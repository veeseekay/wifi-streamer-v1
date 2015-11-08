package com.guidestone.wifi.streamer.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);


    @Autowired
    private AmazonS3 amazonS3;

    private @Value("${s3.bucket}")
    String bucket;

    @RequestMapping(value="/upload", method= RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("radios") String type,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();

                //ObjectListing objList = amazonS3.listObjects(bucket);

                /*for (S3ObjectSummary summary:objList.getObjectSummaries()) {
                    LOG.info("retrieving " + summary.getBucketName());
                    LOG.info("retrieving " + summary.getKey());
                }*/

                TransferManager transferManager = new TransferManager(this.amazonS3);
                Upload upload = transferManager.upload(bucket, type + "/" + file.getOriginalFilename(), new File(file.getOriginalFilename()));
                LOG.info(upload.waitForUploadResult().getBucketName());

                // delete
                new File(file.getOriginalFilename()).delete();

                return "You successfully uploaded " + file.getName() + "!";

            } catch (Exception e) {
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
    }
}