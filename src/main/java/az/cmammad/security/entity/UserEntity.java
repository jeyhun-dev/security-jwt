package az.cmammad.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity {

    public static final String TABLE_NAME = "users";
    private static final long serialVersionUUID = 1234567893245092173L;

    @Id
    @GeneratedValue
    Long id;
    String firstname;
    String lastname;
    String address;
    String phoneNumber;
    String email;
    String password;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    @PrePersist
    public void prePersist() {
        setCreatedDate(new Date());
    }
}
