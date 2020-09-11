package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private ElementsCollection transferButtons = $$("button[data-test-id=action-deposit]");
  private SelenideElement card1 = $("div[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
  private SelenideElement card2 = $("div[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");


  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public TransferPage clickTransfer(String cardNumber) {
    val cardButtonInfo = cardsInfo.find(text(cardNumber.substring(15)));
    cardButtonInfo.$("button[data-test-id=action-deposit]").click();
    return new TransferPage();
  }

  public int getBalance(String id) {
    String text = "";
    for (SelenideElement cardInfo : cardsInfo) {
      val tempCard = cardInfo.$("[data-test-id='" + id + "']");
      if (tempCard.exists()) {
        text = tempCard.getText();
      }
    }
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
}
