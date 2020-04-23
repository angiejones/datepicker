package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static java.lang.String.format;

public class DatePicker {

    private WebDriver driver;

    private By openCalendarButton = By.cssSelector("#datepicker ~ span button");
    private By calendar = By.cssSelector("div[role='calendar']");
    private By period = By.cssSelector("div[role='period']");
    private By leftArrow = By.cssSelector("div[role='navigator'] i.chevron-left");
    private By rightArrow = By.cssSelector("div[role='navigator'] i.chevron-right");
    private String day_FORMAT = "//td[@day='%d' and contains(@class, 'current-month')]";

    public DatePicker(WebDriver driver){
        this.driver = driver;
    }

    public LocalDate chooseDate(LocalDate date){
        open();
        chooseMonth(date);
        chooseDay(date.getDayOfMonth());
        return getSelectedDate();
    }

    public LocalDate getSelectedDate(){
       var fields = driver.findElement(calendar).getAttribute("selectedday").split("-");
       return LocalDate.of(
               Integer.parseInt(fields[0]),
               Integer.parseInt(fields[1]) + 1,
               Integer.parseInt(fields[2]));
    }

    public void chooseDay(int dayOfMonth) {
        By locator = By.xpath(format(day_FORMAT, dayOfMonth));
        driver.findElement(locator).click();
    }

    public void chooseMonth(LocalDate date) {
        var currentPeriod = getCurrentPeriod();
        long monthsAway = ChronoUnit.MONTHS.between(currentPeriod, date.withDayOfMonth(1));

        By arrow = monthsAway < 0 ? leftArrow : rightArrow;

        for(int i = 0; i < Math.abs(monthsAway); i++){
            driver.findElement(arrow).click();
        }
    }

    public LocalDate getCurrentPeriod(){
        var currentPeriod = driver.findElement(period).getText().split(" ");
        return LocalDate.of(
                Integer.parseInt(currentPeriod[1]),
                Month.valueOf(currentPeriod[0].toUpperCase()),
                1);
    }

    public void open(){
        if(!isCalendarOpen()){
            driver.findElement(openCalendarButton).click();
        }
    }

    public boolean isCalendarOpen(){
        return driver.findElement(calendar).isDisplayed();
    }
}
