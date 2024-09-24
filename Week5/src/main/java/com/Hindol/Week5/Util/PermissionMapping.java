package com.Hindol.Week5.Util;

import com.Hindol.Week5.Entity.Enum.Permission;
import com.Hindol.Week5.Entity.Enum.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Hindol.Week5.Entity.Enum.Permission.*;
import static com.Hindol.Week5.Entity.Enum.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(USER_VIEW, POST_VIEW, POST_CREATE, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(USER_VIEW, POST_VIEW, POST_CREATE, USER_UPDATE, POST_UPDATE, USER_DELETE, USER_CREATE, POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map.getOrDefault(role, Set.of()).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
