package com.example.reyortiz_ejercicio3.domain;

import com.example.reyortiz_ejercicio3.data.EmpleadoRepository;
import com.example.reyortiz_ejercicio3.data.modelo.Empleado;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmpleadoRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado user = userRepository.findByNombre(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.getNombre())
                .password(user.getPassword())
                .roles(user.getRol())
                .build();
    }
}
