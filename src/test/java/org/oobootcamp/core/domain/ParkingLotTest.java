package org.oobootcamp.core.domain;

import org.junit.jupiter.api.Test;
import org.oobootcamp.core.exception.ParkCarException;
import org.oobootcamp.core.exception.PickUpCarException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {

    //- Given 停车场有一个空位, When 普通停车用户自助停车, Then 普通停车用户 停车成功后拿到票
    //- Given 停车场有两个空位, When 普通停车用户自助停车, Then 普通停车用户 停车成功后拿到票
    @Test
    public void should_return_ticket_when_parking_car_given_parking_lot_has_one_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car(1);
        assertThat(parkingLot.parkingCar(car)).isEqualTo(new Ticket(car.hashCode()));
    }

    @Test
    public void should_return_ticket_when_parking_car_given_parking_lot_has_two_space() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car(1);
        assertThat(parkingLot.parkingCar(car)).isEqualTo(new Ticket(car.hashCode()));
    }

    //- Given 停车场没有空位, When 普通停车用户自助停车, Then 普通停车用户 停车失败，提示停车失败
    @Test
    public void should_park_failed_when_parking_car_given_parking_lot_no_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = new Car(1);
        parkingLot.parkingCar(car1);
        Car car2 = new Car(2);
        assertThat(assertThrows(ParkCarException.class, () -> parkingLot.parkingCar(car2)).getLocalizedMessage()).isEqualTo("停车失败");
    }

    //- Given 有票，号码为一号, When 普通停车用户自助取车, Then 普通停车用户 取一号车位的车成功
    //- Given 有票，号码为二号, When 普通停车用户自助取车, Then 普通停车用户 取二号车位的车成功
    @Test
    public void should_pick_up_car_1_when_pick_up_car_given_ticket_1() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car(1);
        Ticket ticket = parkingLot.parkingCar(car);
        assertThat(parkingLot.pickUpCar(ticket)).isEqualTo(car);
        assertThat(car.id()).isEqualTo(ticket.id());
    }

    @Test
    public void should_pick_up_car_2_when_pick_up_car_given_ticket_2() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car(2);
        Ticket ticket = parkingLot.parkingCar(car);
        assertThat(parkingLot.pickUpCar(ticket)).isEqualTo(car);
        assertThat(car.id()).isEqualTo(ticket.id());
    }

    //- Given 有票,票对应的车已经被取 When 普通停车用户自助取车, Then 普通停车用户 取车失败，提示票无效
    @Test
    public void should_pick_up_failed_when_pick_up_car_given_used_ticket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car(1);
        Car car2 = new Car(2);
        Ticket ticket = parkingLot.parkingCar(car);
        parkingLot.parkingCar(car2);
        assertThat(parkingLot.pickUpCar(ticket)).isEqualTo(car);
        assertThat(assertThrows(PickUpCarException.class, () -> parkingLot.pickUpCar(ticket)).getLocalizedMessage()).isEqualTo("取车失败");
    }

    // - Given 无票, When 普通停车用户自助取车, Then 普通停车用户 取车失败
    @Test
    public void should_pick_up_failed_when_pick_up_car_given_null_ticket() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car(1);
        parkingLot.parkingCar(car);
        assertThat(assertThrows(PickUpCarException.class, () -> parkingLot.pickUpCar(null)).getLocalizedMessage()).isEqualTo("取车失败");
    }

}
