package co.instil.userlogin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {
    @RequestMapping(path = "/user", method = GET)
    public Principal getUser(Principal user) {
        return user;
    }
}