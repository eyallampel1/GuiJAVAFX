package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Controller {

    SerialPort serialPort;
    @FXML
    Button autoBtn, manualBtn, upbtn, downbtn, rightbtn, leftbtn;
    @FXML
    ImageView img;
    @FXML
    TextArea Console;
    URL upmsg, downmsg, rightmsg, leftmsg, stopmsg, rstmsg, manmsg, automsg;
    InputStream is = null;

    public void initialize() {
        Image image = new Image("file:/../GUI_IMAGE.png");
        img.setImage(image);
        //  SerialPortinit();
        try {
            upmsg = new URL("https://httpbin.org/get?forward");
            downmsg = new URL("https://httpbin.org/get?backword");
            rightmsg = new URL("https://httpbin.org/get?right");
            leftmsg = new URL("https://httpbin.org/get?left");
            stopmsg = new URL("https://httpbin.org/get?stop");
            rstmsg = new URL("https://httpbin.org/get?reset");
            manmsg = new URL("https://httpbin.org/get?manual");
            automsg = new URL("https://httpbin.org/get?auto");
            /*upmsg = new URL("http://192.168.1.11/get?forward");
            downmsg = new URL("http://192.168.1.11/get?backword");
            rightmsg = new URL("http://192.168.1.11/get?right");
            leftmsg = new URL("http://192.168.1.11/get?left");
            stopmsg = new URL("http://192.168.1.11/get?stop");
            rstmsg = new URL("http://192.168.1.11/get?reset");
            manmsg = new URL("http://192.168.1.11/get?manual");
            automsg = new URL("http://192.168.1.11/get?auto");*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void upfunc() {
        Console.appendText("Forward Sent.\n");
        Thread myT = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    is = upmsg.openConnection().getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myT.run();
    }

    public void downfunc() {
        Console.appendText("Reverse Sent.\n");
        try {
            is = downmsg.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
    }

    public void rightfunc() {
        Console.appendText("Right Sent.\n");
        try {
            is = rightmsg.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
    }

    public void leftfunc() {
        Console.appendText("Left Sent.\n");
        try {
            is = leftmsg.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
    }

    public void manBtnfunc() {
        manualBtn.setDisable(true);
        autoBtn.setDisable(false);
        upbtn.setDisable(false);
        downbtn.setDisable(false);
        leftbtn.setDisable(false);
        rightbtn.setDisable(false);
        try {
            is = manmsg.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void autoBtnfunc() {
        manualBtn.setDisable(false);
        autoBtn.setDisable(true);
        upbtn.setDisable(true);
        downbtn.setDisable(true);
        leftbtn.setDisable(true);
        rightbtn.setDisable(true);
        Console.appendText("Scanning...\n");
        try {
            is = automsg.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

        String line = null;
        while(true)  {
            try {
                if (!(( line = reader.readLine() ) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SerialPortinit() {

        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8, 1, 0);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }
}
    /*    try {
                url = new URL("https://httpbin.org/get?helloBTC");
                } catch (MalformedURLException e) {
                e.printStackTrace();
                }
                InputStream is = null;
                try {
                is = url.openConnection().getInputStream();
                } catch (IOException e) {
                e.printStackTrace();
                }

                BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

                String line = null;
                while(true)  {
                try {
                if (!(( line = reader.readLine() ) != null)) break;
                } catch (IOException e) {
                e.printStackTrace();
                }
                System.out.println(line);
                }
                try {
                reader.close();
                } catch (IOException e) {
                e.printStackTrace();
                }*/