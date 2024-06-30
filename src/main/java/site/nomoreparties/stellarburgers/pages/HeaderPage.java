package site.nomoreparties.stellarburgers.pages;

import static java.time.Duration.ofSeconds;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class HeaderPage {


  private WebDriver driver;
  private By personalAreaButtonLocator = By.xpath(".//p[text()='Личный Кабинет']");
  private By constructorButtonLocator = By.xpath(".//p[text()='Конструктор']");
  private By feedOrderButtonLocator = By.xpath(".//p[text()='Лента заказов']");
  private By logoLocator = By.xpath(".//div[@class= 'AppHeader_header__logo__2D0X2']/a");

  public HeaderPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("Click on personal area button in the header")
  public void clickOnPersonalAreaButton(By element) {
    driver.findElement(personalAreaButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Click on Constructor button in the header")
  public void clickOnConstructorButton(By element) {
    driver.findElement(constructorButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Click on the main logo in the header")
  public void clickOnLogo(By element) {
    driver.findElement(logoLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

}
