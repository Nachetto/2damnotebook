package com.hospitalcrud.domain.error;

import com.hospitalcrud.common.Constantes;

public final class FOREIGN_KEY_ERROR extends DataBaseError {
    public FOREIGN_KEY_ERROR() {
        super(Constantes.FOREIGN_KEY_ERROR);
    }
}
