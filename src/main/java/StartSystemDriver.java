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
    private static final String START_MESSAGE="Try To connect! you need to Scan the QR";
    private final ChromeDriver driver;
    private JLabel systemMessages;
    private final ArrayList<MessageWhatsapp> messageList;
    private final ArrayList<MessageWhatsapp> errorMessages;
    private final HashMap<MessageWhatsapp, JLabel> statusMessageList;


    public StartSystemDriver(ArrayList<MessageWhatsapp> list) {
        super(0, 0, Const.WINDOW_W, Const.WINDOW_H, "QR.png");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\97252\\Downloads\\chromedriver_win32 (1)\\1\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-fata-dir=C:\\Users\\97252\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        this.driver = new ChromeDriver();
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
            statusMessageList.put(messageWhatsapp, addJLabel(messageWhatsapp.getFormatPhoneNumber() + START_STATUS_MESSAGE, 0, y, Const.WINDOW_W / 2, 30, 10, Color.black));
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
        driver.manage().window().maximize();
        driver.get("https://web.whatsapp.com/");
        Util.sleep(Const.SEC*2);
        systemMessages = addJLabel(START_MESSAGE, 0, 0, Const.WINDOW_W, 100, 20, Color.blue);
        systemMessages.setOpaque(true);
        repaint();
    }

    public void initReportButton() {
        Button summaryReport = new Button("Click Here for make a report massage!");
        summaryReport.setBounds(0, Const.WINDOW_H - 100, Const.WINDOW_W, 50);
        summaryReport.addActionListener(e -> {
            writeToFile();
        });
        summaryReport.setBackground(Color.lightGray);
        summaryReport.setForeground(Color.white);
        summaryReport.setFont(new Font("ariel", Font.BOLD, 15));
        this.add(summaryReport);
    }

    public boolean login() {
        return driver.findElements(By.className("ldL67")).size() != 0;
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
