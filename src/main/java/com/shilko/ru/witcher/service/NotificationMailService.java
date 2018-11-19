package com.shilko.ru.witcher.service;

import org.springframework.data.util.Pair;

public interface NotificationMailService {

    boolean sendMessage(String message, String email);
}
