package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
  int startingBalance1;
  int startingBalance2;
  int finishingBalance1;
  int finishingBalance2;
  int sum;
  DashboardPage dashboardPage;

  @BeforeEach
  void SetUp() {
    open("http://localhost:9999");
    val loginPage = new LoginPage();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
    startingBalance1 = dashboardPage.getBalance(dashboardPage.card1);
    startingBalance2 = dashboardPage.getBalance(dashboardPage.card2);
  }

  @Test
  void shouldSendMoneyToFirstTest() {
    sum = 100;
    val transferPage = dashboardPage.clickTransfer(dashboardPage.card1);
    val cardNum = DataHelper.getSecondCard().getNumber();
    val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), cardNum);
    finishingBalance1 = dashboardPage2.getBalance(dashboardPage2.card1);
    finishingBalance2 = dashboardPage2.getBalance(dashboardPage2.card2);
    assertEquals(startingBalance1 + sum, finishingBalance1);
    assertEquals(startingBalance2 - sum, finishingBalance2);
  }

  @Test
  void shouldSendMoneyToSecondTest() {
    sum = 100;
    val transferPage = dashboardPage.clickTransfer(dashboardPage.card2);
    val cardNum = DataHelper.getFirstCard().getNumber();
    val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), cardNum);
    finishingBalance1 = dashboardPage2.getBalance(dashboardPage2.card1);
    finishingBalance2 = dashboardPage2.getBalance(dashboardPage2.card2);
    assertEquals(startingBalance1 - sum, finishingBalance1);
    assertEquals(startingBalance2 + sum, finishingBalance2);
  }
  
}

