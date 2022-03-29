package model;

import exception.AccessDeniedException;
import util.Usage;


public class RecycleBin {

    private boolean isBlocked;
    private Usage usage;

    public RecycleBin() {
        isBlocked = false;
        usage = Usage.none;
    }
    
    public Usage getUsage() {
        return usage;
    }
    
    public void setUsage(final Usage usage) {
        open();
        this.usage = usage;
    }
    
    public boolean isBlocked() {
        return isBlocked;
    }
    
    public void setIsBlocked(final boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    private void open() throws AccessDeniedException {
        if (isBlocked) {
            throw new AccessDeniedException();
        }
    }

}
