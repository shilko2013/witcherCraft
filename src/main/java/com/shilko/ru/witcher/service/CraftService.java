package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Thing;

public interface CraftService {

    Thing getTreeByThingId(Long id);
}
