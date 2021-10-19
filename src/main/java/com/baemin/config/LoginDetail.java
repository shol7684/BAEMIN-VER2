package com.baemin.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baemin.vo.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDetail implements UserDetails {

	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		System.out.println("getAuthorities");
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		roles.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				System.out.println();
				return user.getRole();
			}
		});
//		System.out.println(roles.get(0).getAuthority());
		
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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

}
