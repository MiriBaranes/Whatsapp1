
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;


public class MessageWhatsapp {
    public static final int UN_SENT = 0;
    public static final int DELIVERED = 1;
    public static final int ERROR_STATUS_INT = 2;
    public static final int SENT_STATUS_INT = 3;
    public static final int ACCEPTED_STATUS_INT = 4;
    public static final int SEEN_STATUS_INT = 5;
    public static final String ACCEPTED_STATUS = "Status message---> ✔✔ - Sent & Received";
    public static final String SEEN_STATUS = "Status Message---> ✔✔ - Sent & Accepted & read";
    public static final String ERROR_STATUS = "Status Message---> Error! The number does not exist on whatsapp";
    private static final String DELIVERED_STATUS = "Status message---> delivered";
    public static final String SENT_STATUS = "Status message---> ✔ - Sent";
    private final String phoneNumber;
    private final String message;
    private int typeSent;
    private String status;
    private String messageBack;

    public MessageWhatsapp(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = "Status Message--->  Not sent yet. Loading...";
        this.messageBack = null;
        this.typeSent = UN_SENT;
    }

    public void setTypeSent(int typeSent) {
        if (typeSent >= 0 && typeSent <= SEEN_STATUS_INT) {
            this.typeSent = typeSent;
            setStatus(typeSent);
        }
    }

    public void setStatus(int status) {
        switch (status) {
            case DELIVERED -> {
                this.status = DELIVERED_STATUS;
            }
            case SENT_STATUS_INT -> {
                this.status = SENT_STATUS;
            }
            case ACCEPTED_STATUS_INT -> {
                this.status = ACCEPTED_STATUS;
            }
            case SEEN_STATUS_INT -> {
                this.status = SEEN_STATUS;
            }
            case ERROR_STATUS_INT -> {
                this.status = ERROR_STATUS;
            }
        }
    }

    public int getTypeSent() {
        return this.typeSent;
    }

    public void setMessageBack(String messageBack) {
        this.messageBack = messageBack;
    }

    public String getMessageBack() {
        return this.messageBack;
    }

    public String getStatus() {
        return this.status;
    }


    public String getPhoneNumber() {
        return this.phoneNumber.substring(1);
    }

    public String getFormatPhoneNumber() {
        return this.phoneNumber;
    }

    public String getMessage() {
        return this.message;
    }


    public boolean isSent() {
        return this.typeSent == DELIVERED;
    }

    @Override
    public String toString() {
        return "For: " + phoneNumber + "\n" +
                " Message: " + message + "\n" +
                " Status:" + status +
                " \nReply:" + messageBack + "\n\n";
    }
}
