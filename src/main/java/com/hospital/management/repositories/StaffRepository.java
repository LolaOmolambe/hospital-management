package com.hospital.management.repositories;

import com.hospital.management.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByUuidIgnoreCase(String uuid);

    Staff findByUuidIgnoreCase(String uuid);
}
