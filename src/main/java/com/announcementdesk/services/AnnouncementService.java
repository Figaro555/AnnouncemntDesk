package com.announcementdesk.services;


import com.announcementdesk.domain.Announcement;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementService {

    @Value("${upload.path}")
    private String uploadPath;

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository){
        this.announcementRepository = announcementRepository;
    }

    public Iterable<Announcement> findAll(){
        Iterable<Announcement> announcements = announcementRepository.findAll();
        return  announcements;
    }

     public void addAnnouncement(Announcement announcement, User author, MultipartFile file) throws IOException {
         if(file!= null && file.getOriginalFilename().length()!=0){
             File uploadDir  = new File(uploadPath);
             if(!uploadDir.exists())
                 uploadDir.mkdir();

             String fileUUID = UUID.randomUUID().toString();
             String resultFileName = fileUUID + "_" + file.getOriginalFilename();
             file.transferTo(new File(uploadPath + "/" + resultFileName));
             announcement.setFilename(resultFileName);
         }
         announcement.setAuthor(author);
         announcementRepository.save(announcement);

    }

    public List<Announcement> findByAuthor(User author){
        List<Announcement> userAnnouncements = announcementRepository.findByAuthor(author);
        return userAnnouncements;
    }

    public void delete(Announcement announcement){
        announcementRepository.delete(announcement);
    }

    public void setValues(Announcement announcement, String Topic, String Text, String Tag){
        announcement.setTopic(Topic);
        announcement.setText(Text);
        announcement.setTag(Tag);

        announcementRepository.save(announcement);
    }



}
