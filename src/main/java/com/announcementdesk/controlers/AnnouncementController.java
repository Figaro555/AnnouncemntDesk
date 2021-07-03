package com.announcementdesk.controlers;

import com.announcementdesk.domain.Announcement;
import com.announcementdesk.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AnnouncementController {

    private AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    /*@GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String,Object> model) {
        model.put("name", name);
        return "start";
    }*/

    @GetMapping("/announcement")
    public String main(Map<String, Object> model){

        Iterable<Announcement> announcements = announcementRepository.findAll();
        model.put("announcements", announcements);
        return "main";
    }

    @GetMapping("/announcement/add")
    public String adding(Map<String, Object> model){
        return "addAnnouncement";
    }


    @PostMapping("/announcement/add")
    public String add(@RequestParam String topic, @RequestParam String text, @RequestParam String tag,Map<String, Object> model ){
        Announcement announcement = new Announcement(topic, text, tag);
        announcementRepository.save(announcement);

        Iterable<Announcement> announcements = announcementRepository.findAll();
        model.put("announcements", announcements);

        return "main";
    }

    @PostMapping("announcement/filter")
    public String filter(@RequestParam String filterTag, Map<String, Object> model){
        List<Announcement> announcements = announcementRepository.findByTag(filterTag);

        model.put("announcements", announcements);

        return "main";
    }
}
