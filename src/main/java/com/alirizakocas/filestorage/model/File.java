package com.alirizakocas.filestorage.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "FILE")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class File {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileId;

    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 45,
            message = "The username '${validatedValue}' must be between {min} and {max} characters long"
    )
    @JoinColumn(name = "USERNAME")
    private String username;

    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 100,
            message = "The fileName '${validatedValue}' must be between {min} and {max} characters long"
    )
    private String fileName;

    @NonNull
    private String fileContentType;

    @NonNull
    private String fileExtension;

    @NonNull
    private long fileSize;

    @NonNull
    private String filePath;

}
