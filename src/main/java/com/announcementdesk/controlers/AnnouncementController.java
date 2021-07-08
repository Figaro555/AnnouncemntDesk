package com.announcementdesk.controlers;

import com.announcementdesk.domain.Announcement;
import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.AnnouncementRepository;
import com.announcementdesk.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }


    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping
    public String main(Model model){

        Iterable<Announcement> announcements = announcementService.findAll();
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
            @ModelAttribute("announcement") @Valid Announcement announcement,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file) throws IOException {

        if(bindingResult.hasErrors()){
            return "addAnnouncement";
        }

        announcementService.addAnnouncement(announcement, user, file);


        return "redirect:";
    }

//    @PostMapping("/filter")
//    public String filter(@RequestParam String filterTag, Map<String, Object> model){
//        List<Announcement> announcements = announcementRepository.findByTag(filterTag);
//
//        model.put("announcements", announcements);
//
//        return "main";
//    }

    @GetMapping("/myannouncements")
    public String getMyAnnouncements(@AuthenticationPrincipal User user, Model model){
        List<Announcement> userAnnouncements = announcementService.findByAuthor(user);
        model.addAttribute("announcements", userAnnouncements);
        return "myAnnouncements";
    }

    @DeleteMapping("/{announcementId}")
    public String deleteAnnouncement(@AuthenticationPrincipal User user,
                                      @PathVariable("announcementId") Announcement announcement){

        if(user.equals(announcement.getAuthor())){
            announcementService.delete(announcement);
        }
        return "redirect:myannouncements";
    }


    @PatchMapping("/{announcementId}")
    public String editAnnouncement(@AuthenticationPrincipal User user,
                                   @PathVariable("announcementId") Announcement announcement,
                                   @ModelAttribute("announcement") @Valid Announcement newAnnouncement,
                                   BindingResult bindingResult){

        if(user.equals(announcement.getAuthor())){
            if(bindingResult.hasErrors()){
                return "addAnnouncement";
            }

            announcementService.setValues(announcement,
                    newAnnouncement.getTopic(),
                    newAnnouncement.getText(),
                    newAnnouncement.getTag());

        }

        return "redirect:myannouncements";
    }
    @GetMapping("/{announcementId}/edit")
    public String editAnnouncementPage(
                                   @PathVariable("announcementId") Announcement announcement,
                                   Model model){
        model.addAttribute("announcement", announcement);
        return "editAnnouncement";
    }


}
