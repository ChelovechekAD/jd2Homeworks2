import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minion extends Thread {

    private final Laboratory linkedLaboratory;

    public void pickUpComponent(Component component) {
        backpack.add(component);
    }

    public List<Component> getComponents() {
        return backpack;
    }

    private final String name;

    private void refreshBackpack() {
        backpack.clear();
    }

    private final Random random = new Random();
    private boolean completedTask = false;

    public Minion(Laboratory laboratory, String min_name) {
        this.linkedLaboratory = laboratory;
        this.name = min_name;
    }

    private final List<Component> backpack = new ArrayList<>();


    @Override
    public synchronized void run() {
        JunkYard junkYard = JunkYard.getInstance();
        this.setName(name);
        //TODO junkYard.isAlive() -- Поток после создания является new и не считается как Alive,
        // след-но данный поток сразу же завершится.
        while (junkYard.getState() != State.TERMINATED) {

            refreshBackpack();

            //TODO Ждем запуска свалки
            while (true){
                if (junkYard.getState() != State.NEW){
                    break;
                }
            }

            while (junkYard.isAlive() && junkYard.isOpenState() && !this.completedTask) {
                //System.out.println("Зашел на свалку");
                int countComp = random.nextInt(ConstValues.MAX_COUNT_COMPONENTS_ROBOT_TAKE + 1
                        - ConstValues.MIN_COUNT_COMPONENTS_ROBOT_TAKE)
                        + ConstValues.MIN_COUNT_COMPONENTS_ROBOT_TAKE;
                System.out.println(this.getName() + " заберет " + countComp);
                for (int i = 0; i < countComp; i++) {
                    try {
                        pickUpComponent(junkYard.pickUpComponent());
                    } catch (Exceptions.ComponentsNotFound e) {
                        break;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.completedTask = true;
                System.out.println(this.getName() + " забрал " + backpack);
            }

            // TODO Ждем закрытия свалки.
            while (true) {
                if (!junkYard.isOpenState()) {
                    this.completedTask = false;
                    break;
                }
            }

            //System.out.println("Ушел");
            linkedLaboratory.putComponents(this.getComponents());

        }
        System.out.println(this.getName() + " завершил работу.");
    }

}
