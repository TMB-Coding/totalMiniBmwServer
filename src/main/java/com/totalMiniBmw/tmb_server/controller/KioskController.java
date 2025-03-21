package com.totalMiniBmw.tmb_server.controller;

import com.totalMiniBmw.tmb_server.dto.KioskDto;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.services.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
public class KioskController {

    private final ToolService toolService;

    public KioskController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PreAuthorize("(hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_EMPLOYEE')) and (hasAuthority('INVENTORY'))")
    @PostMapping("/kiosk")
    public ResponseEntity<GenericActionResponse> kioskTool(@AuthenticationPrincipal UserEntity user, @RequestBody KioskDto dto) {
        return toolService.inventoryTool(dto.getBarcode(), user.getId());
    }
}
