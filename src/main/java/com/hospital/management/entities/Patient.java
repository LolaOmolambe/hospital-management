package com.hospital.management.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
@SQLDelete(sql = "UPDATE patients SET deleted = true WHERE id=? and version=?")
@Where(clause = "deleted = false")
public class Patient extends BaseEntity {
    private String name;

    private int age;

    private LocalDate lastVisitDate;
}
