package tasks;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SellersListDate {

    public LocalDate dateOfShipment() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDate nowDate = nowDateTime.toLocalDate();

        LocalTime shipTime = LocalTime.of(18, 0, 0, 0);

        LocalDateTime firstDayOfShipment = LocalDateTime.of(nowDate.withDayOfMonth(1), shipTime);
        LocalDateTime tenDayOfShipment = LocalDateTime.of(nowDate.withDayOfMonth(10), shipTime);
        LocalDateTime twentyDayOfShipment = LocalDateTime.of(nowDate.withDayOfMonth(20), shipTime);

        LocalDate shipDate;

        if (nowDateTime.isAfter(firstDayOfShipment) && nowDateTime.isBefore(tenDayOfShipment)) {
            shipDate = tenDayOfShipment.toLocalDate();
        } else if (nowDateTime.isAfter(tenDayOfShipment) && nowDateTime.isBefore(twentyDayOfShipment)) {
            shipDate = twentyDayOfShipment.toLocalDate();
        } else {
            shipDate = firstDayOfShipment.toLocalDate().plusMonths(1);
        }

        LocalDate checkShipDate = getVacCheck(Date.valueOf(shipDate)).toLocalDate();

        while (!shipDate.isEqual(checkShipDate)){
            shipDate = shipDate.minusDays(1);
            checkShipDate = getVacCheck(Date.valueOf(shipDate)).toLocalDate();
        }

        return shipDate;
    }

    //Рекурсию можно заменить на цикл
    public Date getVacCheck(Date modDate) {
        LocalDate date = modDate.toLocalDate();
        if (date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5) {
            return modDate;
        } else {
            modDate = Date.valueOf(date.plusDays(1));
        }
        return getVacCheck(modDate);
    }

}

class SellersListDateTest{
    public static void main(String[] args) {
        System.out.println(new SellersListDate().dateOfShipment());
    }
}
