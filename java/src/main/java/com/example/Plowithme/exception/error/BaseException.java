package com.example.Plowithme.exception.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;

}