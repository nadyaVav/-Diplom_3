package site.nomoreparties.stellarburgers.pages;
import static java.time.Duration.ofSeconds;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class MainPage {

  private WebDriver driver;

  private By loginToAccountButtonLocator = By.xpath(".//button[text()='Войти в аккаунт']");
  private By createOrderButtonLocator = By.xpath(".//button[text()='Оформить заказ']");

  private By bunsTabLocator = By.xpath("//span[text()='Булки']");
  private By saucesTabLocator = By.xpath("//span[text()='Соусы']");
  private By fillingsTabLocator = By.xpath("//span[text()='Начинки']");
  private By activeTab = By.className("tab_tab_type_current__2BEPc");

  public MainPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("Click on sign in button main page and ensure that switching is correct")
  public void clickLoginToAccountButton(By element) {
    driver.findElement(loginToAccountButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Ensure that current page is main page (constructor page) in authorization mode")
  public boolean isAuthorizeMode() {
    return driver.findElement(createOrderButtonLocator).isDisplayed();
  }

  @Step("Ensure that current page is main page (constructor page) in authorization mode")
  public boolean isNonAuthorizeMode() {
    return driver.findElement(loginToAccountButtonLocator).isDisplayed();
  }

  @Step("Navigate to tab Bun")
  public void clickBunsTab() {
    driver.findElement(bunsTabLocator).click();
  }

  @Step("Ensure that tab Bun is active and selected")
  public boolean isBunsTabActive() {
    return driver.findElement(activeTab).getText().equals("Булки");
  }

  @Step("Navigate to tab Sauces")
  public void clickSaucesTab() {
    driver.findElement(saucesTabLocator).click();
  }

  @Step("Ensure that tab Sauces is active and selected")
  public boolean isSaucesTabActive() {
    return driver.findElement(activeTab).getText().equals("Соусы");
  }

  @Step("Navigate to tab Fillings")
  public void clickFillingsTab() {
    driver.findElement(fillingsTabLocator).click();
  }

  @Step("Ensure that tab Fillings is active and selected")
  public boolean isFillingsTabActive() {
    return driver.findElement(activeTab).getText().equals("Начинки");
  }

}
