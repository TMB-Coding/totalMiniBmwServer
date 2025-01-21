package com.totalMiniBmw.tmb_server.entities.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;


public enum Authority {
    INVENTORY,
    KIOSK,
    PAYROLL,
    @JsonEnumDefaultValue NO_ACCESS,
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    @JsonEnumDefaultValue ROLE_GUEST
}
