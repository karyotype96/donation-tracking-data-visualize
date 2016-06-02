/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donationtracker;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.chart.*;
import java.util.*;
import java.io.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.scene.chart.XYChart.*;

/**
 *
 * @author seanc
 */
public class FXMLDocumentController extends Thread implements Initializable {
    
    private ArrayList<String> dateList;
    private ArrayList<Double> amountList;
    private double initAmount;
    private int count;
    private PrintWriter writer;
    private File file;
    private File initAmountFile;
    private ArrayList<Screen> screens;
    
    @FXML
    TabPane tabPane;
    
    @FXML
    Tab enterDataTab;
    
    @FXML
    Tab visualizeTab;
    
    @FXML
    Label dateLabel;
    
    @FXML
    TextField dateTextField;
    
    @FXML
    Button enterX;
    
    @FXML
    Button visualX;
    
    @FXML
    Label amountLabel;
    
    @FXML
    TextField amountTextField;
    
    @FXML
    GridPane grid;
    
    @FXML
    Label dateGridLabel;
    
    @FXML
    Label amountGridLabel;
    
    @FXML
    Label dateLabel1;
    @FXML
    Label dateLabel2;
    @FXML
    Label dateLabel3;
    @FXML
    Label dateLabel4;
    @FXML
    Label dateLabel5;
    @FXML
    Label dateLabel6;
    @FXML
    Label dateLabel7;
    @FXML
    Label dateLabel8;
    @FXML
    Label dateLabel9;
    @FXML
    Label dateLabel10;
    
    @FXML
    Label amountLabel1;
    @FXML
    Label amountLabel2;
    @FXML
    Label amountLabel3;
    @FXML
    Label amountLabel4;
    @FXML
    Label amountLabel5;
    @FXML
    Label amountLabel6;
    @FXML
    Label amountLabel7;
    @FXML
    Label amountLabel8;
    @FXML
    Label amountLabel9;
    @FXML
    Label amountLabel10;
    
    @FXML
    Button addButton;
    
    @FXML
    Label initialAmountLabel;
    
    @FXML
    TextField initialAmountTextField;
    
    @FXML
    Button updateButton;
    
    @FXML
    Button removeFirstButton;
    
    @FXML
    Button removeLastButton;
    
    @FXML
    TextField removeByDateTextField;
    
    @FXML
    Button removeByDateButton;
    
    @FXML
    TextField removeByAmountTextField;
    
    @FXML
    Button removeByAmountButton;
    
    @FXML
    ImageView iv;
    
    @FXML
    BarChart<String, Number> weeklyAmount;
    
    @FXML
    LineChart<String, Number> totalAmount;
    
    @FXML
    CategoryAxis weeklyAmountXAxis;
    
    @FXML
    NumberAxis weeklyAmountYAxis;
    
    @FXML
    CategoryAxis totalAmountXAxis;
    
    @FXML
    NumberAxis totalAmountYAxis;
    
    @FXML
    Button startVisualizationButton;
    
    ArrayList<Label> dateLabelList;
    ArrayList<Label> amountLabelList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateList = new ArrayList<>();
        amountList = new ArrayList<>();
        count = 0;
        
        file = new File("data.dat");
        if (!(file.exists())){
            try {
                file.createNewFile();
            } catch (IOException e){
                System.err.println("IOException, please retry...");
            }
        }
        
