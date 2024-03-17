package org.brandon.healingpaws.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final Long id;

    public EntityNotFoundException(Long id) {
        super(String.format("Client with id [%d] not found", id));
        this.id = id;
    }
}
