package site.nomoreparties.stellarburgers.pages;

import static java.time.Duration.ofSeconds;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class LoginPage {

  private WebDriver driver;

  private By signUpLinkLocator = By.xpath(".//a[text()='Зарегистрироваться']");
  private By signInButtonLocator = By.xpath(".//button[text()='Войти']");
  private By emailFieldLocator = By.xpath(".//label[text()='Email']/parent::div/input[@class = 'text input__textfield text_type_main-default']");
  private By passwordFieldLocator = By.xpath(".//label[text()='Пароль']/parent::div/input[@class = 'text input__textfield text_type_main-default']");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("Click on sign up link on login page")
  public void clickSignUpLink(By element) {
    driver.findElement(signUpLinkLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Click on sign in button on login page")
  public void clickSignInButton(By element) {
    driver.findElement(signInButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Ensure that current page is login page")
  public boolean isLoginPage() {
    return driver.findElement(signInButtonLocator).isDisplayed();
  }

  @Step("Fill inputs email and password on login page" )
  public void fillClientDataForLogin(String email, String password) {
    driver.findElement(emailFieldLocator).sendKeys(email);
    driver.findElement(passwordFieldLocator).sendKeys(password);
  }
}