        initAmountFile = new File("init.dat");
        if (!(initAmountFile.exists())){
            try {
                initAmountFile.createNewFile();
                PrintWriter pWriter = new PrintWriter(initAmountFile);
                pWriter.append("0");
                pWriter.close();
            } catch (IOException e){
                System.err.println("IOException, please retry...");
            }
        }
        
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                dateList.add(scan.next());
                amountList.add(scan.nextDouble());
                count++;
            }
            
            scan.close();
            scan.close();
        } catch (IOException e){
            System.err.println("IOException, please retry...");
        }
        
        try {
            Scanner scan = new Scanner(initAmountFile);
            if (scan.hasNext()){
                initAmount = scan.nextDouble();
            }
        } catch (IOException e){
            System.err.println("IOException, please retry...");
        }
        
        this.initLabels();
        
        if (!(dateList.isEmpty())){
            this.update();
        }
        
        this.updateBase();
        
    }
    
    @FXML
    private void stackPush(){
        if (!(dateTextField.getText().isEmpty()) && count < 10){
            if (!(amountTextField.getText().isEmpty())){
                String date = String.valueOf(dateTextField.getText());
                Double amount = Double.parseDouble(amountTextField.getText());
                
                System.out.println(date);
                System.out.println(amount);
                
                dateList.add(date);
                amountList.add((double)amount);
                
                dateTextField.clear();
                amountTextField.clear();
                count++;
                
                this.update();
            }
        }
    }
    
    @FXML
    private void stackPop(){
        dateList.remove(count - 1);
        amountList.remove(count-- - 1);
        
        this.update();
    }
    
    @FXML
    private void queuePull(){
        
        initAmount += amountList.get(0);
        
        dateList.remove(0);
        amountList.remove(0);
        count--;
        
        this.update();
        this.updateBase();
    }
    
    @FXML
    private void removeByDate(){    //removes all entries with date specified
                                    //in removeByDateTextField
        String dateString = removeByDateTextField.getText();
        
        for (int i = dateList.size() - 1; i >= 0; i--){
            if (dateList.get(i).equals(dateString)){
                dateList.remove(i);
                amountList.remove(i);
            }
        }
        
        removeByDateTextField.clear();
        
        this.update();
        count--;
    }
    
    @FXML
    private void removeByAmount(){  //removes all entries with the amount
                                    //specified in removeByAmountTextField
        String amountString = removeByAmountTextField.getText();
        
        for (int i = amountList.size() - 1; i >= 0; i--){
            if (String.valueOf(amountList.get(i)).equals(amountString)){
                dateList.remove(i);
                amountList.remove(i);
            }
        }
        
        removeByAmountTextField.clear();
        
        this.update();
        count--;
    }
    
    @FXML
    private void exitProgram(){
        file.delete();
        initAmountFile.delete();
        try {
            file.createNewFile();
            initAmountFile.createNewFile();
            
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < dateList.size(); i++){
                pw.printf("%s %f ", dateList.get(i), amountList.get(i));
            }
            pw.close();
                
        } catch (IOException e){
            System.err.println("Error saving lists...");
        }
        
        try {
            PrintWriter pw = new PrintWriter(initAmountFile);
            pw.append(String.valueOf(initAmount));
            pw.close();
        } catch (IOException e) {
            System.err.println("IOException, please retry...");
        }
        
        System.exit(0);
    }
    
    @FXML
    private void visualize(){
        
        weeklyAmount.getData().clear();
        totalAmount.getData().clear();
        
        Series<String, Number> weekly = new Series();
        for (int i = 0; i < dateList.size(); i++){
            weekly.getData().add(new XYChart.Data(dateList.get(i), amountList.get(i)));
        }
        
        double total = initAmount;
        Series<String, Number> fullAmount = new Series<>();
        for (int i = 0; i < amountList.size(); i++){
            total = total + amountList.get(i);
            fullAmount.getData().add(new XYChart.Data(dateList.get(i), total));
        }
        
        weeklyAmount.getData().add(weekly);
        totalAmount.getData().add(fullAmount);
        
    }
    
    private void initLabels(){  //adds all grid labels to specified ArrayLists
        dateLabelList = new ArrayList<>();
        amountLabelList = new ArrayList<>();
        
        dateLabelList.add(dateLabel1);
        dateLabelList.add(dateLabel2);
        dateLabelList.add(dateLabel3);
        dateLabelList.add(dateLabel4);
        dateLabelList.add(dateLabel5);
        dateLabelList.add(dateLabel6);
        dateLabelList.add(dateLabel7);
        dateLabelList.add(dateLabel8);
        dateLabelList.add(dateLabel9);
        dateLabelList.add(dateLabel10);
        
        amountLabelList.add(amountLabel1);
        amountLabelList.add(amountLabel2);
        amountLabelList.add(amountLabel3);
        amountLabelList.add(amountLabel4);
        amountLabelList.add(amountLabel5);
        amountLabelList.add(amountLabel6);
        amountLabelList.add(amountLabel7);
        amountLabelList.add(amountLabel8);
        amountLabelList.add(amountLabel9);
        amountLabelList.add(amountLabel10);
        
        for (int i = 0; i < dateLabelList.size(); i++){
            dateLabelList.get(i).setText("");
            amountLabelList.get(i).setText("");
        }
    }
    
    private void update(){  //changes all grid labels to display data
        for (int i = 0; i < dateLabelList.size(); i++){
            dateLabelList.get(i).setText("");
            amountLabelList.get(i).setText("");
        }
        
        for (int i = 0; i < dateList.size(); i++){
            dateLabelList.get(i).setText(dateList.get(i));
            amountLabelList.get(i).setText(String.valueOf(amountList.get(i)));
        }
    }
    
    @FXML
    private void setBase(){
        initAmount = Double.parseDouble(initialAmountTextField.getText());
        updateBase();
    }
    
    private void updateBase(){
        initialAmountTextField.setText(String.valueOf(initAmount));
    }
}
