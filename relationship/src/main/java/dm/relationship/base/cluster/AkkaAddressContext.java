package dm.relationship.base.cluster;

import akka.actor.Address;
import dm.relationship.base.ServerEnvEnum;

public class AkkaAddressContext {
    public static final AkkaAddressContext NULL = new AkkaAddressContext();

    /**
     * 格式为：center-server-192.168.8.173-20100
     */
    private String roleName;
    private Address address;
    private ServerEnvEnum env;

    public AkkaAddressContext() {
    }

    public AkkaAddressContext(String roleName, Address address, ServerEnvEnum env) {
        this.roleName = roleName;
        this.address = address;
        this.env = env;
    }

    public Address getAddress() {
        return address;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[")//
                .append("address=").append(address)//
                .append(" | roleName=").append(roleName)//
                .append(" | env=").append(env)//
                .append("]");
        return sb.toString();
    }
}
