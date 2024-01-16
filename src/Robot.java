import java.util.HashMap;
import java.util.Map;

public class Robot {

    private int countParts = 0;

    private final Map<Component, Boolean> components = new HashMap<>();

    public void addCountParts() {
        countParts++;
    }

    public int getCountParts() {
        return countParts;
    }

    public Map<Component, Boolean> getComponents() {
        return components;
    }


    public Robot() {
        components.put(Component.HEAD, false);
        components.put(Component.BODY, false);
        components.put(Component.LEFT_ARM, false);
        components.put(Component.RIGHT_ARM, false);
        components.put(Component.LEFT_LEG, false);
        components.put(Component.RIGHT_LEG, false);
        components.put(Component.CPU, false);
        components.put(Component.RAM, false);
        components.put(Component.HDD, false);
    }


}
