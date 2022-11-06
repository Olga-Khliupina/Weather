import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class OlgaKhliupinaTest {

    static String url = "https://openweathermap.org/";

    static boolean isTempInSymbol(WebDriver driver, String temp, String symbolTemp) throws InterruptedException {

        WebElement tempUnit = driver.findElement(By.xpath(String.format("//div[text()='%s']", temp)));
        tempUnit.click();
        Thread.sleep(1000);

        WebElement tempUnitHeading = driver.findElement(By.xpath("//div[@class='current-temp']/span"));

        return tempUnitHeading.getText().contains(symbolTemp);
    }

    //    TC_1_1  - Тест кейс:
    //    //1. Открыть страницу https://openweathermap.org/
    //    //2. Набрать в строке поиска город Paris
    //    //3. Нажать пункт меню Search
    //    //4. Из выпадающего списка выбрать Paris, FR
    //    //5. Подтвердить, что заголовок изменился на "Paris, FR"

   @Test
   public void testH2TagText_WhenSearchingCityCountry() throws InterruptedException {

       System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
       WebDriver driver = new ChromeDriver();

       String cityName = "Paris";
       String expectedResult = "Paris, FR";

       driver.manage().window().maximize();
       driver.get(url);
       Thread.sleep(5000);

       WebElement searchCityField = driver.findElement(
               By.xpath("//div[@id='weather-widget']//input[@placeholder='Search city']"));
       searchCityField.click();
       searchCityField.sendKeys(cityName);

       WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
       searchButton.click();
       Thread.sleep(1000);

       WebElement parisFranceChoiceInDropdownMenu = driver.findElement(
               By.xpath("//ul[@class='search-dropdown-menu']/li/span[text()='Paris, FR ']"));
       parisFranceChoiceInDropdownMenu.click();

       WebElement h2CityCountryHeader = driver.findElement(By.xpath("//div[@id='weather-widget']//h2"));

       Thread.sleep(2000);
       String actualResult = h2CityCountryHeader.getText();

       Assert.assertEquals(actualResult, expectedResult);

       driver.quit();
   }

   // TC_11_01
    //1.  Открыть базовую ссылку
    //2.  Нажать на пункт меню Guide
    //3.  Подтвердить, что вы перешли на страницу со ссылкой https://openweathermap.org/guide и что
    // title этой страницы OpenWeatherMap API guide - OpenWeatherMap

    @Test
    public void testLinkAndTitle_WhenGoingToGuideMenu() throws InterruptedException {

       System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
       WebDriver driver = new ChromeDriver();

       String expectedResultUrl = "https://openweathermap.org/guide";
       String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";

       driver.manage().window().maximize();
       driver.get(url);
       Thread.sleep(5000);

       WebElement menuGuide = driver.findElement(By.xpath("//div/ul//li/a[@href='/guide']"));
       menuGuide.click();
       Thread.sleep(1000);

       String actualResultUrl = driver.getCurrentUrl();

       Assert.assertEquals(actualResultUrl, expectedResultUrl);

        String actualResultTitle = driver.getTitle();

        Assert.assertEquals(actualResultTitle, expectedResultTitle);

        driver.quit();
    }

    // TC_11_02
    //1.  Открыть базовую ссылку
    //2.  Нажать на единицы измерения Imperial: °F, mph
    //3.  Подтвердить, что температура для города показана в Фарингейтах

    @Test
    public void testChangingTempUnitInHeading_WhenSwitchTempUnitButton() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);

        Assert.assertTrue(isTempInSymbol(driver, "Imperial: °F, mph", "°F"));

        driver.quit();
    }

        // TC_11_03
        //1.  Открыть базовую ссылку
        //2. Подтвердить, что внизу страницы есть панель с текстом “We use cookies which are essential for the
        // site to work. We also use non-essential cookies to help us improve our services. Any data collected
        // is anonymised. You can allow all cookies or manage them individually.”
        //3. Подтвердить, что на панели внизу страницы есть 2 кнопки “Allow all” и “Manage cookies”

        @Test
        public void testCookiePanel_BottomMainPage_WhenOpenWebsite() throws InterruptedException {

            System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
            WebDriver driver = new ChromeDriver();

            String expectedResult_CookieText = "We use cookies which are essential for the site to work. We also use non-essential " +
                    "cookies to help us improve our services. Any data collected is anonymised. You can allow all " +
                    "cookies or manage them individually.";
            String expectedResult_ButtonAllowAll = "Allow all";
            String expectedResult_ButtonManageCookies = "Manage cookies";

            driver.manage().window().maximize();
            driver.get(url);
            Thread.sleep(5000);

            WebElement cookieBanner = driver.findElement(
                    By.className("stick-footer-panel__description"));

            String actualResult_CookieText = cookieBanner.getText();

            Assert.assertEquals(actualResult_CookieText, expectedResult_CookieText);

            WebElement buttonAllowAll = driver.findElement(By.xpath("//div/button[@type='button']"));

            String actualResult_ButtonAllowAll = buttonAllowAll.getText();

            Assert.assertEquals(actualResult_ButtonAllowAll, expectedResult_ButtonAllowAll);

            WebElement buttonManageCookies = driver.findElement(
                    By.xpath("//div/a[@href='/cookies-settings']"));

            String actualResult_ManageCookies = buttonManageCookies.getText();

            Assert.assertEquals(actualResult_ManageCookies, expectedResult_ButtonManageCookies);

            driver.quit();
        }

        // TC_11_04
        //1.  Открыть базовую ссылку
        //2.  Подтвердить, что в меню Support есть 3 подменю с названиями “FAQ”, “How to start” и “Ask a question”

    @Test
    public void testExistFAQHowToStartAskAQuestion_InSupportDropdownMenu() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        String expectedResultFaq = "FAQ";
        String expectedResultHowToStart = "How to start";
        String expectedResultAskAQuestion = "Ask a question";

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        Thread.sleep(1000);
        WebElement dropdownFAQ = driver.findElement(By.xpath("//nav/ul/div/ul//li/a[@href='/faq']"));

        String actualResultFaq = dropdownFAQ.getText();

        Assert.assertEquals(actualResultFaq, expectedResultFaq);

        WebElement dropdownHowToStart = driver.findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li/a[@href='/appid']"));

        String actualResultHowToStart = dropdownHowToStart.getText();

        Assert.assertEquals(actualResultHowToStart, expectedResultHowToStart);

        WebElement dropdownAskAQuestion = driver.findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li/a[@href='https://home.openweathermap.org/questions']"));

        String actualResultAskAQuestion = dropdownAskAQuestion.getText();

        Assert.assertEquals(actualResultAskAQuestion, expectedResultAskAQuestion);

        driver.quit();
    }

        // TC_11_05
        //1. Открыть базовую ссылку
        //2. Нажать пункт меню Support → Ask a question
        //3. Заполнить поля Email, Subject, Message
        //4. Не подтвердив CAPTCHA, нажать кнопку Submit
        //5. Подтвердить, что пользователю будет показана ошибка “reCAPTCHA verification failed, please try again.”

    @Test
    public void testError_WhenSubmitInSupportWithoutCaptcha() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        String eMail = "tester@test.com";
        String subject = "Other";
        String message = "lalala";
        String expectedResult = "reCAPTCHA verification failed, please try again.";

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(   3000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        Thread.sleep(500);

        WebElement dropDownAskAQuestion = driver.findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li/a[@href='https://home.openweathermap.org/questions']"));
        dropDownAskAQuestion.click();
        Thread.sleep(5000);

        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
//                break;
            }
        }
        Thread.sleep(3000);

        WebElement emailField = driver.findElement(By.id("question_form_email"));
        emailField.click();
        emailField.sendKeys(eMail);

        WebElement subjectField = driver.findElement(By.id("question_form_subject"));
        subjectField.click();
        subjectField.sendKeys(subject);

        WebElement messageField = driver.findElement(By.id("question_form_message"));
        messageField.click();
        messageField.sendKeys(message);

        WebElement submitButton = driver.findElement(By.xpath("//div/input[@type='submit']"));
        submitButton.click();

        WebElement errorText = driver.findElement(By.xpath("//div[@class = 'help-block']"));

        String actualResult = errorText.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.close();
        driver.quit();
    }

    // TC_11_06
    //1.  Открыть базовую ссылку
    //2.  Нажать пункт меню Support → Ask a question
    //3.  Оставить значение по умолчанию в checkbox Are you an OpenWeather user?
    //4. Оставить пустым поле Email
    //5. Заполнить поля  Subject, Message
    //6. Подтвердить CAPTCHA
    //7. Нажать кнопку Submit
    //8. Подтвердить, что в поле Email пользователю будет показана ошибка “can't be blank”

    @Test
    public void testError_WhenSubmitInSupportAskAQuestionWithoutFillEmail() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        String subject = "Other";
        String message = "lalala";
        String expectedResult = "can't be blank";

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(   3000);

        WebElement menuSupport = driver.findElement(By.xpath("//div[@id='support-dropdown']"));
        menuSupport.click();
        Thread.sleep(500);

        WebElement dropDownAskAQuestion = driver.findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li/a[@href='https://home.openweathermap.org/questions']"));

        dropDownAskAQuestion.click();
        Thread.sleep(5000);

        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        Thread.sleep(3000);

        WebElement subjectField = driver.findElement(By.id("question_form_subject"));
        subjectField.click();
        subjectField.sendKeys(subject);

        WebElement messageField = driver.findElement(By.id("question_form_message"));
        messageField.click();
        messageField.sendKeys(message);

