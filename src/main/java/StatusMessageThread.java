import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class StatusMessageThread extends MyRunnable {
    public static final String SENT_1 = "msg-check";
    public static final String SENT_2_3 = "msg-dblcheck";
    public static final String CLASS_OF_READ_MESSAGE = "_3l4_3";
    public static final String CSS_STATUS_MESSAGE = "div[class='do8e0lj9 l7jjieqr k6y3xtnu']";
    public static final String TAG_STATUS_MESSAGE = "span";
    public static final String ATRRIBUTE_STATUS_MESSAGE = "data-testid";
    public static final String MASSAGE_OUT_CLASS = "message-out";
    private boolean checked;
    private final int sec;
    private final ArrayList<MessageWhatsapp> messageWhatsappArrayList;

    public StatusMessageThread(StartSystemDriver startSystemDriver, ArrayList<MessageWhatsapp> list) {
        super(startSystemDriver);
        this.messageWhatsappArrayList = list;
        this.checked = false;
        if (messageWhatsappArrayList.size()!=0) {
            this.sec = 10 / messageWhatsappArrayList.size();
        }
        else this.sec=0;
    }

    @Override
    public void _run() {
        for (MessageWhatsapp message : this.messageWhatsappArrayList) {
            if (message.getMessageBack() == null) {
                getStart().setDriverPage(message.getFormatPhoneNumber());
                do {
                    statusMessage(message);
                    Util.sleep(Const.SEC * sec);
                } while (!checked);
            }
            checked = false;
        }
        if (messageWhatsappArrayList.stream().allMatch(messageWhatsapp -> messageWhatsapp.getMessageBack() != null)) {
            stop();
        }
    }


    public void statusMessage(MessageWhatsapp messageWhatsapp) {
        try {
            List<WebElement> allMessages = getStart().loadListOfMessages();
            if (allMessages.size() > 0) {
                WebElement lastMessage = allMessages.get(allMessages.size() - 1);
                WebElement myMessage = getMyMessageFromList(allMessages);
                if (myMessage != null) {
                    WebElement css = myMessage.findElement(By.cssSelector(CSS_STATUS_MESSAGE));
                    WebElement read = css.findElement(By.tagName(TAG_STATUS_MESSAGE));
                    String find = read.getAttribute(ATRRIBUTE_STATUS_MESSAGE);
                    if (find.equals(SENT_1)) {
                        messageWhatsapp.setTypeSent(MessageWhatsapp.SENT_STATUS_INT);
                    } else if (find.equals(SENT_2_3)) {
                        messageWhatsapp.setTypeSent(MessageWhatsapp.ACCEPTED_STATUS_INT);
                        String className = read.getAttribute(Const.CLASS);
                        if (className.equals(CLASS_OF_READ_MESSAGE)) {
                            messageWhatsapp.setTypeSent(MessageWhatsapp.SEEN_STATUS_INT);
                        }
                    }
                }
                if (lastMessage != null && !isISentThisMessage(lastMessage)) {
                    messageWhatsapp.setMessageBack(lastMessage.getText());
                }
                getStart().setJLabelStatusByMessage(messageWhatsapp);
                checked = true;
            }
        } catch (Exception ignored) {

        }
    }

    public boolean isISentThisMessage(WebElement myElement) {
        boolean iSent = false;
        if (myElement != null) {
            String classSent = myElement.getAttribute(Const.CLASS);
            if (classSent.contains(MASSAGE_OUT_CLASS)) {
                iSent = true;
            }
        }
        return iSent;
    }

    public WebElement getMyMessageFromList(List<WebElement> webElements) {
        WebElement my = null;
        for (int i = webElements.size() - 1; i >= 0; i--) {
            if (isISentThisMessage(webElements.get(i))) {
                my = webElements.get(i);
                break;
            }
        }
        return my;
    }
}
