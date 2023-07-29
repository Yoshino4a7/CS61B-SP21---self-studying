package byow.Core;

import java.io.Serializable;
import java.util.Objects;

public  class Position implements Serializable {
    public int x;
    public int y;
    public int G;
    public int F;
    public int H;
    public Position parent;

    Position(int x,int y){

        this.x=x;
        this.y=y;



    }

    Position shift(int dx, int dy){
        Position p=new Position(x+dx,y+dy);
        return p;
    }

    public void setG(int G){
        this.G=G;

    }
    public void setH(int H){
        this.H=H;

    }

    public int getG(){
        return G;

    }
    public int getH(){
        return H;

    }
    public int getF(){

        return F;

    }
    public void setF(int F){
        this.F=F;


    }
    public void setConnection(Position p){
        this.parent=p;
    }
    public Position getConnection(){
        return parent;
    }
//    @Override


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, G, F, H);
    }
}
