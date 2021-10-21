package drama.gameServer.features.actor.message;

/**
 * Created by lee on 2021/10/11
 */
public enum PackageEnums {

    ROOM("room");
    private String packageName;

    PackageEnums(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
