package com.totalMiniBmw.tmb_server.repository;

import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<ToolEntity, String> {

}
