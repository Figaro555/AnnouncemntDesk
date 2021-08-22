package com.announcementdesk.services;


import com.announcementdesk.domain.Announcement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileManager {

    @Value("${upload.path}")
    private String uploadPath;

    public boolean addFile(Announcement announcement, MultipartFile file) throws IOException {
        if(file!= null && file.getOriginalFilename().length()!=0){
            File uploadDir  = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();

            String fileUUID = UUID.randomUUID().toString();
            String resultFileName = fileUUID + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            announcement.setFilename(resultFileName);
        }


        return false;
    }

}
