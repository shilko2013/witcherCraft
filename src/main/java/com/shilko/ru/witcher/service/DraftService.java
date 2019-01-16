package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Draft;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Thing;

import java.util.List;
import java.util.Optional;

public interface DraftService {

    List<Draft> getAllDrafts(boolean isAlchemy);

    Optional<Image> getImageByIdDraft(Long id);

    void saveDraft(String information, String thing, List<String> components);

    Optional<Draft> getDraftById(Long id);

    void deleteDraftById(Long id);
}
