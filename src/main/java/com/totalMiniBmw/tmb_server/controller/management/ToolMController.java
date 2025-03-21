package com.totalMiniBmw.tmb_server.controller.management;

import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import com.totalMiniBmw.tmb_server.services.ToolService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mgmt/tool")
public class ToolMController {

    private final ToolService toolService;

    public ToolMController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('INVENTORY')")
    @PostMapping("/")
    public ResponseEntity<ToolEntity> addTool(@Valid @RequestBody ToolEntity tool) {
        ToolEntity newTool = toolService.saveTool(tool);

        return ResponseEntity.ok().body(newTool);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('INVENTORY')")
    @PatchMapping("/{toolId}")
    public ResponseEntity<ToolEntity> editTool(@PathVariable String toolId, @RequestBody ToolEntity tool) {
        toolService.editTool(tool, toolId);
        return ResponseEntity.ok(new ToolEntity());
    }
}
