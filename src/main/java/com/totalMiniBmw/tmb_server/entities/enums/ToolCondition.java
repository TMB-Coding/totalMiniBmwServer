package com.totalMiniBmw.tmb_server.entities.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum ToolCondition {
    @JsonEnumDefaultValue OKAY,
    BROKEN,
    DAMAGED;

}
