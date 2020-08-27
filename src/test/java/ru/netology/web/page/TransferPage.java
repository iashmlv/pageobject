package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class TransferPage {
    private SelenideElement sumField = $("div[data-test-id=amount] input");
    private SelenideElement accountField = $("span[data-test-id=from] input");
    private SelenideElement topUpButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");

    public DashboardPage successfulTransfer(String sum, String cardNum) {
        sumField.doubleClick().sendKeys(Keys.BACK_SPACE);
        sumField.setValue(sum);
        accountField.doubleClick().sendKeys(Keys.BACK_SPACE);
        accountField.setValue(cardNum);
        sleep(5000);
        topUpButton.click();
        return new DashboardPage();
    }

    public void unsuccessfulTransfer(String sum, String cardNum) {
        sumField.doubleClick().sendKeys(Keys.BACK_SPACE);
        sumField.setValue(sum);
        errorNotification.shouldBe(Condition.visible);
    }
}
