package coronhackathon.backend.service;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> ouser = userRepository.findByUsername(username);
        if (!ouser.isPresent()) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + username);
        }
        User user = ouser.get();

        /*
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
         */

        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPwdHash(),
                        /*
                        enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked,
                         */
                        auths);
    }

}