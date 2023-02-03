package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findSkillBySkillName(String skillName);
}