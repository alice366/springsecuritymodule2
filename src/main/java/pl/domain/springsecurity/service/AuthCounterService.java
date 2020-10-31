package pl.domain.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.domain.springsecurity.model.InMemoryUserList;

@Service
public class AuthCounterService {

    private InMemoryUserList inMemoryUserList;

    @Autowired
    public AuthCounterService(InMemoryUserList inMemoryUserList) {
        this.inMemoryUserList = inMemoryUserList;
    }

    @EventListener
    public void onApplicationSuccessEvent(AuthenticationSuccessEvent event) {
        inMemoryUserList.findUserByName(((User) event.getAuthentication()
                .getPrincipal()).getUsername())
                .increaseNumberOfLoginTimes();
    }

}