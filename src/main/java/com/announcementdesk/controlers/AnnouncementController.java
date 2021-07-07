package com.announcementdesk.controlers;

import com.announcementdesk.domain.Announcement;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    private AnnouncementRepository announcementRepository;

    @Value("${upload.path}")
    private String uploadPath;


    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }





    @GetMapping
    public String main(Model model){

        Iterable<Announcement> announcements = announcementRepository.findAll();
        model.addAttribute("announcements", announcements);
        return "main";
    }

    @GetMapping("/add")
    public String addAnnouncement(Model model){
        model.addAttribute("announcement", new Announcement());
        return "addAnnouncement";
    }


    @PostMapping("/add")
    public String addAnnouncement(
            @AuthenticationPrincipal User user,
            @ModelAttribute("announcement") Announcement announcement,
            @RequestParam("file") MultipartFile file) throws IOException {

        if(file!= null){
            File uploadDir  = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();

            String fileUUID = UUID.randomUUID().toString();
            String resultFileName = fileUUID + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            announcement.setFilename(resultFileName);
        }
        announcement.setAuthor(user);
        announcementRepository.save(announcement);
        return "redirect:";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filterTag, Map<String, Object> model){
        List<Announcement> announcements = announcementRepository.findByTag(filterTag);

        model.put("announcements", announcements);

        return "main";
    }

    @GetMapping("/myannouncements")
    public String getMyAnnouncements(@AuthenticationPrincipal User user, Model model){
        List<Announcement> userAnnouncements = announcementRepository.findByAuthor(user);
        model.addAttribute("announcements", userAnnouncements);
        return "myAnnouncements";
    }
}
