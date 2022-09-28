package com.example.MGN_Schema.model;

import com.example.MGN_Schema.model.entity.Cashi;
import com.example.MGN_Schema.model.entity.CashiRelationPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashiRepository extends JpaRepository<Cashi, CashiRelationPK> {
}
