package student.examples.comm;

public enum Action {
    OK,
    ERROR,
    POKE,
    SHUTDOWN;


    public static Action getByInt(int value) {
        for (Action action : Action.values()) {
            if (action.ordinal() == value) {
                return action;
            }
        }
        return null;
    }
}

