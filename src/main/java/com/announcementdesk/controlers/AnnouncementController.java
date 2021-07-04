package com.announcementdesk.controlers;

import com.announcementdesk.domain.Announcement;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    private AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }




    @GetMapping
    public String main(Map<String, Object> model){

        Iterable<Announcement> announcements = announcementRepository.findAll();
        model.put("announcements", announcements);
        return "main";
    }

    @GetMapping("/add")
    public String addAnnouncement(Map<String, Object> model){
        return "addAnnouncement";
    }


    @PostMapping("/add")
    public String addAnnouncement(
            @AuthenticationPrincipal User user,
            @RequestParam String topic,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model ){
        Announcement announcement = new Announcement(topic, text, tag, user);
        announcementRepository.save(announcement);

        return "redirect:";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filterTag, Map<String, Object> model){
        List<Announcement> announcements = announcementRepository.findByTag(filterTag);

        model.put("announcements", announcements);

        return "main";
    }
}
