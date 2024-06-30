package site.nomoreparties.stellarburgers.pages;

import static java.time.Duration.ofSeconds;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class PersonalAreaPage {

  private WebDriver driver;

  private By profileLinkLocator = By.xpath(".//a[text()='Профиль']");
  private By logOutButtonLocator = By.xpath(".//button[text()='Выход']");

  public PersonalAreaPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("Ensure that current page is personal area page")
  public boolean isPersonalAreaPage() {
    return driver.findElement(profileLinkLocator).isDisplayed();
  }

  @Step("Click on log out button on personal area page and ensure that switching is correct")
  public void clickOnLogOutButton(By element) {
    driver.findElement(logOutButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

}
