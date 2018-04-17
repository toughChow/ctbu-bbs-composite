package bbs.core.persist.service;

import bbs.core.data.Plate;

import java.util.List;

public interface PlateService {
    List<Plate> findAll();

    Plate findOne(Long id);
}
