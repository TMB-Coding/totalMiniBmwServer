package com.totalMiniBmw.tmb_server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.totalMiniBmw.tmb_server.dto.KioskDto;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionType;
import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.repository.ToolRepository;
import com.totalMiniBmw.tmb_server.services.ToolService;
import com.totalMiniBmw.tmb_server.services.UserService;
import com.totalMiniBmw.tmb_server.views.Views;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tool")
public class ToolController {
    private final ToolService toolService;
    private final ToolRepository toolRepository;

    public ToolController(ToolService toolService, ToolRepository toolRepository, UserService userService) {
        this.toolService = toolService;
        this.toolRepository = toolRepository;
    }


    @PreAuthorize("(hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_EMPLOYEE')) and (hasAuthority('INVENTORY') AND hasAuthority('SESSION_ALL_APPS'))")
    @GetMapping("/{toolId}")
    @JsonView(Views.Public.class)
    public ResponseEntity<ToolEntity> getTool(@PathVariable String toolId) {
        Optional<ToolEntity> tool = toolRepository.findById(toolId);

        if (tool.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tool.get());
    }

    @PreAuthorize("(hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_EMPLOYEE')) and (hasAuthority('INVENTORY') AND hasAuthority('SESSION_ALL_APPS'))")
    @GetMapping("/")
    public ResponseEntity<List<ToolEntity>> getAllTools() {
        List<ToolEntity> tools = toolRepository.findAll();
        return ResponseEntity.ok().body(tools);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('INVENTORY')")
    @DeleteMapping("/{toolId}")
    public ResponseEntity<GenericActionResponse> deleteTool(@PathVariable String toolId) {
        toolService.deleteTool(toolId);

        GenericActionResponse gar = new GenericActionResponse("Successfully deleted the tool.", null, GenericActionType.DELETE);

        return ResponseEntity.ok().body(gar);
    }

}
