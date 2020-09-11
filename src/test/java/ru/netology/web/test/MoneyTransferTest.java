package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {
  int startingBalance1;
  int startingBalance2;
  int finishingBalance1;
  int finishingBalance2;
  int sum;
  DashboardPage dashboardPage;

  @BeforeEach
  void SetUp() {
    val loginPage = open("http://localhost:9999", LoginPage.class);
    val authInfo = getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = getVerificationCodeFor(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
    startingBalance1 = dashboardPage.getBalance(getFirstCard().getId());
    startingBalance2 = dashboardPage.getBalance(getSecondCard().getId());
  }

  @Test
  void shouldSendMoneyToFirstTest() {
    sum = 100;
    val transferPage = dashboardPage.clickTransfer(getFirstCard().getNumber());
    val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), getSecondCard().getNumber());
    finishingBalance1 = dashboardPage2.getBalance(getFirstCard().getId());
    finishingBalance2 = dashboardPage2.getBalance(getSecondCard().getId());
    assertEquals(startingBalance1 + sum, finishingBalance1);
    assertEquals(startingBalance2 - sum, finishingBalance2);
  }

  @Test
  void shouldSendMoneyToSecondTest() {
    sum = 100;
    val transferPage = dashboardPage.clickTransfer(getSecondCard().getNumber());
    val dashboardPage2 = transferPage.successfulTransfer(Integer.toString(sum), getFirstCard().getNumber());
    finishingBalance1 = dashboardPage2.getBalance(getFirstCard().getId());
    finishingBalance2 = dashboardPage2.getBalance(getSecondCard().getId());
    assertEquals(startingBalance1 - sum, finishingBalance1);
    assertEquals(startingBalance2 + sum, finishingBalance2);
  }
  
}

