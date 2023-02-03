package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}