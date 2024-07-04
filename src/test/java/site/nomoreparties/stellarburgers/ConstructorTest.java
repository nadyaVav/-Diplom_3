package site.nomoreparties.stellarburgers;

import static org.junit.Assert.assertTrue;
import static site.nomoreparties.stellarburgers.config.UrlConstants.BASE_URI;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.config.WebDriverFactory;
import site.nomoreparties.stellarburgers.pages.MainPage;

@Feature("Tab navigation")
public class ConstructorTest extends AbstractTest {

  private WebDriver webDriver;
  MainPage mainPage;

  @Before
  public void setUp() {
    webDriver = WebDriverFactory.getWebDriver();
    webDriver.get(BASE_URI);
    mainPage = new MainPage(webDriver);
  }

  @After
  public void tearDown() {
    webDriver.quit();
  }

  //Проверяем, что по-умолчанию - булочка
  @Test
  @DisplayName("Check default mode for tab navigation on main page")
  @Description("Default mode: tab Buns should be selected as active"
      + "\n User is non authorized")
  public void checkDefaultModeForTabNavigation() {
    assertTrue(mainPage.isBunsTabActive());
  }

  //Проверяем, переход к разделу Соусы
  @Test
  @DisplayName("Check navigation to Sauces tab")
  @Description("After navigation to Sauces tab, the tab should become active"
    + "\n User is non authorized")
  public void checkNavigationToSauces() {
    mainPage.clickSaucesTab();
    assertTrue(mainPage.isSaucesTabActive());
  }

  //Проверяем, переход к разделу Начинки
  @Test
  @DisplayName("Check navigation to Fillings tab")
  @Description("After navigation to Fillings tab, the tab should become active"
    + "\n User is non authorized")
  public void checkNavigationToFillings() {
    mainPage.clickFillingsTab();
    assertTrue(mainPage.isFillingsTabActive());
  }

  //Проверяем, переход к разделу Булочки (после изменения вкладок)
  @Test
  @DisplayName("Check navigation to Buns tab")
  @Description("After navigation to Buns tab, the tab should become active"
    + "\n User is non authorized")
  public void checkNavigationToBuns() {
    mainPage.clickFillingsTab();
    mainPage.clickBunsTab();
    assertTrue(mainPage.isBunsTabActive());
  }

}
