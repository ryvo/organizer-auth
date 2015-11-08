package cz.ryvo.organizer.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Document(collection = UserDetailsVO.COLLECTION)
public class UserDetailsVO {

    public static final String COLLECTION = "user";

    @Id
    private BigInteger id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private Boolean enabled;

    private Boolean locked;

    private Timestamp expirationDateTime;

    private Timestamp credentialsExpirationDateTime;

    public Boolean isExpired() {
        Date now = new Date();
        return expirationDateTime != null && expirationDateTime.before(now);
    }

    public Boolean isCredentialsExpired() {
        Date now = new Date();
        return credentialsExpirationDateTime != null && credentialsExpirationDateTime.before(now);
    }

    public BigInteger getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled == null ? true : enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isLocked() {
        return locked == null ? false : locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Timestamp getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Timestamp expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Timestamp getCredentialsExpirationDateTime() {
        return credentialsExpirationDateTime;
    }

    public void setCredentialsExpirationDateTime(Timestamp credentialsExpirationDateTime) {
        this.credentialsExpirationDateTime = credentialsExpirationDateTime;
    }
}
