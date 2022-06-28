package triple.mileage.domain;

public enum Event {

    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    final String event;

    Event(String event) {
        this.event = event;
    }
}
