package net.devopssolutions.microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class User implements UserDetails {

    private Long id;
    private String name;
    private String password;
    private String role;

    public User() {
    }

    public User(Long id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    private User(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setPassword(builder.password);
        setRole(builder.role);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(User copy) {
        Builder builder = new Builder();
        builder.id = copy.id;
        builder.name = copy.name;
        builder.password = copy.password;
        builder.role = copy.role;
        return builder;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> collection = new ArrayList<>();
        if (getId() != null && !"user".equalsIgnoreCase(getRole())) {
            collection.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if (getRole() != null) {
            collection.add(new SimpleGrantedAuthority("ROLE_" + getRole()));
        }
        return collection;
    }

    @Override
    public String getUsername() {
        return name;
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
        return true;
    }

    public static final class Builder {
        private Long id = null;
        private String name = null;
        private String password = null;
        private String role = null;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
