/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Spame;

/**
 *
 * @author ADMIN
 */
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
public class Controller implements Initializable{
    @FXML private Button btnPlay, btnPause, btnBack, btnNext;
    @FXML private Button btnChoose, btnSound, btnSoundMin, btnSoundMax, btnSoundMute;
    @FXML private Label lblNameMusic = new Label();
    @FXML private ListView<String> listView = new ListView<>();
    @FXML private Pane pane = new Pane();
    @FXML Slider sliderTime = new Slider();
    @FXML Label lblTotalTime = new Label();
    @FXML Label lblCurentTime = new Label();
    @FXML Slider sliderVolume = new Slider();
    static Media media = new Media(Function.getListFile(new File("Music")).get(0));
    static MediaPlayer player = new MediaPlayer(media);
    static ArrayList<String> listMusic = Function.getListFile(new File("Music"));
    private int searchIndexFromList(String URIMusic){
        for(int i=0; i<listMusic.size(); i++){
            if(URIMusic.equals(listMusic.get(i))) return i;
        }
        return -1;
    }
    private void autoNextSong(){
        if(sliderTime.getValue()==sliderTime.getMax()){
            int index = this.searchIndexFromList(player.getMedia().getSource());
            if(index < listMusic.size()-1){
                media = new Media(listMusic.get(index + 1));
                player.stop();
                btnPlay.setVisible(false);
                player = new MediaPlayer(media);
                player.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                });
                lblNameMusic.setText(Function.URItoName(listMusic.get(index+1)));
                btnPause.setVisible(true);
            }
            else{
                media = new Media(listMusic.get(0));
                player.stop();
                btnPlay.setVisible(false);
                player = new MediaPlayer(media);
                player.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                });
                lblNameMusic.setText(Function.URItoName(listMusic.get(0)));
                btnPause.setVisible(true);
            }
        }
    }
    public void buttonBack(){
        btnBack.setVisible(false);
        btnNext.setVisible(true);
        btnChoose.setVisible(true);
        btnSound.setVisible(false);
        btnSoundMax.setVisible(false);
        btnSoundMin.setVisible(false);
        btnSoundMute.setVisible(false);
        pane.setVisible(false);
        listView.setVisible(true);
    }
    public void buttonNext(){
        btnBack.setVisible(true);
        btnNext.setVisible(false);
        btnChoose.setVisible(false);
        setShowVolume();
        listView.setVisible(false);
        pane.setVisible(true);
    }
    public void ChooseFile(ActionEvent event){
        File folder = Function.getNameDirectory();
        if(folder != null){
            listMusic = Function.getListFile(folder);
            listView.setItems((FXCollections.observableArrayList(Function.URItoName(listMusic))));
        }
    }
    public void start(){
        double duration = player.getMedia().getDuration().toSeconds();
        sliderTime.setValue(player.getCurrentTime().toSeconds());
        player.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
            lblCurentTime.setText(Function.formatTime(newValue.toSeconds()));
            sliderTime.setValue(newValue.toSeconds());
            autoNextSong();
        }));
        sliderTime.setMin(0);
        sliderTime.setMax(duration);
        lblTotalTime.setText(Function.formatTime(duration));
        player.play();
    }
    public void buttonVolume(){
        sliderVolume.setVisible(true);
    }
    private void setShowVolume(){
        if(sliderVolume.getValue() >= 0.75){
            btnSoundMax.setVisible(true);
            btnSound.setVisible(false);
            btnSoundMin.setVisible(false);
            btnSoundMute.setVisible(false);
            sliderVolume.setVisible(false);
        }
        else{
            if(sliderVolume.getValue() >= 0.4 && sliderVolume.getValue() < 0.75){
                btnSoundMax.setVisible(false);
                btnSound.setVisible(true);
                btnSoundMin.setVisible(false);
                btnSoundMute.setVisible(false);
                sliderVolume.setVisible(false);
            }
            else{
                if(sliderVolume.getValue() > 0 && sliderVolume.getValue() < 0.4){
                    btnSoundMax.setVisible(false);
                    btnSound.setVisible(false);
                    btnSoundMin.setVisible(true);
                    btnSoundMute.setVisible(false);
                    sliderVolume.setVisible(false);
                }
                else{
                    if(sliderVolume.getValue()==0){
                        btnSoundMax.setVisible(false);
                        btnSound.setVisible(false);
                        btnSoundMin.setVisible(false);
                        btnSoundMute.setVisible(true);
                        sliderVolume.setVisible(false);
                    }
                }
            }
        }
    }
    public  void slidervolume(MouseEvent event){
        player.setVolume(sliderVolume.getValue());
        setShowVolume();
    }
    public void slidertime(MouseEvent event){
        player.seek(Duration.seconds(sliderTime.getValue()));
    }
    public void Play(ActionEvent event){
        start();
        btnPlay.setVisible(false);
        btnPause.setVisible(true);
    }
    public void Pause(ActionEvent event){
        player.pause();
        btnPause.setVisible(false);
        btnPlay.setVisible(true);
    }
     @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(listMusic.size() > 0){
            listView.setItems(FXCollections.observableArrayList(Function.URItoName(listMusic)));
        }
        Media media1 = player.getMedia();
        lblNameMusic.setText(Function.URItoName(media1.getSource()));
        lblTotalTime.setText("00:00");
        lblCurentTime.setText("00:00");
        sliderVolume.setMin(0);
        sliderVolume.setMax(1);
        sliderVolume.setValue(1);
    }
}
