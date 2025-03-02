package com.hospital_jpa.common;

public class Constantes {
    public static final String DUPLICATED_USERNAME_ERROR = "Username duplicated, try a different one";
    public static final String FOREIGN_KEY_ERROR = "Before deleting a patient you first have to delete its personal information";
    public static final String PERSISTANCE_UNIT = "hibernate";

    private Constantes() {}
}
