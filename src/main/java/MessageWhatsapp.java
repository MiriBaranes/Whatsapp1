
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;


public class MessageWhatsapp {

    private static final String MASSAGE_OUT_CLASS = "message-out";
    private static final String PATH = "C:\\FILE\\1\\t.txt";
    public static final int UN_SENT = 0;
    public static final int DELIVERED=1;
    public static final int ERROR_STATUS_INT = 2;
    public static final int SENT_STATUS_INT = 3;
    public static final int ACCEPTED_STATUS_INT = 4;
    public static final int SEEN_STATUS_INT = 5;
    private static final String DELIVERED_STATUS="Status message---> delivered";
    public static final String SENT_STATUS = "Status message---> ✔ - Sent";
    public static final String ACCEPTED_STATUS = "Status message---> ✔✔ - Sent & Accepted";
    public static final String SEEN_STATUS = "Status Message---> ✔✔ - Sent & Accepted & Reed";
    public static final String ERROR_STATUS = "Status Message---> Error! the number of this phone number not exsit in whatsapp";
    private final String phoneNumber;
    private final String message;
    private int typeSent;
    private String status;
    private String messageBack;

    public MessageWhatsapp(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = "Status Message---> Not Sent loading...";
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
                this.status=DELIVERED_STATUS;
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


    public void writeToFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(PATH);
            fileWriter.write("For: " + this.getFormatPhoneNumber() + "\nYour message---> " + this.message +
                    "\n" + this.status + "\n" + this.messageBack);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean isSent() {
        return this.typeSent==DELIVERED;
    }

    @Override
    public String toString() {
        return "For= " + phoneNumber + "\n" +
                " Message= " + message + "\n" +
                " status=" + status +
                " \nmessageBack=" + messageBack + "\n\n";
    }
}
