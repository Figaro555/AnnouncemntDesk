package com.announcementdesk.repositories;

import com.announcementdesk.domain.Announcement;
import com.announcementdesk.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    List<Announcement> findByTag(String tag);

    List<Announcement> findByTopic(String topic);

    List<Announcement> findByAuthor(User author);

}
