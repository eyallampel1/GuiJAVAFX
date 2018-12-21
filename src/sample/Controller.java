package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import java.util.Timer;
import java.util.TimerTask;


public class Controller {

    SerialPort serialPort;
    @FXML
    Button autoBtn, manualBtn, upbtn, downbtn, rightbtn, leftbtn;
    @FXML
    ImageView img;
    @FXML
    TextArea Console;
    @FXML
    Label lbl;
    @FXML
    PieChart pieChart;
    URL upmsg, downmsg, rightmsg, leftmsg, stopmsg, rstmsg, manmsg, automsg;
    InputStream is = null;
    int[] Brightness_str = new int[360];
    boolean flag = true;

    public void initialize() {
        Image image = new Image("file:/../GUI_IMAGE.png");
        img.setImage(image);
        Brightness_str[25] = 200;
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
        try {
            upmsg.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Console.appendText("Stop Sent.\n");
                    stopmsg.openConnection();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(task).start();
    }


    public void downfunc() {
        Console.appendText("Reverse Sent.\n");
        try {
            downmsg.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Console.appendText("Stop Sent.\n");
                    stopmsg.openConnection();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(task).start();
    }

    public void rightfunc() {
        Console.appendText("Right Sent.\n");
        try {
            rightmsg.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Console.appendText("Stop Sent.\n");
                    stopmsg.openConnection();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(task).start();
    }

    public void leftfunc() {
        Console.appendText("Left Sent.\n");
        try {
            leftmsg.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //enter delay here and send stop
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Console.appendText("Stop Sent.\n");
                    stopmsg.openConnection();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(task).start();
    }

    public void manBtnfunc() {
        flag = true;
        Console.appendText("Manual Mode.\n");
        manualBtn.setDisable(true);
        autoBtn.setDisable(false);
        upbtn.setDisable(false);
        downbtn.setDisable(false);
        leftbtn.setDisable(false);
        rightbtn.setDisable(false);
        lbl.setVisible(false);
        try {
            manmsg.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void autoBtnfunc() {
        flag = false;
        manualBtn.setDisable(false);
        autoBtn.setDisable(true);
        upbtn.setDisable(true);
        downbtn.setDisable(true);
        leftbtn.setDisable(true);
        rightbtn.setDisable(true);
        Console.appendText("Scanning...\n");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    is = automsg.openConnection().getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                //int i=0;
                while (true) {
                    try {
                        if (!((line = reader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(line);
                    /*string[]tokens = line.split(" ");
                     * Brightness_str[i] = Integer.parseInt(tokens[1]);
                     * i++;*/
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbl.setVisible(true);
                        lbl.setText("Max brightness found at: " + find_max() + " degrees (PLACEHOLDER)");
                    }
                });
                Console.appendText("Done!\n");
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(task).start();
    }

    private int find_max() {
        int i, max = 0, max_idx = 0;
        for (i = 0; i < 360; i++) {
            if (Brightness_str[i] > max) {
                max = Brightness_str[i];
                max_idx = i;
            }
        }
        return max_idx;
    }

    private void SerialPortinit() {

        try {
            serialPort = new SerialPort("COM3");
            serialPort.openPort();
            serialPort.setParams(115200, 8, 1, 0);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    String uartLine = null;
                    uartLine = serialPort.readString(5);
                    Console.appendText(uartLine);
                    if (flag) {
                        switch (uartLine) {
                            case "Forward":
                                upfunc();
                                break;
                            case "Backward":
                                downfunc();
                                break;
                            case "Right":
                                rightfunc();
                                break;
                            case "Left":
                                leftfunc();
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 50, 50);
    }

    private void creatPiechart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < 360; i++) {
            pieChartData.add(new PieChart.Data("" + i, 10));
        }
        pieChart.setData(pieChartData);

    }
}