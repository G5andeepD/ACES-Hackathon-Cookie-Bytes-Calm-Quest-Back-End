package com.cookiebytes.calmquest.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cookiebytes.calmquest.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE,
                    STUDENT_CREATE,
                    COUNSELOR_READ,
                    COUNSELOR_UPDATE,
                    COUNSELOR_DELETE,
                    COUNSELOR_CREATE
            )
    ),

    STUDENT(
            Set.of(
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE,
                    STUDENT_CREATE
            )
    ),

    COUNSELOR(
            Set.of(
                    COUNSELOR_READ,
                    COUNSELOR_UPDATE,
                    COUNSELOR_CREATE,
                    COUNSELOR_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
