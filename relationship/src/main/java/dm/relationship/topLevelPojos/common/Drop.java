package dm.relationship.topLevelPojos.common;

import java.io.Serializable;

public class Drop implements Serializable {
    private static final long serialVersionUID = -5507426760995709529L;

    private int libraryId;
    private int hasDropTimes;
    private int totalDropTimes;

    public Drop() {
    }

    public Drop(int libraryId) {
        this.libraryId = libraryId;
    }

    public Drop(int libraryId, int hasDropTimes, int totalDropTimes) {
        this.libraryId = libraryId;
        this.hasDropTimes = hasDropTimes;
        this.totalDropTimes = totalDropTimes;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public int getHasDropTimes() {
        return hasDropTimes;
    }

    public void setHasDropTimes(int hasDropTimes) {
        this.hasDropTimes = hasDropTimes;
    }

    public int getTotalDropTimes() {
        return totalDropTimes;
    }

    public void setTotalDropTimes(int totalDropTimes) {
        this.totalDropTimes = totalDropTimes;
    }

    @Override
    public Drop clone() {
        return new Drop(libraryId, hasDropTimes, totalDropTimes);
    }
}
