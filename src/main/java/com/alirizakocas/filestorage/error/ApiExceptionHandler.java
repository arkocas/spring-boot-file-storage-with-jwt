package com.alirizakocas.filestorage.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(ForbiddenException exception, HttpServletRequest request){
        ApiError error = new ApiError(403, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception, HttpServletRequest request){
        ApiError error = new ApiError(404, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleFileException(FileException exception, HttpServletRequest request){
        ApiError error = new ApiError(400, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiError handleFileUploadException(MaxUploadSizeExceededException exception, HttpServletRequest request){
        ApiError error = new ApiError(500, "File size limit exceeded. Please make sure the file size is less than 5MB", request.getServletPath(), new Date());
        return error;
    }
}