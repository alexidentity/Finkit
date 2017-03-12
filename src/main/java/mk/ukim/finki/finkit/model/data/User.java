package mk.ukim.finki.finkit.model.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import mk.ukim.finki.finkit.service.ImageService;

@Entity
@Table(name="users")
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
     
    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "avatar_id")
    private Long avatarId;
    
    @Column(name = "registered_date")
    private Date registeredDate;
    
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getLogin() {
        return login;
    }
    
    public String getLoginCapitalized() {
      return StringUtils.capitalize(getLogin());
  }
 
    public void setLogin(String login) {
        this.login = login;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getRole() {
        return role;
    }
 
    public void setRole(String role) {
        this.role = role;
    }

		public Long getAvatarId() {
			return avatarId;
		}

		public void setAvatarId(Long avatarId) {
			this.avatarId = avatarId;
		}
		
		public String getAvatarUrl() {
			return ImageService.resolveUrlFromId(getAvatarId());
		}

		public Date getRegisteredDate() {
			return registeredDate;
		}
		
		public String getRegisteredDateFormatted() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			return simpleDateFormat.format(getRegisteredDate());
		}

		public void setRegisteredDate(Date registeredDate) {
			this.registeredDate = registeredDate;
		}
 
		public Collection<GrantedAuthority> getAuthorities() {
	    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	    
	    GrantedAuthority grantedAuthority = new GrantedAuthority() {
				private static final long serialVersionUID = 1L;
				
	        public String getAuthority() {
	            return "USER";
	        }
	    }; 
	    
	    grantedAuthorities.add(grantedAuthority);
	    return grantedAuthorities;
		}
		
}
