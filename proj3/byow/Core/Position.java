package byow.Core;

import java.io.Serializable;

public  class Position implements Serializable {
    public int x;
    public int y;

    Position(int x,int y){

        this.x=x;
        this.y=y;


    }

    Position shift(int dx, int dy){
        Position p=new Position(x+dx,y+dy);
        return p;
    }



}
