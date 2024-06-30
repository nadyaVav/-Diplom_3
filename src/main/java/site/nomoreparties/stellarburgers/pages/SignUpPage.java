package site.nomoreparties.stellarburgers.pages;

import static java.time.Duration.ofSeconds;

import io.qameta.allure.Step;
import lombok.Data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class SignUpPage {

  private WebDriver driver;

  private By signUpButtonLocator = By.xpath(".//button[text()='Зарегистрироваться']");
  private By signInLinkLocator = By.xpath(".//a[text()='Войти']");
  private By nameFieldLocator = By.xpath(".//label[text()='Имя']/parent::div/input[@class = 'text input__textfield text_type_main-default']");
  private By emailFieldLocator = By.xpath(".//label[text()='Email']/parent::div/input[@class = 'text input__textfield text_type_main-default']");
  private By passwordFieldLocator = By.xpath(".//label[text()='Пароль']/parent::div/input[@class = 'text input__textfield text_type_main-default']");
  private By incorrectPasswordMessageLocator = By.xpath(".//p[text()='Некорректный пароль']");

  public SignUpPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("Fill inputs Name, Email and password on sign up page")
  public void fillClientDataForRegistration(String name, String email, String password) {

    driver.findElement(nameFieldLocator).sendKeys(name);
    driver.findElement(emailFieldLocator).sendKeys(email);
    driver.findElement(passwordFieldLocator).sendKeys(password);
  }

  @Step("Ensure that error message about incorrect password is displayed")
  public boolean isIncorrectPasswordMessageLocator() {
    return driver.findElement(incorrectPasswordMessageLocator).isDisplayed();
  }

  @Step("Click on sign up button on sign up page and ensure that switching is correct")
  public void clickOnSignUpButton(By element) {
    driver.findElement(signUpButtonLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  @Step("Click on signIn link on sign up page and ensure that switching is correct")
  public void clickOnSignInLink(By element) {
    driver.findElement(signInLinkLocator).click();
    new WebDriverWait(driver, ofSeconds(3))
      .until(ExpectedConditions.visibilityOfElementLocated(element));
  }

}
