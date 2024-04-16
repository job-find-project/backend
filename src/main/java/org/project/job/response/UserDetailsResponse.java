package org.project.job.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class UserDetailsResponse {
    private Long id;
    private String userName;
    private String token;
    private List<GrantedAuthority> authorities;
}
