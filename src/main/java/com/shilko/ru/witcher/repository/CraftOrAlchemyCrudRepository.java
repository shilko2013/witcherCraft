package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.CraftOrAlchemy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CraftOrAlchemyCrudRepository extends CrudRepository<CraftOrAlchemy, Long> {

    @Override
    CraftOrAlchemy save(CraftOrAlchemy craftOrAlchemy);

    List<CraftOrAlchemy> findAllByIsAlchemy(boolean isAlchemy);
}
