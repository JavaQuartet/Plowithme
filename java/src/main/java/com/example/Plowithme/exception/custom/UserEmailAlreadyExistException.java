package com.example.Plowithme.exception.custom;


import com.example.Plowithme.exception.error.BaseException;
import com.example.Plowithme.exception.error.ErrorCode;



public class UserEmailAlreadyExistException extends BaseException {
    public UserEmailAlreadyExistException() {
        super(ErrorCode.EMAIL_DUPLICATE);
    }

}
