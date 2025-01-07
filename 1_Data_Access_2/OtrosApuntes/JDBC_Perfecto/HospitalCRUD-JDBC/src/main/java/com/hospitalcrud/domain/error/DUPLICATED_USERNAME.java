package com.hospitalcrud.domain.error;

import com.hospitalcrud.common.Constantes;

public final class DUPLICATED_USERNAME extends DataBaseError {
    public DUPLICATED_USERNAME () {
        super(Constantes.DUPLICATED_USERNAME_ERROR);
    }
}
