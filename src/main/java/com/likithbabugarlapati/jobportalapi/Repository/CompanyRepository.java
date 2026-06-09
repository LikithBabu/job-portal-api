package com.likithbabugarlapati.jobportalapi.Repository;

import com.likithbabugarlapati.jobportalapi.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByRecruiterId(Long recruiterId);
    boolean existsByRecruiterId(Long recruiterId);
    boolean existsByName(String name);
}