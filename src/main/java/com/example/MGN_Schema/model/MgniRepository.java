package com.example.MGN_Schema.model;

import com.example.MGN_Schema.model.entity.Mgni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MgniRepository extends JpaRepository<Mgni, String> , JpaSpecificationExecutor<Mgni> {

    @Query(value = "select * from MGNI where MGNI_ID =?1",nativeQuery = true)
    Mgni getMgniById(String id);
}
