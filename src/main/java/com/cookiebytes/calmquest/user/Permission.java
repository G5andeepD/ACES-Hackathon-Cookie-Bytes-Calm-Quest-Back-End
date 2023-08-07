package com.cookiebytes.calmquest.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete"),
    COUNSELOR_READ("counselor:read"),
    COUNSELOR_UPDATE("counselor:update"),
    COUNSELOR_CREATE("counselor:create"),
    COUNSELOR_DELETE("counselor:delete")

    ;

    @Getter
    private final String permission;
}