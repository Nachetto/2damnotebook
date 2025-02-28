package com.hospital_jpa.domain.error;

import com.hospital_jpa.common.Constantes;

public final class DUPLICATED_USERNAME extends DataBaseError {
    public DUPLICATED_USERNAME () {
        super(Constantes.DUPLICATED_USERNAME_ERROR);
    }
}
