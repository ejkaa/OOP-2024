package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{
    private Disposable disposable;
    private boolean repaired;
    public DefectiveLight() {}

    private void randomToggle() {
        double num = Math.random() * 20;
        if ((int) num == 1) toggle();
        repaired = false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        disposable = new Loop<>(new Invoke<>(this::randomToggle)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (disposable == null) return false;
        if (repaired) return false;

        repaired = true;
        disposable.dispose();

        new ActionSequence<>(
            new Wait<>(11),
            new Invoke<>(() -> {
                disposable = new Loop<>(new Invoke<>(this::randomToggle)).scheduleFor(this);
                repaired = false;
            })
        ).scheduleFor(this);
        return true;
    }
}
