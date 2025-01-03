package com.literaturalura.challengerAlura.repository;

import com.literaturalura.challengerAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepository extends JpaRepository<Libro,Long> {

    boolean existsByTitulo(String titulo);
}
