package com.pizzeria.store.service;

import com.pizzeria.store.entity.Crust;

import java.util.List;

public interface CrustService {
    List<Crust> getAvailableCrusts();
    boolean exist(String crust);
}
