package site.nomoreparties.stellarburgers;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertTrue;
import static site.nomoreparties.stellarburgers.config.UrlConstants.SIGN_UP_URI;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.config.WebDriverFactory;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.pages.HeaderPage;
import site.nomoreparties.stellarburgers.pages.LoginPage;
import site.nomoreparties.stellarburgers.pages.SignUpPage;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@Feature("Sign Up user")
public class SignUpUserTest extends AbstractTest{

  private User user;
  private UserSteps userSteps = new UserSteps();

  private WebDriver webDriver;
  HeaderPage headerPage;
  LoginPage loginPage;
  SignUpPage signUpPage;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    webDriver = WebDriverFactory.getWebDriver();
    webDriver.get(SIGN_UP_URI);

    headerPage = new HeaderPage(webDriver);
    loginPage = new LoginPage(webDriver);
    signUpPage = new SignUpPage(webDriver);

    Faker faker = new Faker(new Locale("en-GB"));

    user = new User();
    user.setName(faker.name().firstName());
    user.setPassword(faker.internet().password(6, 10));
    user.setEmail(faker.internet().emailAddress());
  }

  @After
  public void tearDown() {
    webDriver.quit();

    if (user.getAccessToken() != null) {
      userSteps.deleteUser(user);
    }
  }

  @Test
  @DisplayName("SignUp new user (correct data)")
  @Description("Test for checking registration new user"
    + "\n After test case user will be deleted")
  public void registerUserWithCorrectData() {

    signUpPage.fillClientDataForRegistration(user.getName(), user.getEmail(), user.getPassword());
    signUpPage.clickOnSignUpButton(loginPage.getSignInButtonLocator());
    assertTrue("User should be on login page after registration", loginPage.isLoginPage());

    String accessToken = userSteps.
      loginUser(user).
      then()
      .extract().body().path("accessToken");

    user.setAccessToken(accessToken);
  }

  @Test
  @DisplayName("SignUp new user with password less 6 symbols")
  @Description("Test for checking registration user with incorrect password ")
  public void registerUserWithIncorrectPassword() {

    user.setPassword(randomAlphabetic(5));

    headerPage.clickOnPersonalAreaButton(loginPage.getSignInButtonLocator());
    loginPage.clickSignUpLink(signUpPage.getSignUpButtonLocator());

    signUpPage.fillClientDataForRegistration(user.getName(), user.getEmail(), user.getPassword());
    signUpPage.clickOnSignUpButton(signUpPage.getIncorrectPasswordMessageLocator());
    assertTrue("Error message should be correct", signUpPage.isIncorrectPasswordMessageLocator());

  }

}
