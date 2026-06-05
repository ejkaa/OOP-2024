package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private boolean power;
    public Computer() {
        Animation animation = new Animation("sprites/computer.png", 80, 48, 0.2f);
        setAnimation(animation);
        power = false;
    }

    @Override
    public void setPowered(boolean power) {
        this.power = power;
        if (power) getAnimation().play();
        else getAnimation().stop();
    }

    public int add(int x, int y){
        if (power) return x+y;
        else return 0;
    }
    public int sub(int x, int y){
        if (power) return x-y;
        else return 0;
    }

    public float add(float x, float y){
        if (power) return x+y;
        else return 0;
    }
    public float sub(float x, float y){
        if (power) return x-y;
        else return 0;
    }


}
