/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;


/**
 *
 * @author verma
 */
public class levelHandler implements Serializable {
    private static boolean[][] plantgrid = new boolean[9][5];
    private int level;
    private double zombieCount;
    private long lastZombieAdded;
    private transient static ArrayList<ArrayList<Plant>> plantList;
    private ArrayList<ArrayList<Zombie>> zombieList;
    private transient static ArrayList<ArrayList<Pea>> peaList;
    private static ArrayList<Sun> sunList;
    private float spawnTime; // Zombie 
    private final float sunTime ;
    private long lastSunAdded;
    private static int sunToken;
    private ArrayList<LawnMower> lawnMowerList ; 
    private transient ProgressBar progress ;
    private double progressBarOffset;  // progress bar
    ////////////////////faltu
    private  ArrayList<ArrayList<Plant>> plantList1;  
    
    public levelHandler(int level){
        for(int i=0;i<9;i++){
            for(int j=0;j<5;j++){
                plantgrid[i][j]=false;
            }
        }
        
        this.level = level;
        setZombieCount();
        progressBarOffset = zombieCount;   
        sunToken= 0;
        lastZombieAdded = 0L;
        lastSunAdded = 0L;
        sunTime = 5;  
        
        lawnMowerList = new ArrayList<LawnMower>();
        zombieList = new ArrayList<ArrayList<Zombie>>();
        callMe();
        plantList1 = plantList;
    }
    
    
    public static int getSunCount(){return sunToken;}
    
    
    public void setZombieCount(){
        switch(level){
            case 1 :
                zombieCount =15 ;
                spawnTime = 3;//to be changed
         
                break;
            case 2:
                zombieCount = 10;
                spawnTime = 7;
         
                break;
            case 3 :
                zombieCount = 15 ;
                spawnTime = 4;
         
                break;
            case 4:
                zombieCount = 20;
                spawnTime = 2;
         
                break;
            case 5 :
                zombieCount = 25;
                spawnTime = 1;
         
                break;
        }
    }
    
    
    
