package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.LinkedQueue;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Enemy implements Serializable {
    public Position enemy_pos;
    public LinkedList<Position> route;

    Enemy(Position enemy_pos,LinkedList<Position> route)
    {
        this.enemy_pos=enemy_pos;
        this.route=route;
    }

    public void setQueue(LinkedList<Position> route)
    {
        this.route=route;
    }

    public void setEnemy_pos(Position pos)
    {
        this.enemy_pos=pos;
    }

    public Position getEnemy_pos(){
        return enemy_pos;

    }
    public LinkedList<Position> getRoute(){
        return route;

    }

    public LinkedList<Position> searchRoute(Position player, TETile[][] world){
        route=new LinkedList<>();
        boolean[][] isMarked=new boolean[mazeDemo.WIDTH][mazeDemo.HEIGHT];
        intial_marked(isMarked);
//        double distance=getDistance(player,this.enemy_pos);
//        Position pos=this.enemy_pos;
        searchRoute_helper(player,enemy_pos,world,route);


        return route;
    }
//    private void searchRoute_helper(Position player,Position enemy,TETile[][] world,LinkedList<Position> route){
//
//        Position start=enemy;
//        LinkedList<Position> close_list=new LinkedList<>();
//        int G=0;
//
//
//
//        while((enemy.y!=player.y||enemy.x!= player.x)&&route.size()<=100)
//        {
//            Position pos_up=enemy.shift(0,1);
//            Position pos_down=enemy.shift(0,-1);
//            Position pos_left=enemy.shift(-1,0);
//            Position pos_right=enemy.shift(1,0);
//
////            double distance=getDistance(player,enemy);
//
//
//            double dis_up=getDistance(player,pos_up,start,G);
//            double dis_down=getDistance(player,pos_down,start,G);
//            double dis_left=getDistance(player,pos_left,start,G);
//            double dis_right=getDistance(player,pos_right,start,G);
//
//            ArrayList<Double> dis=new ArrayList<>();
//
//            if (mazeDemo.isROAD(pos_up,world)){
//                dis.add(dis_up);
//            }
//
//            if (mazeDemo.isROAD(pos_down,world)){
//                dis.add(dis_down);
//            }
//            if (mazeDemo.isROAD(pos_left,world)){
//                dis.add(dis_left);
//            }
//            if (mazeDemo.isROAD(pos_right,world)){
//                dis.add(dis_right);
//            }
//
//
//
//            if(!route.isEmpty()){
//                if (route.getLast().y-1== enemy.y){
//                    dis.remove(dis_up);
//                }
//                if (route.getLast().y+1== enemy.y){
//                    dis.remove(dis_down);
//                }
//                if (route.getLast().x+1== enemy.x){
//                    dis.remove(dis_left);
//                }
//                if (route.getLast().x-1== enemy.x){
//                    dis.remove(dis_right);
//                }
//            }else
//                route.addLast(enemy);
//
//
//
//
//            double dis_min=getMin(dis);
//
//            if (dis_min==dis_up&&route.getLast().y-1!= enemy.y)
//            {
//
//                route.addLast(enemy);
//                enemy=pos_up;
//                continue;
//            }
//            if (dis_min==dis_down&&route.getLast().y+1!= enemy.y)
//            {
//                route.addLast(enemy);
//                enemy=pos_down;
//                continue;
//            }
//            if (dis_min==dis_left&&route.getLast().x+1!= enemy.y)
//            {
//                route.addLast(enemy);
//                enemy=pos_left;
//                continue;
//            }
//            if (dis_min==dis_right&&route.getLast().x-1!= enemy.y)
//            {
//                route.addLast(enemy);
//                enemy=pos_right;
//                continue;
//            }
//        }
//
//        G++;
//
//
//    }
    private void intial_marked(boolean[][] isMarked){
        for(int x=0;x<mazeDemo.WIDTH;x++){
            for(int y=0;y<mazeDemo.HEIGHT;y++)
                isMarked[x][y]=false;

        }
    }
//    private void searchRoute_helper(Position player,Position enemy,TETile[][] world,LinkedList<Position> route){
//        PriorityQueue<Position> open_queueF=new PriorityQueue<>(new Comparator<Position>() {
//            @Override
//            public int compare(Position o1, Position o2) {
//                int F1=o1.getF();
//                int F2=o2.getF();
//                return F1-F2;
//
//            }
//        });
//        PriorityQueue<Position> open_queueG=new PriorityQueue<>(new Comparator<Position>() {
//            @Override
//            public int compare(Position o1, Position o2) {
//                int G1=o1.getG();
//                int G2=o2.getG();
//                return G1-G2;
//
//            }
//        });
//
//        boolean[][] isMarked=new boolean[mazeDemo.WIDTH][mazeDemo.HEIGHT];
//        intial_marked(isMarked);
//        Position start=enemy;
//        isMarked[start.x][start.y]=true;
//        HashMap<Position,Position> came_from=new HashMap<>();
//        HashMap<Position,Integer> cost_so_far=new HashMap<>();
//        route.addLast(start);
//        start.setF(cost(start,player));
//        open_queueF.add(start);
//        cost_so_far.put(start,0);
//        came_from.put(start,null);
//
//
//
//        while(!open_queueF.isEmpty()&&route.size()<=660){
//            Position current=open_queueF.poll();
//
//            if(current.equals(player))
//            {
//                break;
//            }
//
//            Position[] adjacent=
//                    {current.shift(0,1),current.shift(0,-1),current.shift(-1,0),current.shift(1,0)};
//
//            for(int i=0;i<adjacent.length;i++)
//            {
//                Position next=adjacent[i];
//                if(mazeDemo.isROAD(next,world)){
//                    int new_cost=cost_so_far.get(start)+cost(current,next);
//                    int F=new_cost+cost(next,player);
//                    if(!cost_so_far.containsKey(next)||(cost_so_far.containsKey(next)&&F<cost_so_far.get(next))){
//
//
//
//                        next.setF(F);
//                        open_queueF.add(next);
//                        cost_so_far.put(next,F);
//                        came_from.put(next,current);
//                        route.addLast(next);
//
//                    }
//                }
//
//            }
//
//
//        }
//
////        Position pos=player;
////
////
////        while(!pos.equals(start)){
////            route.addFirst(pos);
////            pos=came_from.get(pos);
////            if(pos==null)
////            {
////               break;
////            }
////
////        }
//
//
//
//
//
//
//    }

    private void searchRoute_helper(Position player,Position enemy,TETile[][] world,LinkedList<Position> route){
        LinkedList<Position> toSearch=new LinkedList<>();
        int G=0;
        enemy.setG(G);
        enemy.setF(G+cost(player,enemy));
        enemy.setH(cost(player,enemy));

        toSearch.addLast(enemy);
        LinkedList<Position> processed=new LinkedList<>();

        while(!toSearch.isEmpty()){
            Position current=toSearch.getFirst();

            current=getFmin(toSearch);

            processed.add(current);
            toSearch.remove(current);




            Position[] adjacent=
                    {current.shift(0,1),current.shift(0,-1),current.shift(-1,0),current.shift(1,0)};

            for(int i=0;i<adjacent.length;i++)
            {
                Position next=adjacent[i];

                if(next.equals(player)){
                    player.setConnection(current);
                    Position currentPathTile=player.getConnection();
                    while(!currentPathTile.equals(enemy)){
                        route.addFirst(currentPathTile);
                        currentPathTile=currentPathTile.getConnection();
                        if(currentPathTile==null)
                            break;
                    }

                return;
                }


                if(mazeDemo.isROAD(next,world)&&!processed.contains(next)){
                    boolean inSearch=toSearch.contains(next);
                    int costToNext=current.getG()+cost(current,next);

                    if(!inSearch||costToNext<next.getG()){
                        next.setG(costToNext);
                        next.setConnection(current);

                        if(!inSearch){
                            next.setH(cost(next,player));
                            next.setF(next.getG()+next.getH());
                            toSearch.add(next);
                        }
                    }

                }
            }






        }
    }
    public Position getFmin(LinkedList<Position> list){

        Iterator<Position> ite=list.iterator();
        Position min_p=null;
        if(!list.isEmpty())
        min_p=list.get(0);

        while(ite.hasNext()){
            Position p=ite.next();
            if(p.getF()<min_p.getF()||min_p.getF()==p.getF()&&p.getH()<min_p.getH())
                min_p=p;

        }
        return min_p;


    }

    private int cost(Position current,Position next){

        return Math.abs(current.x-next.x)+Math.abs(current.y-next.y);


    }

//    private void searchRouteDFS_helper
//            (Position player,Position enemy,TETile[][] world,LinkedList<Position> route,boolean[][] isMarked){
//
//        if(route.size()<=630){
//            Position pos_up=enemy.shift(0,1);
//            Position pos_down=enemy.shift(0,-1);
//            Position pos_left=enemy.shift(-1,0);
//            Position pos_right=enemy.shift(1,0);
//                    if(isMarked[enemy.x][enemy.y])
//                    {
//                        return;
//                    }
//                    else if(enemy.x!=player.x||enemy.y!=player.y){
//                        route.addLast(enemy);
//                        isMarked[enemy.x][enemy.y]=true;
//                        if(mazeDemo.isROAD(pos_up,world))
//                            searchRouteDFS_helper(player,pos_up,world,route,isMarked);
//                        if(mazeDemo.isROAD(pos_down,world))
//                            searchRouteDFS_helper(player,pos_down,world,route,isMarked);
//                        if(mazeDemo.isROAD(pos_left,world))
//                            searchRouteDFS_helper(player,pos_left,world,route,isMarked);
//                        if(mazeDemo.isROAD(pos_right,world))
//                            searchRouteDFS_helper(player,pos_right,world,route,isMarked);
//
//
//
//
//
//                    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        }else
//            return;
//
//
//
//
//    }








    public double getDistance(Position player,Position enemy,Position start,double G,int G_walk){
        double distance=Math.sqrt(Math.pow((player.x-enemy.x),2)+Math.pow((player.y-enemy.y),2))+G_walk;


//        double distance=Math.abs(player.x-enemy.x)+Math.abs(player.y-enemy.y);
        return distance;

    }
    public double getDistanceG(Position player,Position enemy,Position start){
        double distance=Math.abs(enemy.x-start.x)+Math.abs(enemy.y-start.y);


//        double distance=Math.abs(player.x-enemy.x)+Math.abs(player.y-enemy.y)+Math.abs(start.x-enemy.x)+Math.abs(start.y-enemy.y);
        return distance;

    }
    public boolean Min_not_Only(ArrayList<Double> array,double min){
        if(array.isEmpty())
            return false;
        int sum=0;
        int i=0;

        while(i<array.size()){
            if(array.get(i)==min) {
                sum++;

            }
            if(sum>=2)
                return true;
            i++;
        }
        return false;
    }

    public void moveTo(Position pos,TETile[][] world){

        TETile old_floor=null;
        TETile new_floor=null;
        old_floor=new TETile('·', Color.WHITE,world[enemy_pos.x][enemy_pos.y].getBackgroundColor(),
                "floor");
        if(!world[pos.x][pos.y].getBackgroundColor().equals(world[enemy_pos.x][enemy_pos.y].getBackgroundColor()))

            new_floor=new TETile('#', new Color(216, 128, 128), world[pos.x][pos.y].getBackgroundColor(), "enemy");
        else
            new_floor=new TETile('#', new Color(216, 128, 128), old_floor.getBackgroundColor(), "enemy");


        world[pos.x][pos.y]=new_floor;
        world[enemy_pos.x][enemy_pos.y]=old_floor;
        enemy_pos=pos;


    }

    public double getMin(ArrayList<Double> array){
        if(array.isEmpty())
            return 0;

        int i=0;
        double min=array.get(0);
        while(i<array.size()){
            if(array.get(i)<=min)
                min=array.get(i);

            i++;
        }
        return min;

    }
}
