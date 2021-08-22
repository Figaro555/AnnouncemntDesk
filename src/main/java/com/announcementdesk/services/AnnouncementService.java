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


    private final AnnouncementRepository announcementRepository;
    private final FileLoadService fileManager;



    public AnnouncementService(AnnouncementRepository announcementRepository, FileLoadService fileManager){
        this.announcementRepository = announcementRepository;
        this.fileManager = fileManager;
    }



    public Iterable<Announcement> findAll(){
        Iterable<Announcement> announcements = announcementRepository.findAll();
        return  announcements;
    }

    public Iterable<Announcement> findByFilter(String filter){
        Iterable<Announcement> announcements;
        if(filter.equals("")){
            announcements = findAll();
        }else
            announcements = announcementRepository.findByTopic(filter);
        return  announcements;
    }

     public void addAnnouncement(Announcement announcement, User author, MultipartFile file) throws IOException {

        fileManager.addFile(announcement, file);
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
