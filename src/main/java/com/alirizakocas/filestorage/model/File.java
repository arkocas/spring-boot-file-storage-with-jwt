package com.alirizakocas.filestorage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "FILE")
@ApiModel(value = "File Api model documentation", description = "Model")
public class File {

    @ApiModelProperty(value = "Unique fileId field of File object")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileId;

    @ApiModelProperty(value = "username field of File object")
    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 45,
            message = "The username '${validatedValue}' must be between {min} and {max} characters long"
    )
    @JoinColumn(name = "USERNAME")
    private String username;

    @ApiModelProperty(value = "fileName field of File object")
    @NonNull
    @NotNull
    @Size(
            min = 4,
            max = 100,
            message = "The fileName '${validatedValue}' must be between {min} and {max} characters long"
    )
    private String fileName;

    @NonNull
    @ApiModelProperty(value = "fileContentType field of File object")
    private String fileContentType;

    @NonNull
    @ApiModelProperty(value = "fileExtension field of File object")
    private String fileExtension;

    @NonNull
    @ApiModelProperty(value = "fileSize field of File object")
    private long fileSize;

    @NonNull
    @ApiModelProperty(value = "filePath field of File object")
    private String filePath;

}
