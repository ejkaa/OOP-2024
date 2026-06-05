package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int ammo;
    private int maxAmmo;

    public Firearm(int maxAmmo) { this(maxAmmo, maxAmmo); }
    public Firearm(int ammo, int maxAmmo) {
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
    }

    public int getAmmo() { return ammo; }
    public int getMaxAmmo() { return maxAmmo; }
    public void reload(int newAmmo) {
        ammo += newAmmo;
        if (ammo > maxAmmo) ammo = maxAmmo;
    }

    protected abstract Fireable createBullet();
    public Fireable fire() {
        if (ammo < 1) return null;
        ammo -= 1;
        return createBullet();
    }
}
