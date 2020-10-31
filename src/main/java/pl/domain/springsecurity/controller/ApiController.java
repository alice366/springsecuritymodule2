package pl.domain.springsecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.domain.springsecurity.model.InMemoryUserList;

import java.security.Principal;

@RestController
public class ApiController {

    private SessionRegistry sessionRegistry;
    private InMemoryUserList inMemoryUserList;

    @Autowired
    public ApiController(SessionRegistry sessionRegistry, InMemoryUserList inMemoryUserList) {
        this.sessionRegistry = sessionRegistry;
        this.inMemoryUserList = inMemoryUserList;
    }

    @GetMapping("/admin")
    public String getAdmin(Principal principal) {
        return "Hi admin: " + principal.getName() + ", you've logged " + getNumberOfLoginTimes(principal) + " times " +
                "<br>" + " total number of users: " + sessionRegistry.getAllPrincipals().size();
    }

    @GetMapping("/user")
    public String getUser(Principal principal) {
        return "Hi user: " + principal.getName() + ", you've logged " + getNumberOfLoginTimes(principal) + " times ";
    }

    @GetMapping("/all")
    public String getAll() {
        return "Hi unknown!";
    }

    @GetMapping("/byebye")
    public String logout() {
        return "Thank you for your visit!";
    }

    private int getNumberOfLoginTimes(Principal principal) {
        return inMemoryUserList.findUserByName(principal.getName())
                .getNumberOfLoginTimes();
    }

}
