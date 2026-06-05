package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private int temperature;
    private int damage;
    private boolean state;
    private Animation offReactor;
    private Animation normalReactor;
    private Animation hotReactor;
    private Animation brokenReactor;
    private Animation extinguishedReactor;
    private Set<EnergyConsumer> devices;

    public Reactor(){ // toto je konstruktor
        temperature = 0;
        damage = 0;
        state = false;
        devices = new HashSet<>();

        offReactor = new Animation("sprites/reactor.png");
        normalReactor = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotReactor = new Animation("sprites/reactor_hot.png", 80, 80,0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenReactor = new Animation("sprites/reactor_broken.png", 80, 80,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedReactor = new Animation("sprites/reactor_extinguished.png");
        updateAnimation();
    }

    private void updateAnimation(){
        if(damage == 100) setAnimation(brokenReactor);
        else if (!state) setAnimation(offReactor);
        else if(temperature > 4000) setAnimation(hotReactor);
        else setAnimation(normalReactor);
    }

    public int getDamage(){
        return damage;
    }
    public int getTemperature(){
        return temperature;
    }
    public void setDamage(int damage) { this.damage = damage; }
    public void setTemperature(int temperature) { this.temperature = temperature; }

    public void increaseTemperature(int increment){
        if (temperature >= 6000 || increment <= 0 || damage >= 100) return;
        if (!state) return;

        temperature += handleTemperature(increment);
        damage = handleDamage();

        if (damage > 100) damage = 100;
        if (damage == 100) turnOff();

        updateAnimation();
    }

    private int handleTemperature(int increment){
        if (damage < 33) return increment;
        else if (damage <= 66) return  (int)Math.ceil(increment*1.5);
        else return  2*increment;
    }
    private int handleDamage(){
        if (temperature >= 6000) return 100;
        if (temperature < 2000) return damage;

        int new_damage = (int)Math.floor((double)(temperature-2000)/40);
        return Math.max(new_damage, damage);
    }

    public void decreaseTemperature(int decrement){
        if (temperature == 0 || decrement<= 0 || damage == 100) return;
        if (!state) return; // method 'decreaseTemperature()' should have no effect: FAILED ???
        if (damage >= 50) temperature -= decrement/2;
        else temperature -= decrement;
        updateAnimation();
    }

    public boolean repair(){
        if (damage == 0 || damage == 100) return false;

        damage-=50; // toto sa este zmeni (nelinearne)
        int new_temperature = 40*damage + 2000;
        if (new_temperature < temperature) temperature = new_temperature;
        if (damage<=0) damage = 0;
        updateAnimation();
        return true;
    }

    public boolean extinguish(){
        if (temperature <= 4000) return false;

        temperature = 4000;
        setAnimation(extinguishedReactor);
        return true;
    }

    public void addDevice(EnergyConsumer device){
        if (device == null) return;
        devices.add(device);
        device.setPowered(isOn());
    }
    public void removeDevice(EnergyConsumer device) {
        if (device == null) return;
        devices.remove(device);
        device.setPowered(false);
    }

    @Override
    public boolean isOn() { return state; }
    @Override
    public void turnOn(){
        if(damage>=100) return;
        state = true;
        devices.forEach(device -> device.setPowered(true));
        updateAnimation();
    }
    @Override
    public void turnOff(){
        state = false;
        devices.forEach(device -> device.setPowered(false));
        updateAnimation();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
