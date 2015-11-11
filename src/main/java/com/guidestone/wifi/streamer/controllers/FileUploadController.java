package com.guidestone.wifi.streamer.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.guidestone.wifi.streamer.model.MediaUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class FileUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private AmazonS3 amazonS3;

    private @Value("${s3.bucket}")
    String bucket;

    @RequestMapping(value="/api/upload", method=RequestMethod.POST)
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

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String uploadMedia(@ModelAttribute("uploadForm") MediaUpload uploadForm,
            Model model){

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

                        fileInputStream = file.getInputStream();
                        TransferManager transferManager = new TransferManager(this.amazonS3);
                        Upload upload = transferManager.upload(bucket, radios.get(i) + "/" + file.getOriginalFilename(), fileInputStream, null);
                        LOG.info(upload.waitForUploadResult().getBucketName());

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
        }
        return "landing";
    }
}