package by.park.security.service;

import by.park.dao.UserDao;
import by.park.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    UserDao userRepository;

    public UserDetailServiceImpl(@Qualifier("userDaoRepository") UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> searchResult = userRepository.findByLogin(username);

        if(searchResult.isPresent()){
            User user = searchResult.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("USER")
            );
        }else{
            throw new UsernameNotFoundException("User with login " + username + " wasn't found!");
        }
    }
}
