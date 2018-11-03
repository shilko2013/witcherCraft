package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.CraftOrAlchemy;
import org.springframework.data.repository.CrudRepository;

public interface CraftOrAlchemyCrudRepository extends CrudRepository<CraftOrAlchemy, Long> {

    @Override
    CraftOrAlchemy save(CraftOrAlchemy craftOrAlchemy);

    CraftOrAlchemy findByIsAlchemy(boolean isAlchemy);
}