//        WebDriverWait wait = new WebDriverWait(driver, 20);

//        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
//                By.xpath("//div/iframe[@title='reCAPTCHA']")));

        WebElement captchaFrame = driver.findElement(By.xpath("//div/iframe[@title='reCAPTCHA']"));
        driver.switchTo().frame(captchaFrame);
        Thread.sleep(2000);

        WebElement captchaBox = driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-border']"));
        captchaBox.click();
        Thread.sleep(15000);

        driver.switchTo().defaultContent();

        WebElement submitButton = driver.findElement(By.xpath("//div/input[@type='submit']"));
        Thread.sleep(2000);
        submitButton.click();

        WebElement errorEmail = driver.findElement(By.xpath("//div/span[@class='help-block']"));
        String actualResult = errorEmail.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.close();
        driver.quit();
    }

    // TC_11_07
    //1.  Открыть базовую ссылку
    //2.  Нажать на единицы измерения Imperial: °F, mph
    //3.  Нажать на единицы измерения Metric: °C, m/s
    //4.  Подтвердить, что в результате этих действий, единицы измерения температуры изменились с F на С

    @Test
    public void testChangeFromFToC() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);

        Assert.assertTrue(isTempInSymbol(driver, "Imperial: °F, mph", "°F"));
        Assert.assertTrue(isTempInSymbol(driver, "Metric: °C, m/s", "°C"));

        driver.quit();
    }
    
    // TC_11_08
    //1.  Открыть базовую ссылку
    //2.  Нажать на лого компании
    //3.  Дождаться, когда произойдет перезагрузка сайта, и подтвердить, что текущая ссылка не изменилась

    @Test
    public  void testCheckUrlAfterRefreshMainPage() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        String expectedResult = url;

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement logo = driver.findElement(
                By.xpath("//img[@src='/themes/openweathermap/assets/img/logo_white_cropped.png']"));
        logo.click();
        Thread.sleep(5000);

        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }

    //TC_11_09
    //1.  Открыть базовую ссылку
    //2.  В строке поиска в навигационной панели набрать “Rome”
    //3.  Нажать клавишу Enter
    //4.  Подтвердить, что вы перешли на страницу в ссылке которой содержатся слова “find” и “Rome”
    //5. Подтвердить, что в строке поиска на новой странице вписано слово “Rome”

    @Test
    public void testContentInLinkAndInSearchField_WhenGoToCityNavigation() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        String cityName = "Rome";
        String expectedResult = "Rome";

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(   3000);

        WebElement searchFieldWheatherInYourCity = driver.findElement(
                By.xpath("//div[@id='desktop-menu']/form/input[@placeholder='Weather in your city']"));
        searchFieldWheatherInYourCity.click();
        searchFieldWheatherInYourCity.sendKeys(cityName + Keys.ENTER);
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("find"));
        Assert.assertTrue(driver.getCurrentUrl().contains(cityName));

        WebElement searchField = driver.findElement(By.xpath("//input[@id='search_str']"));

        Assert.assertEquals(searchField.getAttribute("value"), expectedResult);

        driver.quit();
    }

    // TC_11_10
    //1.  Открыть базовую ссылку
    //2.  Нажать на пункт меню API
    //3.  Подтвердить, что на открывшейся странице пользователь видит 30 оранжевых кнопок

    @Test
    public void testFindQuantityOfOrangeButtons() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        WebDriver driver = new ChromeDriver();

        int expectedResult = 30;

        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);

        WebElement apiMenu = driver.findElement(
                By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/api']"));
        apiMenu.click();
        Thread.sleep(1000);

        List<WebElement> buttons = driver.findElements(
                By.xpath("//a[@type='button' and contains(@class,'orange') or contains(@class, 'btn-orange')]"));
        int actualResult = buttons.size();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }


}
