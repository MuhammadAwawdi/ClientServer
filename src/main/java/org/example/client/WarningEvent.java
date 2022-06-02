package org.example.client;

import org.example.entities.Warning;

public class WarningEvent {
    private Warning warning;

    public Warning getWarning() {
        return warning;
    }

    public WarningEvent(Warning warning) {
        this.warning = warning;
    }
}