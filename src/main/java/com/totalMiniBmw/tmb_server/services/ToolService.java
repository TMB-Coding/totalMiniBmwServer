package com.totalMiniBmw.tmb_server.services;

import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.repository.ToolRepository;
import com.totalMiniBmw.tmb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class ToolService {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    public ToolService(ToolRepository toolRepository, UserRepository userRepository) {
        this.toolRepository = toolRepository;
        this.userRepository = userRepository;
    }

    private int genBarcode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    public ToolEntity saveTool(ToolEntity tool) {
        tool.setBarcode(genBarcode());
        ToolEntity toolE = toolRepository.save(tool);
        return toolE;
    }

    public void deleteTool(String id) {
       Optional<ToolEntity> tool = toolRepository.findById(id);
       tool.ifPresent(toolRepository::delete);
    }

    public void editTool(ToolEntity tool, String toolId) {
        tool.setId(toolId);
        tool.setBarcode(toolRepository.findById(toolId).get().getBarcode());
        toolRepository.save(tool);
    }
    @Transactional
    public ResponseEntity<GenericActionResponse> inventoryTool(long barcode, String userId) {
        Optional<ToolEntity> tool = toolRepository.findByBarcode(barcode);
        Optional<UserEntity> user = userRepository.findById(userId);
        if (tool.isEmpty()) {
            GenericActionResponse gar = new GenericActionResponse("Tool with the specified barcode does not exist.", null, null);
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(gar);
        }
        ToolEntity toolEntity = tool.get();
        // if user has tool with this barcode checkedout, check in the tool
        if (user.get().getCheckedOut().contains(toolEntity)) {
            toolEntity.setUser(null);
            toolRepository.save(toolEntity);
            GenericActionResponse gar = new GenericActionResponse("Tool checked in.", null, null);
            return ResponseEntity.ok().body(gar);
        }
        // user doesnt have this tool checked out so check the tool in.
        // first verify the tool isn't already checked out.
        if (toolEntity.getUser() != null) {
            GenericActionResponse gar = new GenericActionResponse("Error, tool is already checked out by another employee.", null, null);
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(gar);
        }
            toolEntity.setUser(user.get());
            toolRepository.save(toolEntity);
            GenericActionResponse gar = new GenericActionResponse("Tool checked out.", null, null);
            return ResponseEntity.ok().body(gar);
    }
}
