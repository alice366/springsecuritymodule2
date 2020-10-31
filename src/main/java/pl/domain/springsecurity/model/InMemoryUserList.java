package pl.domain.springsecurity.model;


import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Component
public class InMemoryUserList {

    private List<UserApp> userAppList;

    public InMemoryUserList() {
        this.userAppList = createUserAppList();
    }

    private List<UserApp> createUserAppList() {
        UserApp userAdmin = new UserApp("JanAdmin", getPasswordEncoder().encode("admin"), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserApp userUser = new UserApp("Marek", getPasswordEncoder().encode("marek_user"), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        List<UserApp> users = new ArrayList<>();
        users.add(userAdmin);
        users.add(userUser);

        return users;

    }

    public UserApp findUserByName(String username) {
       return userAppList.stream().filter(user-> user.getUsername().equals(username)).findFirst().get();
    }

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
