package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Thing;

import java.util.List;

public interface CraftService {

    Thing getTreeByThingId(Long id);

    List<Thing> getAllThingsByIsAlchemy(boolean isAlchemy);
}
