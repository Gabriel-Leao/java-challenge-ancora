package br.com.realtech.ancora.services;

import br.com.realtech.ancora.entities.User;
import br.com.realtech.ancora.exceptions.NotFoundException;
import br.com.realtech.ancora.models.UserDetailsImpl;
import br.com.realtech.ancora.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return new UserDetailsImpl(user);
    }
}