    public void callMe(){
        ////////////
        
        plantList = new ArrayList<ArrayList<Plant>>();
        
        peaList = new ArrayList<ArrayList<Pea>>();
        sunList = new ArrayList<Sun>();
        
        progress = new ProgressBar(0);
        progress.setLayoutX(50);
//        progress.setRotate(180);
        
        
        Project.addToGroup(new Group(progress));
        
        
        for (int x = 0 ; x<5 ;x++){
            plantList.add(new ArrayList<Plant>());
            zombieList.add(new ArrayList<Zombie>());
            peaList.add(new ArrayList<Pea>());
            if (lawnMowerList.size()<5){
                switch(x){
                    case 0 :
                        lawnMowerList.add(new LawnMower(220,135));
                        break;
                    case 1 :
                        lawnMowerList.add(new LawnMower(220,255));
                        break;
                    case 2 :
                        lawnMowerList.add(new LawnMower(220,375));
                        break;
                    case 3 :
                        lawnMowerList.add(new LawnMower(220,495));
                        break;
                    case 4 :
                        lawnMowerList.add(new LawnMower(220,615));
                        break;
                }

            }
        }
    
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void load(){
       ////////////////////
       callMe();
       plantList = plantList1;
       for (int x = 0 ; x< lawnMowerList.size();x++){
            lawnMowerList.get(x).load();
            
        }
        
       System.out.println(plantList.size());
        for (int x = 0 ; x<plantList.size();x++){
            for (int i =  0 ; i < plantList.get(x).size();i++){ 
                plantList.get(x).get(i).load();
            }
        }
        for (int x = 0 ; x<zombieList.size();x++){
            for (int i =  0 ; i < zombieList.get(x).size();i++){
               zombieList.get(x).get(i).load();
            }
        }
    
    
    }
    
    public void update(){
        spawnZombies();
        shineSun();
        progress.setProgress((double)((progressBarOffset-zombieCount)/(progressBarOffset)) );
        
        
        
        
        ////////////////////////////////////////////////////////////////////
        for (int x = 0 ; x< lawnMowerList.size();x++){
            if (lawnMowerList.get(x).getIsAlive()){
                lawnMowerList.get(x).update();
                Project.addToGroup(lawnMowerList.get(x).getView());
            }else{
                Project.removeFromGroup(lawnMowerList.get(x).getView());
                
             
             
            }
        }
        for (int x = 0 ; x<plantList.size();x++){
            for (int i =  0 ; i < plantList.get(x).size();i++){ 
                if (plantList.get(x).get(i).getIsAlive()){
                    plantList.get(x).get(i).update();
                    Project.addToGroup(plantList.get(x).get(i).getView()); }
                else {
                    Project.removeFromGroup(plantList.get(x).get(i).getView());
                    plantList.get(x).remove(plantList.get(x).get(i));
                }
            }
        }
        for (int x = 0 ; x<zombieList.size();x++){
            for (int i =  0 ; i < zombieList.get(x).size();i++){
                if (zombieList.get(x).get(i).getIsAlive()){
                    zombieList.get(x).get(i).update();
                    Project.addToGroup(zombieList.get(x).get(i).getView());}
                else if (System.currentTimeMillis()-zombieList.get(x).get(i).getDeadTime()>700){
                    Project.removeFromGroup(zombieList.get(x).get(i).getView());
                    zombieList.get(x).remove(zombieList.get(x).get(i));
                ///////////////increment score
                
                }
            }
        }
        for (int x = 0 ; x<peaList.size();x++){
            for (int i =  0 ; i < peaList.get(x).size();i++){
                if (peaList.get(x).get(i).getIsAlive()){
                    peaList.get(x).get(i).update();
                    Project.addToGroup(peaList.get(x).get(i).getView());
                }else {
                    Project.removeFromGroup(peaList.get(x).get(i).getView());
                    peaList.get(x).remove(peaList.get(x).get(i));

                }
            }
        }
        
        for (int x = 0 ; x<sunList.size();x++){
            if (sunList.get(x).getIsAlive()){
                sunList.get(x).update();
                Project.addToGroup(sunList.get(x).getView());}
            else{
                Project.removeFromGroup(sunList.get(x).getView());
                sunList.remove(sunList.get(x));
                sunToken += 25;
                level1Controller.setSunCount(level1Controller.getSunCount()+25);
            }
        }
        
        for (int x = 0 ; x<lawnMowerList.size();x++){
            for (int i = 0 ; i < zombieList.get(x).size(); i++){
                if (lawnMowerList.get(x).isColliding(zombieList.get(x).get(i)) && lawnMowerList.get(x).getIsAlive() && zombieList.get(x).get(i).getIsAlive() ){
                    System.out.println("uuhch");
                    
                    zombieList.get(x).get(i).setIsAlive(false);
                    lawnMowerList.get(x).moveNow();
                }
            }
        } 
        
        for (int x = 0 ; x<5;x++){
            for(int i = 0 ; i<zombieList.get(x).size();i++){
                
                for (int t = 0 ; t<peaList.get(x).size();t++){
                    if ( peaList.get(x).get(t).getIsAlive() && peaList.get(x).get(t).isColliding(zombieList.get(x).get(i))){
                        peaList.get(x).get(t).setIsAlive(false);
                        zombieList.get(x).get(i).setHealth(zombieList.get(x).get(i).getHealth()- peaList.get(x).get(t).getDamage() );
                    }
                }
                
            }
        }
        
        for (int x = 0 ; x<5;x++){
            for(int i = 0 ; i<zombieList.get(x).size();i++){
                boolean zMove = true ;
                for (int t = 0 ; t<plantList.get(x).size();t++){
                    if (plantList.get(x).get(t).isColliding(zombieList.get(x).get(i))){
                        plantList.get(x).get(t).setHealth(plantList.get(x).get(t).getHealth() - zombieList.get(x).get(i).getDamage() );
                        zMove= false;
                    }
                }
                zombieList.get(x).get(i).setMove(zMove);
            }
        }
        
        
        
        
        
        //////////////////to be edited
    }
    
    public void spawnZombies(){
        if (System.currentTimeMillis()-lastZombieAdded>=spawnTime*1000 && zombieCount-- > 0){
            Random r = new Random();
            int ran = r.nextInt(5);
            zombieList.get(ran).add(new Zombie(ran));
            lastZombieAdded = System.currentTimeMillis();
        }
    }
    
    public void shineSun(){
        if (System.currentTimeMillis()-lastSunAdded>=sunTime*1000){
            sunList.add(new Sun(true, 0,0));// coordinate doesn't affect anything because move is true. move - represents transition
            lastSunAdded = System.currentTimeMillis();
        }
    }
    public static void addPlant(double x , double y, String s ){
            if(y>106 && y<222){
                if(s.equals("shooter")){
                    plantList.get(0).add(new Shooter(x,y,0));
                }
                else if(s.equals("sunflower")){
                    plantList.get(0).add(new Sunflower(x,y));
                }else if(s.equals("walnut")){
                    plantList.get(0).add(new Walnut(x, y));
                }                 
            }
            else if(y>222 && y<343){
                if(s.equals("shooter")){
                    plantList.get(1).add(new Shooter(x,y,1));
                }
                else if(s.equals("sunflower")){
                    plantList.get(1).add(new Sunflower(x,y));
                }else if(s.equals("walnut")){
                    plantList.get(1).add(new Walnut(x, y));
                }
            }
            else if(y>343 && y<463){
                if(s.equals("shooter")){
                    plantList.get(2).add(new Shooter(332,367,2));
                }
                else if(s.equals("sunflower")){
                    plantList.get(2).add(new Sunflower(332,367));
                }else if(s.equals("walnut")){
                    plantList.get(2).add(new Walnut(x, y));
                }
            }
            else if(y>463 && y<579){
                   if(s.equals("shooter")){
                       plantList.get(3).add(new Shooter(x,y,3));
                   }
                   else if(s.equals("sunflower")){
                       plantList.get(3).add(new Sunflower(x,y));
                   }else if(s.equals("walnut")){
                        plantList.get(3).add(new Walnut(x, y));
                }
            }
            else if(y>579 && y<709){
                   if(s.equals("shooter")){
                       plantList.get(4).add(new Shooter(x,y,4));
                   }
                   else if(s.equals("sunflower")){
                       plantList.get(4).add(new Sunflower(x,y));
                   }else if(s.equals("walnut")){
                        plantList.get(4).add(new Walnut(x, y));
                }
            }
        
        //405 490 595 681 783 871 962 1047 1152
        

        
        
    }
    
    
    public static void addPea(double x , double y,int row){
        peaList.get(row).add(new Pea(x , y));
    }
    public static void addSun(double x , double y ){
        sunList.add(new Sun(false, x, y));
    }
    
}
