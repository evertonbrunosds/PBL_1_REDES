package model;

import uefs.ComumBase.interfaces.Sender;

public class GarbageTruckAdministrator {

    private Sender<String> sender;

    public GarbageTruckAdministrator() {

    }

    public void setSender(final Sender<String> sender) {
        this.sender = sender;
    }

    public String getUsage() {
        return sender == null ? null : sender.send();
    }

}
