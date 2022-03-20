/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Spame;

/**
 *
 * @author ADMIN
 */
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampledsound.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class Function {
    public static File getNameDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select directory music");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = new Stage();
        File directory = directoryChooser.showDialog(stage);
        return directory;
    }
    public static ArrayList<String> getListFile(File directory){
        if(directory != null){
            File[] listFile = directory.listFiles();
            ArrayList<String> list = new ArrayList<>();
            for(File file : listFile){
                if(file.toURI().toString().matches(".+.mp3$")){
                    list.add(file.toURI().toString());
                }
            }
            return list;
        }
        return null;
    }
    public static ArrayList<String> URItoName(ArrayList<String> listURI){
        ArrayList<String> listName = new ArrayList<>();
        for(String uri : listURI){
            //Tách chuỗi chỉ lẫy tên bài hát
            String[] list = uri.split("/");
            listName.add(list[list.length-1]);
        }
        return listName;
    }
    public static String URItoName(String URI){
        String[] list = URI.split("/");
        return list[list.length-1];
    }
    public static String formatTime(Double seconds){
        int minute = (int) (seconds/60);
        int second = (int) (seconds%60);
        return minute + ":" + second;
    }
}
