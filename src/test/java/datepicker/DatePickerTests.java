package datepicker;

import base.BaseTests;
import components.DatePicker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatePickerTests extends BaseTests {

    private DatePicker datePicker;

    @BeforeAll
    public static void launchApp(){
        //TODO: change this to your local URL
        driver.get("file:///Users/angie/workspace/blogs/recipes/web/datepicker.html");
    }

    @BeforeEach
    public void refreshCalendar(){
        driver.navigate().refresh();
        datePicker = new DatePicker(driver);
    }

    @Test
    public void pastDate(){
        var dateToSelect = LocalDate.of(1989, Month.DECEMBER, 20);
        var selectedDate = datePicker.chooseDate(dateToSelect);
        assertEquals(dateToSelect, selectedDate);
    }

    @Test
    public void trickyDate(){
        var dateToSelect = LocalDate.of(2020, Month.MARCH, 31);
        var selectedDate = datePicker.chooseDate(dateToSelect);
        assertEquals(dateToSelect, selectedDate);
    }

    @Test
    public void futureDate(){
        var dateToSelect = LocalDate.now().plusMonths(7);
        var selectedDate = datePicker.chooseDate(dateToSelect);
        assertEquals(dateToSelect, selectedDate);
    }

    @Test
    public void currentMonth(){
        var dateToSelect = LocalDate.now().withDayOfMonth(15);
        var selectedDate = datePicker.chooseDate(dateToSelect);
        assertEquals(dateToSelect, selectedDate);
    }
}