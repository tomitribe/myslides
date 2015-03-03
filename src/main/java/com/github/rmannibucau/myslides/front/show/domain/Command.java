package com.github.rmannibucau.myslides.front.show.domain;

public class Command {
    public static final Command NEXT = new Command();
    public static final Command PREVIOUS = new Command();
    static {
        NEXT.setAction(Action.NEXT);
        PREVIOUS.setAction(Action.PREVIOUS);
    }

    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }
}
