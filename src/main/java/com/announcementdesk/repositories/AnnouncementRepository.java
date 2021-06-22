package com.announcementdesk.repositories;

import com.announcementdesk.domain.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    public List<Announcement> findByTag(String tag);

}
