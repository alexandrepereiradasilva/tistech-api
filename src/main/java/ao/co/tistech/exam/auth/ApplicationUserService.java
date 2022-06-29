package ao.co.tistech.exam.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Service
@Slf4j
public class ApplicationUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUserName(userName);

            if (user == null) {
                throw new UsernameNotFoundException("User not found!");
            }

            log.info("User found: {}", userName);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority((role.getName())));
            });

            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
