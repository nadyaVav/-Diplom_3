package site.nomoreparties.stellarburgers;

import static org.junit.Assert.assertTrue;
import static site.nomoreparties.stellarburgers.config.UrlConstants.SIGN_IN_URI;

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
import site.nomoreparties.stellarburgers.pages.PersonalAreaPage;
import site.nomoreparties.stellarburgers.pages.RecoveryPasswordPage;
import site.nomoreparties.stellarburgers.pages.SignUpPage;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@Feature("Personal area")
public class PersonalAreaTest extends AbstractTest{

  private User user;
  private UserSteps userSteps = new UserSteps();

  private WebDriver webDriver;
  HeaderPage headerPage;
  LoginPage loginPage;
  SignUpPage signUpPage;
  MainPage mainPage;
  RecoveryPasswordPage recoveryPasswordPage;
  PersonalAreaPage personalAreaPage;

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
    personalAreaPage = new PersonalAreaPage(webDriver);

    webDriver.get(SIGN_IN_URI);
    loginPage.fillClientDataForLogin(user.getEmail(), user.getPassword());
    loginPage.clickSignInButton(mainPage.getCreateOrderButtonLocator());
  }

  @After
  public void tearDown() {
    webDriver.quit();

    if (user.getAccessToken() != null) {
      userSteps.deleteUser(user);
    }
  }

  // Переход в личный кабинет по клику на «Личный кабинет»
  @Test
  @DisplayName("Transfer to personal area by button on header")
  @Description(
      "Transfer to personal area by button on header"
          + "\n User is created using API"
          + "\n User is logged in"
          + "\n After test the user will be deleted using API")
  public void transferToPersonalAreaByButtonInHeader() {
    headerPage.clickOnPersonalAreaButton(personalAreaPage.getProfileLinkLocator());
    assertTrue("Should be displayed profile link on personal area page", personalAreaPage.isPersonalAreaPage());
  }

  // Переход из личного кабинета в конструктор по клику на «Конструктор»
  @Test
  @DisplayName("Transfer to personal area by button on header")
  @Description(
      "Transfer to personal area by button on header"
          + "\n User is created using API"
          + "\n User is logged in"
          + "\n After test the user will be deleted using API")
  public void transferToMainPageByConstructorButton() {

    headerPage.clickOnPersonalAreaButton(personalAreaPage.getProfileLinkLocator());
    headerPage.clickOnConstructorButton(mainPage.getCreateOrderButtonLocator());
    assertTrue("Should be displayed profile link on personal area page", mainPage.isAuthorizeMode());
  }

  // Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers
  @Test
  @DisplayName("Transfer to personal area by logo on header")
  @Description("Transfer to personal area by logo on header"
    + "\n User is created using API"
    + "\n User is logged in"
    + "\n After test the user will be deleted using API")
  public void transferToMainPageByLogo() {

    headerPage.clickOnPersonalAreaButton(personalAreaPage.getProfileLinkLocator());
    headerPage.clickOnLogo(mainPage.getCreateOrderButtonLocator());
    assertTrue("Should be displayed profile link on personal area page", mainPage.isAuthorizeMode());
  }

  // Выход из аккаунта - Выход по кнопке «Выйти» в личном кабинете
  @Test
  @DisplayName("Transfer to personal area by logo on header")
  @Description("Transfer to personal area by logo on header"
    + "\n User is created using API"
    + "\n User is logged in"
    + "\n After test the user will be deleted using API")
  public void logOutOnPersonalArea() {

    headerPage.clickOnPersonalAreaButton(personalAreaPage.getProfileLinkLocator());
    personalAreaPage.clickOnLogOutButton(loginPage.getSignInButtonLocator());
    assertTrue("Should be displayed sign in button on login page", loginPage.isLoginPage());
  }

}
