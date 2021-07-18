package ru.itis.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.practice.models.Theme;

public interface ThemesRepository extends JpaRepository <Theme, Long>{
}
