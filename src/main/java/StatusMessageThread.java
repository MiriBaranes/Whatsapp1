import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.util.ArrayList;

public class StatusMessageThread extends MyRunnable {
    public static final String SENT_1 = "msg-check";
    public static final String SENT_2_3 = "msg-dblcheck";
    public static final String CLASS_OF_READ_MESSAGE = "_3l4_3";
    public static final String CSS_STATUS_MESSAGE = "div[class='do8e0lj9 l7jjieqr k6y3xtnu']";
    public static final String TAG_STATUS_MESSAGE = "span";
    public static final String ATRRIBUTE_STATUS_MESSAGE = "data-testid";
    public static final String MASSAGE_OUT_CLASS = "message-out";
    private boolean checked;
    private SentMessageThread sentMessageThread;
    private ArrayList<MessageWhatsapp> messageWhatsappArrayList;

    public StatusMessageThread(Start start, ArrayList<MessageWhatsapp> messageWhatsappArrayList, SentMessageThread sentMessageThread) {
        super(start);
        this.messageWhatsappArrayList = messageWhatsappArrayList;
        this.sentMessageThread = sentMessageThread;
        this.checked = false;
    }

    @Override
    public void _run() {
        if (!sentMessageThread.isRunning()) {
            System.out.println("im here");
            for (MessageWhatsapp message : this.messageWhatsappArrayList) {
                System.out.println(message.getFormatPhoneNumber());
                if (message.getMessageBack() == null) {
                    getStart().setDriverPage(message.getFormatPhoneNumber());
                    Util.sleep(Const.SEC * 5);
                    do {
                        statusMessage(message);
                    } while (!checked);
                    checked = false;
                    Util.sleep(Const.SEC * 5);
                }
            }
            if (messageWhatsappArrayList.stream().allMatch(messageWhatsapp -> messageWhatsapp.getMessageBack() != null)) {
                stop();
            }
        }

    }

    public void statusMessage(MessageWhatsapp messageWhatsapp) {
        if (messageWhatsapp.isSent()) {
            JLabel messageLabel = getStart().getStatusMessageList().get(messageWhatsapp);
            getStart().loadListOfMessages();
            WebElement lastMessage = getStart().loadListOfMessages();
            if (lastMessage != null && isISentThisMessage(lastMessage)) {
                WebElement last = lastMessage.findElement(By.cssSelector(CSS_STATUS_MESSAGE));
                WebElement read = last.findElement(By.tagName(TAG_STATUS_MESSAGE));
                String find = read.getAttribute(ATRRIBUTE_STATUS_MESSAGE);
                if (find.equals(SENT_1)) {
                    messageWhatsapp.setStatus(MessageWhatsapp.SENT_STATUS_INT);
                } else if (find.equals(SENT_2_3)) {
                    messageWhatsapp.setStatus(MessageWhatsapp.ACCEPTED_STATUS_INT);
                    String className = read.getAttribute("class");
                    if (className.equals(CLASS_OF_READ_MESSAGE)) {
                        messageWhatsapp.setStatus(MessageWhatsapp.SEEN_STATUS_INT);
                    }
                }
            }
            if (lastMessage != null && !isISentThisMessage(lastMessage)) {
                messageWhatsapp.setStatus(MessageWhatsapp.SEEN_STATUS_INT);
                messageWhatsapp.setMessageBack(lastMessage.getText());
            }
            messageLabel.setText(messageWhatsapp.getFormatPhoneNumber() + " " + messageWhatsapp.getStatus());
            getStart().repaint();
            checked = true;
        }
    }

    public boolean isISentThisMessage(WebElement myElement) {
        boolean iSent = false;
        if (myElement != null) {
            String classSent = myElement.getAttribute("class");
            if (classSent.contains(MASSAGE_OUT_CLASS)) {
                iSent = true;
            }
        }
        return iSent;
    }
}
