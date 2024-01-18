package dao.impl;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HasheoConstrasenas {
    public String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.hash(2, 65536, 1, password.toCharArray());
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

    public boolean verify(String s, char[] chars) {

    }
}
