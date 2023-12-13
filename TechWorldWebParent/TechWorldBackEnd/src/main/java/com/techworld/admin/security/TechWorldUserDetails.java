package com.techworld.admin.security;

import com.techworld.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TechWorldUserDetails implements UserDetails {
    private User user;

    public TechWorldUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authories = new ArrayList<>();
        authories.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authories;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getFullname(){
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public String getRole(){
        return user.getRole().getName();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getPhoto(){
        return user.getPhotos();
    }

    public void setFirstname(String firstName){
        this.user.setFirstName(firstName);
    }

    public void setLastname(String lastName){
        this.user.setLastName(lastName);
    }

    public void setPhoto(String photo){
        this.user.setPhotos(photo);
    }

    public boolean hasRole(String roleName){
        return user.hasRole(roleName);
    }
}
