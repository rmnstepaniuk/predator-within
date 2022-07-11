package src.com.steering.behaviours;

public enum Behaviour {
    WANDER("wander"),
    SEEK("seek"),
    FLEE("flee"),
    CONTAIN("contain");

    public final String name;

    Behaviour(String name) {
        this.name = name;
    }
}
