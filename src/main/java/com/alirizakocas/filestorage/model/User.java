package com.alirizakocas.filestorage.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 45,
            message = "The username '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "USERNAME", nullable = false, length = 100)
    private String username;

    @NonNull
    @NotNull
    @Size(
            min = 8,
            max = 30,
            message = "The password '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;
}

