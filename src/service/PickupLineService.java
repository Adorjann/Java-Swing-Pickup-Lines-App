package service;

import data.PickupLineRepository;
import model.PickupLine;

import java.util.Collection;

public class PickupLineService {
    public static Collection<PickupLine> getAllLines() {
        return PickupLineRepository.getAll();
    }
}
