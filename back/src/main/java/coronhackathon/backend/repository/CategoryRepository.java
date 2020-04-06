package coronhackathon.backend.repository;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findById(long id);

    public Optional<Category> findByName(String name);

    public long count();
}
