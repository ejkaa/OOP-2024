package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private Animation onAnimation;
    private Animation offAnimation;
    private boolean state;
    private boolean power;

    public Light() {
        onAnimation = new Animation("sprites/light_on.png");
        offAnimation = new Animation("sprites/light_off.png");
        state = false;
        power = false;
        setAnimation(offAnimation);
    }
    private void updateAnimation(){
        if (state && power) setAnimation(onAnimation);
        else setAnimation(offAnimation);
    }

    public void toggle() {
        state = !state;
        updateAnimation();
    }

    @Override
    public boolean isOn() { return state; }
    @Override
    public void turnOn() {
        state = true;
        updateAnimation();
    }
    @Override
    public void turnOff() {
        state = false;
        updateAnimation();
    }

    public void setPowered(boolean power) {
        this.power = power;
        updateAnimation();
    }




}
