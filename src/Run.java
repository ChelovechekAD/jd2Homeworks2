public class Run {

    @FunctionalInterface
    interface FactoryPrinter {
        void print(Laboratory laboratory);
    }

    public static void main(String[] args) throws InterruptedException {
        Laboratory laboratory1 = new Laboratory("Factory1", "Sc1", "Min1");
        Laboratory laboratory2 = new Laboratory("Factory2", "Sc2", "Min2");
        JunkYard junkYard = JunkYard.getInstance();
        junkYard.addFactories(laboratory1, laboratory2);
        laboratory1.startFactory();
        laboratory2.startFactory();
        junkYard.start();
        junkYard.join();
        FactoryPrinter output = (o) -> System.out.println(o.getFactoryName() + " - " + o.getCountRobots());
        System.out.print("Winner: ");
        output.print(junkYard.getFactoryOnPos(0));
        System.out.print("Loser: ");
        output.print(junkYard.getFactoryOnPos(1));

    }
}