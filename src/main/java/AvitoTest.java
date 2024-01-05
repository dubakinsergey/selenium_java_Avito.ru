import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AvitoTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/dubakinsergey/Downloads/chromedriver-mac-arm64/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);

//        Шаг 1
//        Открыть ресурс по адресу https://www.avito.ru/

        driver.get("https://www.avito.ru/");
        WebElement categories = driver.findElement(By.xpath("//button[@data-marker=\"top-rubricator/all-categories\"]"));
        categories.click();

//        Шаг 2
//        Выбрать в выпадающем списке “категория”  значение оргтехника и расходники

        WebElement electronics = driver.findElement(By.xpath("//div[@data-marker=\"top-rubricator/root-category-26195\"]"));
        electronics.click();
        WebElement officeEquipmentAndConsumables = driver.findElement(By.xpath("//strong[@data-name=\"Оргтехника и расходники\"]"));
        officeEquipmentAndConsumables.click();

//        Шаг 3
//        В поле поиск по объявлению ввести значение “Принтер”

        WebElement search = driver.findElement(By.xpath("//input[@data-marker=\"search-form/suggest\"]"));
        search.click();
        search.sendKeys("Принтер");

//        Шаг 4
//        Нажать на поле город

        WebElement location = driver.findElement(By.cssSelector("[data-marker=\"search-form/change-location\"]"));
        location.click();

//        Шаг 5
//        Заполнить значением “Владивосток” поле город  в открывшемся окне и кликнуть по первому предложенному варианту. Нажать на кнопку “Показать объявления

        WebElement clearLocation = driver.findElement(By.xpath("//div[@data-marker=\"clear-icon\"]"));
        clearLocation.click();

        WebElement selectCityOrRegion = driver.findElement(By.xpath("//input[@data-marker=\"popup-location/region/input\"]"));
        selectCityOrRegion.click();
        selectCityOrRegion.sendKeys("Владивосток");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Выбираем первый элемент из выпадающего списка

        selectCityOrRegion.sendKeys(Keys.ARROW_DOWN); // переход к первому элементу
        selectCityOrRegion.sendKeys(Keys.RETURN); // выбор первого элемента

        WebElement buttonSelectByCity = driver.findElement(By.xpath("//button[@data-marker=\"popup-location/save-button\"]"));
        buttonSelectByCity.click();

//        Шаг 6
//        Проверить, активирован ли чекбокс, и если не активирован – активировать и нажать кнопку “Показать объявления”

        WebElement checkbox = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-marker=\"delivery-filter/text\"]")));

//        Прокрутить страницу, чтобы чекбокс был видимым

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);

        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        WebElement buttonShowAdsWithSelectedDelivery = driver.findElement(By.xpath("//button[@data-marker=\"search-filters/submit-button\"]"));
        buttonShowAdsWithSelectedDelivery.click();

//        Шаг 7
//        В выпадающем списке фильтрации выбрать фильтрацию по убыванию цены.

        WebElement sort = driver.findElement(By.xpath("(//span[@data-marker=\"sort/title\"])[1]"));
        sort.click();

        WebElement sortDescPrice = driver.findElement(By.xpath("//button[@data-marker=\"sort/custom-option(2)\"]"));
        sortDescPrice.click();

//        Шаг 8
//        Вывести в консоль название и стоимость 3х самых дорогих принтеров

        List<WebElement> printers = driver.findElements(By.xpath("//div[@data-marker=\"catalog-serp\"]/div[@data-item-id]"));

        for (int i = 0; i < 3; i++) {
            WebElement printer = printers.get(i);
            String name = printer.findElement(By.cssSelector("[itemprop='name']")).getText();
            String price = printer.findElement(By.cssSelector("[itemprop='price']")).getAttribute("content");

            System.out.println("Принтер " + (i + 1));
            System.out.println("Название: " + name);
            System.out.println("Стоимость: " + price);
            System.out.println("------------");

        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}