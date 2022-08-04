package org.oobootcamp.core.domain;

import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Comparator;

public class SmartParkingBoy extends AbstractParkingBoy {

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    Option<ParkingLot> findParkingLot() {
        return this.getMaxRemainingParkingLot(this.parkingLots);
    }

    private Option<ParkingLot> getMaxRemainingParkingLot(List<ParkingLot> parkingLots) {
        return parkingLots.maxBy(Comparator.comparingInt(ParkingLot::getUsefulTilesRemaining));
    }
}