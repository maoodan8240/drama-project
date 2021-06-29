package dm.relationship.base;

/**
 * Created by lee on 17-4-14.
 */
public class RedisRankAndScore {
    private String member;  // 排名的成员
    private int rank;       // 排名
    private long score;     // 分数

    public RedisRankAndScore(String member, int rank, long score) {
        this.member = member;
        this.rank = rank;
        this.score = score;
    }

    public String getMember() {
        return member;
    }

    public int getRank() {
        return rank;
    }

    public long getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "[" + member + " : " + rank + " : " + score + "]";
    }
}
