package org.brandon.healingpaws.exceptions;

import lombok.Getter;

@Getter
public class ClientAlreadyExistsException extends RuntimeException {

    private final String email;

    public ClientAlreadyExistsException(String email) {
        super(String.format("Client with email [%s] already exists", email));
        this.email = email;
    }
}
