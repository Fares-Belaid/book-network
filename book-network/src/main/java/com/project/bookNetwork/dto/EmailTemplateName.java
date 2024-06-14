package com.project.bookNetwork.dto;

import lombok.Getter;

/**
 * @author fares.belaid
 * 14/06/2024
 */
@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate-account");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
