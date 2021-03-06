package com.ticketing.implementation;

import com.ticketing.dto.UserDTO;
import com.ticketing.entity.User;
import com.ticketing.util.MapperUtil;
import com.ticketing.service.SecurityService;
import com.ticketing.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {


    private UserService userService;
    private MapperUtil mapperUtil;

    public SecurityServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDTO user = userService.findByUserName(s);

        if(user==null){
            throw new UsernameNotFoundException("This user does not exists");
        }

        return new org.springframework.security.core.userdetails.User(user.getId().toString(),user.getPassWord(),listAuthorities(user));

    }

    @Override
    public User loadUser(String param) throws AccessDeniedException {
        UserDTO user =  userService.findByUserName(param);
        return mapperUtil.convert(user,new User());
    }

    private Collection<? extends GrantedAuthority> listAuthorities(UserDTO user){
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getDescription());
        authorityList.add(authority);

        return authorityList;


    }
}