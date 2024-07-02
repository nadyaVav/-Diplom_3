package site.nomoreparties.stellarburgers;
import static org.junit.Assert.assertTrue;
import static site.nomoreparties.stellarburgers.config.UrlConstants.BASE_URI;
import static site.nomoreparties.stellarburgers.config.UrlConstants.RECOVERY_PASSWORD_URI;
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
import site.nomoreparties.stellarburgers.pages.MainPage;
import site.nomoreparties.stellarburgers.pages.RecoveryPasswordPage;
import site.nomoreparties.stellarburgers.pages.SignUpPage;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@Feature("Login user")
public class LoginUserTest extends AbstractTest {

  private User user;
  private UserSteps userSteps = new UserSteps();

  private WebDriver webDriver;
  HeaderPage headerPage;
  LoginPage loginPage;
  SignUpPage signUpPage;
  MainPage mainPage;
  RecoveryPasswordPage recoveryPasswordPage;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    Faker faker = new Faker(new Locale("en-GB"));
    user = new User();
    user.setName(faker.name().firstName());
    user.setPassword(faker.internet().password(6, 10));
    user.setEmail(faker.internet().emailAddress());

    String accessToken = userSteps.
      createUser(user).
      then()
      .extract().body().path("accessToken");
    user.setAccessToken(accessToken);

    webDriver = WebDriverFactory.getWebDriver();

    headerPage = new HeaderPage(webDriver);
    loginPage = new LoginPage(webDriver);
    signUpPage = new SignUpPage(webDriver);
    mainPage = new MainPage(webDriver);
    recoveryPasswordPage = new RecoveryPasswordPage(webDriver);

    webDriver.get(BASE_URI);
  }
  @Test
  @DisplayName("Login using \"Log in to account\" button on main page")
  @Description("Test for checking login user using \"Log in to account\" button on main page"
    + "\n User is created using API"
    + "\n After test the user will be deleted using API")
  public void loginOnMainPageByCreateOrderButton() {

    mainPage.clickLoginToAccountButton(loginPage.getSignInButtonLocator());

    loginPage.fillClientDataForLogin(user.getEmail(), user.getPassword());
    loginPage.clickSignInButton(mainPage.getCreateOrderButtonLocator());

    assertTrue("Should be displayed \"Create order\" button", mainPage.isAuthorizeMode());
  }

  //вход через кнопку «Личный кабинет»
  @Test
  @DisplayName("Login using personal area button")
  @Description("Test for checking login user using button on header"
    + "\n User is created using API"
    + "\n After test the user will be deleted using API")
  public void loginOnHeaderByPersonalAreaButton() {

    headerPage.clickOnPersonalAreaButton(loginPage.getSignInButtonLocator());
    loginPage.fillClientDataForLogin(user.getEmail(), user.getPassword());
    loginPage.clickSignInButton(mainPage.getCreateOrderButtonLocator());

    assertTrue("Should be displayed \"Create order\" button", mainPage.isAuthorizeMode());
  }

  // вход через кнопку в форме регистрации
  @Test
  @DisplayName("Login using the link on sign up form")
  @Description(
      "Test for checking login user using the link on sign up form"
          + "\n User is created using API"
          + "\n After test the user will be deleted using API")
  public void loginOnSignUpFormBySignInLink() {

    webDriver.get(SIGN_UP_URI);
    signUpPage.clickOnSignInLink(loginPage.getSignInButtonLocator());
    loginPage.fillClientDataForLogin(user.getEmail(), user.getPassword());
    loginPage.clickSignInButton(mainPage.getCreateOrderButtonLocator());

    assertTrue("Should be displayed \"Create order\" button", mainPage.isAuthorizeMode());
  }

  // вход через кнопку в форме восстановления пароля.
  @Test
  @DisplayName("Login using the link on sign up form")
  @Description(
      "Test for checking login user using the link on sign up form"
          + "\n User is created using API"
          + "\n After test the user will be deleted using API")
  public void loginOnPasswordRecoveryPageBySignInLink() {

    webDriver.get(RECOVERY_PASSWORD_URI);
    recoveryPasswordPage.clickOnSignInLink(loginPage.getSignInButtonLocator());
    loginPage.fillClientDataForLogin(user.getEmail(), user.getPassword());
    loginPage.clickSignInButton(mainPage.getCreateOrderButtonLocator());

    assertTrue("Should be displayed \"Create order\" button", mainPage.isAuthorizeMode());
  }

  @After
  public void tearDown() {
    webDriver.quit();

    if (user.getAccessToken() != null) {
      userSteps.deleteUser(user);
    }
  }

}
