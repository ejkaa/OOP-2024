package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private Reactor reactor;
    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.reactor = reactor;
    }
    private void toggle() {
        if (reactor == null) return;
        int temperature = reactor.getTemperature();
        if (temperature > 2500) turnOn();
        else if (temperature <= 1500) turnOff();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::toggle)).scheduleFor(this); //Prípadné argumenty sú metóde dodané až pri použití referencie.
    }
}
