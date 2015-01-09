package net.devopssolutions.microservice.client.controller;

import net.devopssolutions.microservice.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class WsController {

    Logger logger = LoggerFactory.getLogger(WsController.class);

    @Autowired
    private UserService userService;

//    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/user")
    @SendTo("/broker/users")
    public Map<String, Object> sendUser(Map<String, String> user, WebSocketSession session) throws Exception {
        logger.info("send user");
        Map<String, Object> map = new HashMap<>();
        map.put("date", DateTimeFormat.fullDateTime().print(new DateTime()));
        map.put("session", session);
        map.put("user", userService.getUserByName(user.get("name")));

        return map;
    }

//    @Scheduled(fixedDelay = 5000)
//    @Async
//    public void publishUsers() {
//        logger.debug("publishUsers");
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("date", DateTimeFormat.fullDateTime().print(new DateTime()));
//        messagingTemplate.convertAndSend("/broker/userList", map);
//    }

}
