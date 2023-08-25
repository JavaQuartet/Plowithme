package com.example.Plowithme.exception.custom;

import com.example.Plowithme.exception.error.BaseException;
import com.example.Plowithme.exception.error.ErrorCode;

public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }

}
