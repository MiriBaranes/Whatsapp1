import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class SentMessageThread extends MyRunnable {
    private ArrayList<MessageWhatsapp> messageWhatsappArrayList;
    private final LoginThread loginThread;
    private static final String ERROR_MESSAGE = "_2Nr6U";
    //"_2i3w0";
    private static final String BUTTON_ERROR_CSS = "div[role='button'][tabindex='0'][class='_20C5O _2Zdgs']";
    private static final String SEND_MESSAGE_TAG = "footer";
    private static final String CSS_BOX_TEXT = "div[role='textbox']";
    private static final String CSS_BUTTON_MESSAGE = "button[class='tvf2evcx oq44ahr5 lb5m6g5c svlsagor p2rjqpw5 epia9gcq'";

    public SentMessageThread(StartSystemDriver startSystemDriver, ArrayList<MessageWhatsapp> messageWhatsappArrayList, LoginThread loginThread) {
        super(startSystemDriver);
        this.loginThread = loginThread;
        this.messageWhatsappArrayList = messageWhatsappArrayList;
    }


    public void _run() {
        if (!loginThread.isRunning()) {
            for (MessageWhatsapp message : messageWhatsappArrayList) {
                if (!message.isSent() && message.getTypeSent() != MessageWhatsapp.ERROR_STATUS_INT) {
                    Util.sleep(Const.SEC);
                    getStart().setDriverPage(message.getFormatPhoneNumber());
                    do {
                        sendMessage(message);
                        Util.sleep(Const.SEC * 2);
                    } while (!message.isSent() && message.getTypeSent() != MessageWhatsapp.ERROR_STATUS_INT);
                }
            }
        }
        assert this.messageWhatsappArrayList != null;
        messageWhatsappArrayList = getStart().removeAllError();
        if (messageWhatsappArrayList.stream().allMatch(MessageWhatsapp::isSent)) {
            stop();
        }
    }

    @Override
    public void stop() {
        super.stop();
        StatusMessageThread statusMessageThread = new StatusMessageThread(getStart(), this.messageWhatsappArrayList);
        new Thread(statusMessageThread).start();
    }

    public void sendMessage(MessageWhatsapp messageWhatsapp) {
        if (!messageWhatsapp.isSent() && !errorNumber(messageWhatsapp)) {
            WebElement text;
            try {
                text = getStart().getDriver().findElement(By.tagName(SEND_MESSAGE_TAG));
                WebElement textBox = text.findElement(By.cssSelector(CSS_BOX_TEXT));
                if (textBox != null) {
                    textBox.sendKeys(messageWhatsapp.getMessage());
                    WebElement clicked = text.findElement(By.cssSelector(CSS_BUTTON_MESSAGE));
                    if (clicked != null) {
                        clicked.click();
                        messageWhatsapp.setTypeSent(MessageWhatsapp.DELIVERED);
                    }
                }
                this.getStart().setJLabelStatusByMessage(messageWhatsapp);

            } catch (Exception ignored) {
            }
        }

    }

    public boolean errorNumber(MessageWhatsapp messageWhatsapp) {
        boolean isError = false;
        try {
            WebElement errorClass = getStart().getDriver().findElement(By.className(ERROR_MESSAGE));
            if (errorClass != null) {
                WebElement classType = errorClass.findElement(By.className("_2i3w0"));
                if (classType != null) {
                    WebElement buttonError = getStart().getDriver().findElement(By.cssSelector(BUTTON_ERROR_CSS));
                    if (buttonError != null) {
                        buttonError.click();
                        getStart().addErrorMessages(messageWhatsapp);
                        messageWhatsapp.setTypeSent(MessageWhatsapp.ERROR_STATUS_INT);
                        getStart().setJLabelStatusByMessage(messageWhatsapp);
                        isError = true;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return isError;
    }
}
