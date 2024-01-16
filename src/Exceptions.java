public interface Exceptions {

    class ComponentsNotFound extends Exception {
        public ComponentsNotFound() {
            super("Component not found. Ny bivaet i takoe.");
        }
    }

}
