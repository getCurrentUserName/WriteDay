package org.write_day.components.enums;

public enum CommandStatus {

    OK,
    ERROR,
    ANONYM,
    USERNAME_EXIST,
    EMAIL_EXIST,
    ACCESS_DENIED,
    BAD_REQUEST,
    NULL,
    SUCCESS,
    CURRENT_USER,

    EMPTY,
    OTHER_FORMAT,

    FRIEND,
    SUBSCRIBER,
    FOLLOW,
    REQUEST,
    IGNORE,
    IGNORED;

    CommandStatus() {}
}
