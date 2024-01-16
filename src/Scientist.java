import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scientist extends Thread {
    private int robotCreatedCounter = 0;
    private final List<Robot> robotsQueue = new ArrayList<>();
    private final String name;

    private final Laboratory linkedLaboratory;

    public Scientist(Laboratory laboratory, String name_th) {
        this.linkedLaboratory = laboratory;
        this.name = name_th;
    }

    @Override
    public synchronized void run() {
        JunkYard junkYard = JunkYard.getInstance();
        this.setName(name);
        robotsQueue.add(new Robot());
        while (junkYard.getState() != State.TERMINATED) {
            try {
                wait(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int iter = 0;
            List<Component> components = new ArrayList<>(linkedLaboratory.getComponents());
            for (Component component : components) {
                while (true) {
                    try {
                        if (robotsQueue.get(iter).getCountParts() != ConstValues.COUNT_OF_COMPONENTS_TYPE) {
                            Map<Component, Boolean> map = robotsQueue.get(iter).getComponents();
                            if (!map.get(component)) {
                                map.put(component, true);
                                robotsQueue.get(iter).addCountParts();
                                break;
                            } else iter++;
                        } else {
                            robotCreatedCounter++;
                            robotsQueue.remove(iter);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        robotsQueue.add(new Robot());
                    }
                }
            }
            //System.out.println(robotCreatedCounter);;
            linkedLaboratory.setCountRobots(robotCreatedCounter);
        }
        System.out.println(this.getName() + " завершил работу.");


    }
}
