import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

public class SentMessageThread extends MyRunnable {
    private ArrayList<MessageWhatsapp> messageWhatsappArrayList;
    private final LoginThread loginThread;
    private static final String SEND_MESSAGE_TAG = "footer";
    private static final String CSS_BOX_TEXT = "div[role='textbox']";
    private static final String CSS_BUTTON_MESSAGE = "button[class='tvf2evcx oq44ahr5 lb5m6g5c svlsagor p2rjqpw5 epia9gcq'";

    public SentMessageThread(Start start, ArrayList<MessageWhatsapp> messageWhatsappArrayList,LoginThread loginThread) {
        super(start);
        this.loginThread=loginThread;
        this.messageWhatsappArrayList = messageWhatsappArrayList;
    }

    public void _run() {
        if (!loginThread.isRunning()) {
            for (MessageWhatsapp message : messageWhatsappArrayList) {
                getStart().setDriverPage(message.getFormatPhoneNumber());
                if (!message.isSent()) {
                    do {
                        Util.sleep(Const.SEC * 5);
                        sendMessage(message);
                        Util.sleep(Const.SEC * 5);
                    } while (!message.isSent());
                }
            }
        }
        assert this.messageWhatsappArrayList != null;
        if (messageWhatsappArrayList.stream().allMatch(MessageWhatsapp::isSent)) {
            System.out.println("done");
            stop();
        }
    }

    public void sendMessage(MessageWhatsapp messageWhatsapp) {
        if (!messageWhatsapp.isSent()) {
            WebElement text = null;
            try {
                text = getStart().getDriver().findElement(By.tagName(SEND_MESSAGE_TAG));
                WebElement textBox = text.findElement(By.cssSelector(CSS_BOX_TEXT));
                if (textBox != null) {
                    textBox.sendKeys(messageWhatsapp.getMessage());
                    text.findElement(By.cssSelector(CSS_BUTTON_MESSAGE)).click();
                    messageWhatsapp.sent();
                }
                getStart().getStatusMessageList().get(messageWhatsapp).setText(messageWhatsapp.getPhoneNumber() + "Status message---> sent");
            } catch (Exception ignored) {
            }
        }
    }
}
//
//    @Override
//    public void _run() {
//        if (!messageWhatsapp.isSent()) {
//            WebElement text = null;
//            try {
//                text = getStart().getDriver().findElement(By.tagName(Start.SEND_MESSAGE_TAG));
//                WebElement textBox = text.findElement(By.cssSelector(Start.CSS_BOX_TEXT));
//                Util.sleep(Const.SEC * 4);
//                if (textBox != null) {
//                    textBox.sendKeys(messageWhatsapp.getMessage());
//                    text.findElement(By.cssSelector(Start.CSS_BUTTON_MESSAGE)).click();
//                    messageWhatsapp.sent();
//                    Util.sleep(Const.SEC * 4);
//                }
//
//            } catch (Exception ignored) {
//                System.out.println(messageWhatsapp.getFormatPhoneNumber() + messageWhatsapp.isSent());
//            }
//        } else {
//            getStart().getStatusMessageList().get(messageWhatsapp).setText(messageWhatsapp.getFormatPhoneNumber() + " " + messageWhatsapp.getStatus());
//            getStart().repaint();
//            stop();
//        }
//
//    }
//}
