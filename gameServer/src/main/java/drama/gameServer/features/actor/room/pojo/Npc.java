package drama.gameServer.features.actor.room.pojo;

import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/10/26
 */
public class Npc {
    private String npcId;
    private int roleId;
    private String roleName;
    private boolean isLive;
    private String roleProfile;
    private List<Integer> equipEnums;

    private List<SpecialCell> roomPlayerItems = new ArrayList<>();
    /**
     * 被谁击杀
     */
    private int killerRoleId;

    public Npc(String npcId, int roleId, String roleName, boolean isLive, String roleProfile, List<Integer> equipEnums) {
        this.npcId = npcId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.isLive = isLive;
        this.roleProfile = roleProfile;
        this.equipEnums = equipEnums;
    }

    public List<SpecialCell> getRoomPlayerItems() {
        return roomPlayerItems;
    }

    public int getKillerRoleId() {
        return killerRoleId;
    }

    public void setKillerRoleId(int killerRoleId) {
        this.killerRoleId = killerRoleId;
    }

    public String getNpcId() {
        return npcId;
    }

    public void setNpcId(String npcId) {
        this.npcId = npcId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getRoleProfile() {
        return roleProfile;
    }

    public void setRoleProfile(String roleProfile) {
        this.roleProfile = roleProfile;
    }

    public List<Integer> getEquipEnums() {
        return equipEnums;
    }

    public void setEquipEnums(List<Integer> equipEnums) {
        this.equipEnums = equipEnums;
    }
}
