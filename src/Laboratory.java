import java.util.ArrayList;
import java.util.List;

public class Laboratory implements Comparable<Laboratory> {
    private String factoryName;

    private Integer countRobots = 0;

    private final List<Component> components = new ArrayList<>();
    private final Minion minion;
    private final Scientist scientist;

    public Laboratory(String factoryName, String sc_name, String min_name) {
        this.minion = new Minion(this, min_name);
        this.scientist = new Scientist(this, sc_name);
        setFactoryName(factoryName);
    }
    public void startFactory(){
        minion.start();
        scientist.start();
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public synchronized int getCountRobots() {
        return countRobots;
    }

    public synchronized void setCountRobots(int countRobots) {
        this.countRobots = countRobots;
    }

    public synchronized List<Component> getComponents() {
        List<Component> rt = new ArrayList<>(components);
        components.clear();
        return rt;
    }

    public synchronized void putComponents(List<Component> components) {
        this.components.addAll(components);
    }

    @Override
    public int compareTo(Laboratory o) {
        return o.countRobots.compareTo(this.countRobots);
    }
}
