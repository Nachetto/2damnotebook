package com.hospital_jpa.domain.error;

import com.hospital_jpa.ui.common.Constantes;

public final class FOREIGN_KEY_ERROR extends DataBaseError {
    public FOREIGN_KEY_ERROR() {
        super(Constantes.FOREIGN_KEY_ERROR);
    }
}
