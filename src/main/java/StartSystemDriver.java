import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class StartSystemDriver extends BasicJPanel {
    public static final String ALL_MESSAGE_CLASS = "_2wUmf";
    public static final String PATH = "C:\\FILE\\1\\t.txt";
    public static final String DRIVER_GET = "https://web.whatsapp.com/send?phone=972";
    private static final String START_STATUS_MESSAGE = "Status message---> The message is loading - not sent yet";
    private static final String START_MESSAGE = "Try To connect! You need to scan the QR";
    private static final String TITLE_REPORT_BUTTON = "Click here for making a report massage";
    private static final String LOGIN_CLASS_NAME = "ldL67";
    private static final String WHATSAPP_PHAT = "https://web.whatsapp.com/";
    private static final String PHOTO_PATH = "QR.png";
    private ChromeDriver driver;
    private JLabel systemMessages;
    private final ArrayList<MessageWhatsapp> messageList;
    private final ArrayList<MessageWhatsapp> errorMessages;
    private final HashMap<MessageWhatsapp, JLabel> statusMessageList;


    public StartSystemDriver(ArrayList<MessageWhatsapp> list) {
        super(0, 0, Const.WINDOW_W, Const.WINDOW_H, PHOTO_PATH);
        this.messageList = list;
        this.errorMessages = new ArrayList<>();
        this.statusMessageList = new HashMap<>();
        getIn();
    }

    public void addErrorMessages(MessageWhatsapp error) {
        this.errorMessages.add(error);
    }

    public ArrayList<MessageWhatsapp> removeAllError() {
        this.messageList.removeAll(errorMessages);
        return this.messageList;
    }

    public void setMessageList() {
        int y = 100;
        initReportButton();
        int distance = (Const.WINDOW_H - y * 3) / messageList.size();
        for (MessageWhatsapp messageWhatsapp : messageList) {
            statusMessageList.put(messageWhatsapp, addJLabel(messageWhatsapp.getFormatPhoneNumber() + START_STATUS_MESSAGE, 0, y, Const.WINDOW_W, Const.MESSAGE_H, Const.FONT_SIZE, Color.blue));
            y += distance;
        }
        repaint();
    }

    public JLabel getSystemMessages() {
        return this.systemMessages;
    }


    public void setDriverPage(String phoneNumber) {
        this.driver.get(DRIVER_GET + phoneNumber.substring(1));
        while (!this.login()) {
            Util.sleep(1);
        }
    }

    public List<WebElement> loadListOfMessages() {
        return this.driver.findElements(By.className(ALL_MESSAGE_CLASS));
    }

    public ChromeDriver getDriver() {
        return this.driver;
    }

    public void run() {

        LoginThread loginThread = new LoginThread(this);
        new Thread(loginThread).start();
        SentMessageThread sentMessageThread = new SentMessageThread(this, this.messageList, loginThread);
        new Thread(sentMessageThread).start();
    }

    public void setJLabelStatusByMessage(MessageWhatsapp message) {
        this.statusMessageList.get(message).setText(message.getFormatPhoneNumber() + " " + message.getStatus());
        this.repaint();
    }

    public void startAct() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\97252\\Downloads\\chromedriver_win32 (1)\\1\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-fata-dir=C:\\Users\\97252\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(WHATSAPP_PHAT);
        Util.sleep(Const.SEC * 2);
        systemMessages = addJLabel(START_MESSAGE, 0, 0, Const.WINDOW_W, Const.SIZE, 20, Color.blue);
        systemMessages.setOpaque(true);
        repaint();
    }

    public void initReportButton() {
        Button summaryReport = new Button(TITLE_REPORT_BUTTON);
        summaryReport.setBounds(0, Const.WINDOW_H - Const.SIZE, Const.WINDOW_W, Const.SIZE / 2);
        summaryReport.addActionListener(e -> {
            writeToFile();
        });
        summaryReport.setBackground(Color.lightGray);
        summaryReport.setForeground(Color.white);
        summaryReport.setFont(Const.FONT);
        this.add(summaryReport);
    }

    public boolean login() {
        List<WebElement> in = new LinkedList<>();
        try {
            in = driver.findElements(By.className(LOGIN_CLASS_NAME));

        } catch (Exception ignored) {

        }
        return in.size() != 0;
    }

    public void getIn() {
        startAct();
        run();
    }

    public void writeToFile() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(PATH);
            Set<MessageWhatsapp> list = new HashSet<>();
            list.addAll(messageList);
            list.addAll(errorMessages);
            fileWriter.write(list.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
