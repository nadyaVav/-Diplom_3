package site.nomoreparties.stellarburgers.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {

  private static WebDriver webDriver;

  public static WebDriver getWebDriver() {

    if (webDriver == null) {

      InputStream resourceAsStream =
        WebDriverFactory.class.getResourceAsStream("/config.properties");
      Properties properties = new Properties();
      try {
        properties.load(resourceAsStream);
      } catch (IOException e) {
        System.out.println("gfff");
    }

      String browserType = properties.getProperty("browser");

      switch (browserType.toLowerCase()) {
        case "chrome":
          WebDriverManager.chromedriver().create();
          return new ChromeDriver();
        case "yandex":
          System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/yandexdriver.exe");
          return new ChromeDriver();
        case "firefox":
          WebDriverManager.firefoxdriver().clearDriverCache().create();
          return new FirefoxDriver();
        default:
          throw new RuntimeException("Unsupported browser: " + browserType);
      }
    }
    return webDriver;
  }

}
