package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GameInfo implements Serializable {

    private HashMap<Long,Position> info;
    private HashMap<Long, TETile[][]> info_world;
    private HashMap<Long, Long> info_time;
    private HashMap<Long, Position> info_vic;
    private HashMap<Long, Boolean> info_sight;
    private HashMap<Long, Enemy> info_enemy;

    GameInfo(Position p,long seed,TETile[][] world,long time,Position vic,boolean sightOn,Enemy enemy){


      this.info=new HashMap<>();
      info.put(seed,p);
      this.info_world=new HashMap<>();
      info_world.put(seed,world);
        this.info_time=new HashMap<>();
        info_time.put(seed,time);
        this.info_vic=new HashMap<>();
        info_vic.put(seed,vic);
        this.info_sight=new HashMap<>();
        info_sight.put(seed,sightOn);
        this.info_enemy=new HashMap<>();
        info_enemy.put(seed,enemy);


    }
    GameInfo(GameInfo old_info,Position p,long seed,TETile[][] world,long time,Position vic,boolean sightOn,Enemy enemy){


        this.info=old_info.getInfo();
        info.put(seed,p);
        this.info_world=old_info.getInfo_world();
        info_world.put(seed,world);
        this.info_time=old_info.getInfo_time();
        info_time.put(seed,time);
        this.info_vic=old_info.getInfo_vic();
        info_vic.put(seed,vic);
        this.info_sight=old_info.getInfo_sight();
        info_sight.put(seed,sightOn);
        this.info_enemy=old_info.getInfo_enemy();
        info_enemy.put(seed,enemy);
    }

    public void saveInfo(Position p,long seed,TETile[][] world){

        info.put(seed,p);
        info_world.put(seed,world);

    }
    public boolean SeedIsExist(long seed){

        if(info.containsKey(seed))
            return true;
        else
            return false;

    }
    public Position getPlayerPosition(long seed){
        if(info.containsKey(seed))
            return info.get(seed);
        else
            return null;

    }
    public TETile[][] getWorld(long seed){
        if(info_world.containsKey(seed))
            return info_world.get(seed);
        else
            return null;

    }

    public Position getVictory(long seed){
        if(info_vic.containsKey(seed))
            return info_vic.get(seed);
        else
            return null;

    }
    public boolean getSightOn(long seed){
        if(info_sight.containsKey(seed))
            return info_sight.get(seed);
        else
            return false;

    }
    public Enemy getEnemy(long seed){
        if(info_enemy.containsKey(seed))
            return info_enemy.get(seed);
        else
            return null;

    }

    public long getTime(long seed){
        if(info_time.containsKey(seed))
            return info_time.get(seed);
        else
            return 0;

    }
    public void printInfo(){
        Set<Long> set=info.keySet();
        Iterator<Long> ite=set.iterator();

        while(ite.hasNext()){
            System.out.println(ite.next());
        }

    }
    public HashMap<Long,Position> getInfo(){
        return info;
    }
    public HashMap<Long,Position> getInfo_vic(){
        return info_vic;
    }
    public HashMap<Long,TETile[][]> getInfo_world(){
        return info_world;
    }
    public HashMap<Long,Long> getInfo_time(){
        return info_time;
    }
    public HashMap<Long,Boolean> getInfo_sight(){
        return info_sight;
    }
    public HashMap<Long,Enemy> getInfo_enemy(){
        return info_enemy;
    }

    public void remove(long seed){
        this.info.remove(seed);
        this.info_world.remove(seed);
        this.info_time.remove(seed);
        this.info_vic.remove(seed);
    }

}
