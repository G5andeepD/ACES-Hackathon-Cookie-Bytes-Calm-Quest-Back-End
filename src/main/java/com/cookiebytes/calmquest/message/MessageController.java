package com.cookiebytes.calmquest.message;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/{messageCode}")
    public String getMessageByCode(@PathVariable String messageCode){
        return messageRepository.getMessageByMessageCode(messageCode).getMessage();
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message){
        return messageRepository.save(message);
    }
}
