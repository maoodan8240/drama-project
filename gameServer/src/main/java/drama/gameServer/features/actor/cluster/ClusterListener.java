package drama.gameServer.features.actor.cluster;

import akka.actor.ActorContext;
import akka.actor.Address;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.ClusterDomainEvent;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberExited;
import akka.cluster.ClusterEvent.MemberJoined;
import akka.cluster.ClusterEvent.MemberLeft;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.ReachableMember;
import akka.cluster.ClusterEvent.RoleLeaderChanged;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.metrics.ClusterMetricsChanged;
import akka.cluster.metrics.ClusterMetricsExtension;
import dm.relationship.utils.ClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterListener extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(ClusterListener.class);
    private Cluster cluster = Cluster.get(getContext().system());
    private ClusterMetricsExtension metrics = ClusterMetricsExtension.get(getContext().system());

    private static ActorContext actorContext;
    private static Address address;
    private int num = 0;

    public ClusterListener() {
        actorContext = getContext();
        address = cluster.selfAddress();
    }

    @Override
    public void preStart() throws Exception {
        cluster.subscribe(getSelf(), ClusterDomainEvent.class);
        metrics.subscribe(getSelf());
    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(getSelf());
        metrics.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg instanceof ClusterMetricsChanged) {
            num++;
            if (num >= 40) { // 2分钟
                ClusterUtils.displayAllMemberInfo(actorContext);
                ClusterUtils.displaySystemHealthInfo((ClusterMetricsChanged) msg);
                num = 0;
            }
        } else if (msg instanceof MemberUp) {
            onMemberUp((MemberUp) msg);
        } else if (msg instanceof MemberLeft) {
            onMemberLeft((MemberLeft) msg);
        } else if (msg instanceof MemberJoined) {
            onMemberJoined((MemberJoined) msg);
        } else if (msg instanceof MemberExited) {
            onMemberExited((MemberExited) msg);
        } else if (msg instanceof MemberRemoved) {
            onMemberRemoved((MemberRemoved) msg);
        } else if (msg instanceof LeaderChanged) {
            onLeaderChanged((LeaderChanged) msg);
        } else if (msg instanceof ReachableMember) {
            onReachableMember((ReachableMember) msg);
        } else if (msg instanceof RoleLeaderChanged) {
            onRoleLeaderChanged((RoleLeaderChanged) msg);
        } else if (msg instanceof UnreachableMember) {
            onUnreachableMember((UnreachableMember) msg);
        } else if (msg instanceof CurrentClusterState) {
            onCurrentClusterState((CurrentClusterState) msg);
        }
    }

    private void onMemberUp(MemberUp msg) {
        logger.info("\n\n---------------------------------onMemberUp------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onMemberLeft(MemberLeft msg) {
        logger.info("\n\n---------------------------------onMemberLeft------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onMemberJoined(MemberJoined msg) {
        logger.info("\n\n---------------------------------onMemberJoined------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onMemberExited(MemberExited msg) {
        logger.info("\n\n---------------------------------onMemberExited------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onMemberRemoved(MemberRemoved msg) {
        logger.info("\n\n---------------------------------onMemberRemoved------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onLeaderChanged(LeaderChanged msg) {
        logger.info("\n\n---------------------------------onLeaderChanged------------------------------------\n" +
                        "leader={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.getLeader());
    }

    private void onReachableMember(ReachableMember msg) {
        logger.info("\n\n---------------------------------onReachableMember------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }

    private void onRoleLeaderChanged(RoleLeaderChanged msg) {
        logger.info("\n\n---------------------------------onRoleLeaderChanged------------------------------------\n" +
                        "leader={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.getLeader());
    }

    private void onUnreachableMember(UnreachableMember msg) {
        logger.info("\n\n---------------------------------onUnreachableMember------------------------------------\n" +
                        "address={}, roles={}" +
                        "\n------------------------------------------------------------------------------\n\n",
                msg.member().address(), msg.member().getRoles());
    }


    private void onCurrentClusterState(CurrentClusterState msg) {
        logger.info("\n\n----------------------------onCurrentClusterState----------------------------\n" +
                        "Leader={}\nMembers={}\nAllRoles={}\nUnreachable={}\nSeenBy={}\n" +
                        "-----------------------------------------------------------------------------\n\n",
                msg.getLeader(), msg.getMembers(),
                ClusterUtils.appendMemberSet(msg.getUnreachable()),
                ClusterUtils.appendMemberSet(msg.getSeenBy()));

        ClusterUtils.displayAllMemberInfo(getContext());
    }

    public static ActorContext getActorContext() {
        return actorContext;
    }

    public static Address getAddress() {
        return address;
    }
}