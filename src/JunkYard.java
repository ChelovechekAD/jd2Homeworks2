import java.util.*;

public class JunkYard extends Thread {

    private static JunkYard instance = null;
    private final List<Component> components = new ArrayList<>();
    private volatile boolean openState = false;
    private final List<Laboratory> factories = new ArrayList<>();
    private final Random random = new Random();

    private JunkYard() {
        for (int i = 0; i < ConstValues.START_COUNT_OF_COMPONENTS_ON_JUNK_YARD; i++) {
            components.add(generateComponent());
        }
    }

    public synchronized static JunkYard getInstance() {
        if (instance == null) {
            instance = new JunkYard();
        }
        return instance;
    }

    @Override
    public synchronized void run() {
        this.setName("JunkYard");
        for (int i = 0; i < ConstValues.COUNT_DAYS; i++) {
            //System.out.println("На свалке: " + this.components);
            dayTimeSwap();
        }

        sortFactories();
    }

    private synchronized void changeOpenState() {
        openState = !openState;
    }

    public synchronized void addFactories(Laboratory... factories) {
        this.factories.addAll(Arrays.asList(factories));
    }

    public Laboratory getFactoryOnPos(int pos) throws IndexOutOfBoundsException {
        return factories.get(pos);
    }

    private Component generateComponent() {
        Random random = new Random();
        return Component.valueOf(
                ConstValues.componentsNames[random.nextInt(ConstValues.COUNT_OF_COMPONENTS_TYPE)]);

    }

    public boolean isOpenState() {
        return openState;
    }

    public synchronized Component pickUpComponent() throws Exceptions.ComponentsNotFound, InterruptedException {
        try {
            Component component = components.get(0);
            components.remove(0);
            return component;
        } catch (IndexOutOfBoundsException e) {
            openState = false;
            throw new Exceptions.ComponentsNotFound();
        }
    }

    public void refreshComponents() {
        int countComp = random.nextInt(ConstValues.MAX_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT + 1
                - ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT)
                + ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT;
        for (int i = 0; i < countComp + 1; i++) {
            components.add(generateComponent());
        }
    }

    private synchronized void dayTimeSwap() {
        try {
            wait(ConstValues.DAY_TIME_SWAP_TIME);
            if (this.openState) {
                this.openState = false;
            } else {
                refreshComponents();
                this.openState = true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sortFactories() {
        Collections.sort(factories);
    }


}
