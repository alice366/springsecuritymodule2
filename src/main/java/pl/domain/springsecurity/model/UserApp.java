package pl.domain.springsecurity.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserApp extends User {

    private int numberOfLoginTimes;

    public UserApp(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.numberOfLoginTimes = 0;
    }

    public void increaseNumberOfLoginTimes() {
        numberOfLoginTimes++;
    }

}
