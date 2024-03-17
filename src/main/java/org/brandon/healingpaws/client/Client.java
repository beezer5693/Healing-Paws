package org.brandon.healingpaws.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.brandon.healingpaws.audit.Auditable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "clients")
public class Client extends Auditable {

    @Id
    @SequenceGenerator(
            name = "clients_generator",
            sequenceName = "clients_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    @NotEmpty(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name cannot be empty")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @NotEmpty(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name cannot be empty")
    @JsonProperty("last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(nullable = false)
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(email, client.email) && Objects.equals(phone, client.phone) && Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, password);
    }
}
