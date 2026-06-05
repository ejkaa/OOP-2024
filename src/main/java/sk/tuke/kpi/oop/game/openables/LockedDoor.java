package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door {
    private boolean locked;
    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
        locked = true; }

    public boolean isLocked() { return locked; }
    public void lock(){
        locked = true;
        close();
    }
    public void unlock(){
        locked = false;
        open();
    }

    @Override
    public void open() {
        if (!isLocked()) super.open();
    }

}
