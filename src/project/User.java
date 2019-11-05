/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 *
 * @author verma
 */
public class User implements Serializable{

    private static final long serialVersionUID = 42L;
    private int diamonds;
    private int currentLevel;
    private boolean isLevelCompleted;
    private String name ;
    private levelHandler level;
    
    public User(String name){
        this.name =name;
        this.currentLevel=0;
        this.isLevelCompleted=true;
        this.diamonds=0;
        this.level = new levelHandler(1);
    }
    
    public String getName(){return name;}
    
    public void update() {
        level.update();       
    }
    
    public static boolean doesExists(String name ){
        File file = new File(System.getProperty("user.dir")+"\\userFiles\\");
        File [] ls = file.listFiles();
        
        for (File fs : ls){
//            System.out.println(fs);
            if (fs.toString().endsWith(".zzz")&& name.trim().equals(parsePlayer(fs.toString()))){
                return true;
                
            }
        }
        return false;
    }
    private static String parsePlayer(String s){
        Pattern pat = Pattern.compile("[a-zA-Z0-9]+.zzz");
        Matcher m = pat.matcher(s);
        m.find();
        String g = m.group(0);
        return g.substring(0,g.lastIndexOf(".")).trim();
        
    }   
    
    public static String[] getPlayerList(){
//        System.out.println("123");
        File file = new File(System.getProperty("user.dir")+"\\userFiles\\");
        File[] ls = file.listFiles();
        HashMap <String,String> Player_list = new HashMap<String,String>();
        for (File fs : ls){
//            System.out.println(fs.toString());
            if (fs.toString().endsWith(".zzz")){
                Player_list.put(parsePlayer(fs.toString()),fs.toString());
//                System.out.println(parsePlayer(fs.toString()));
            }
        }
        String [] arr = Player_list.keySet().toArray(new String[0]);
        return arr;
        
    }
    public void load (){
        level.load();
    
    }
    
    
}
