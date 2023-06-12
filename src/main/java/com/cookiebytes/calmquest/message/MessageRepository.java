package com.cookiebytes.calmquest.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

    public Message getMessageByMessageCode(String messageCode);

}
