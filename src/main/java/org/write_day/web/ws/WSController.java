package org.write_day.web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
public class WSController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private Logger logger = Logger.getLogger(WSController.class.getName());

    @MessageMapping("/help")
    @SendTo("/topic/help")
    public String greeting(String message, Principal principal) throws Exception {
        logger.info("Sending message");
        logger.info(message);
        return ("Hello, " + principal.getName() + "!" + " Your message: " + message);
    }

}