package com.smha.sms.aop;

import com.smha.sms.annotation.PermissionRequired;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class SecurityAspect {


    @Before("@annotation(permissionRequired)")
    public void checkPermission (PermissionRequired permissionRequired) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasPermission = false;
        for (GrantedAuthority authority : authentication.getAuthorities()){
            if (authority.getAuthority().equals(permissionRequired.value())){
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission){
            throw new AccessDeniedException("Access Denied");
        }
    }

//    @Before("@annotation(permissionRequired)")
//    public void checkPermission(PermissionRequired permissionRequired) throws AccessDeniedException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        authentication.getAuthorities()
//                .forEach(a -> System.out.println(a.getAuthority()));
//
//        System.out.println("permissionRequired.value()==="+permissionRequired.value().toString());
//
//        boolean hasPermission = false;
//        for(GrantedAuthority authority : authentication.getAuthorities()){
//            if(authority.getAuthority().equals(permissionRequired.value())){
//                hasPermission = true;
//                break;
//            }
//        }
//
//        /*boolean hasPermission = authentication.getAuthorities()
//                .forEach(a -> {
//                    a.getAuthority().equals(permissionRequired.value());
//                });*/
//
//        /*boolean hasPermission = authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority()
//                        .equals(permissionRequired.value()));*/
//
//        System.out.println("hasPermission==="+hasPermission);
//
//        if (!hasPermission) {
//            throw new AccessDeniedException("Permission Denied");
//        }
//    }
}
