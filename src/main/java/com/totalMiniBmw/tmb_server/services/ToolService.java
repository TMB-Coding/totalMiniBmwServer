package com.totalMiniBmw.tmb_server.services;

import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import com.totalMiniBmw.tmb_server.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
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
}
