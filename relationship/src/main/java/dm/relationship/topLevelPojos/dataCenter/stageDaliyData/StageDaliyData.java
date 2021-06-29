package dm.relationship.topLevelPojos.dataCenter.stageDaliyData;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.LinkedHashMap;

public class StageDaliyData extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -7529885107274959937L;

    private LinkedHashMap<Integer, Data> dateToData = new LinkedHashMap<>(); // Key为日期, 格式为:YYYYMMDD

    public StageDaliyData() {
    }

    public StageDaliyData(String playerId) {
        super(playerId);
    }

    public LinkedHashMap<Integer, Data> getDateToData() {
        return dateToData;
    }

    public void setDateToData(LinkedHashMap<Integer, Data> dateToData) {
        this.dateToData = dateToData;
    }
}
