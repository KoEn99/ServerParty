package com.koen.server.party.repository;

import com.koen.server.party.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogRepo extends JpaRepository<Dialog, String> {
}
