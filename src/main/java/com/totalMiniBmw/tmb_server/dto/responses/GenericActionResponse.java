package com.totalMiniBmw.tmb_server.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class GenericActionResponse {
    private String message;
    private List<String> data;
    private GenericActionType type;

    public GenericActionResponse(String message, List<String> data, GenericActionType type) {
        this.message = message;
        this.data = data;
    }

}
