package dm.relationship.gm;

public class GmCommandGroupNameConstants {
    /**
     * 角色属性修改
     */
    public static final String Player = "pl";
    /**
     * 物品产入产出
     */
    public static final String ItemIo = "ito";
    /**
     * 商店
     */
    public static final String Shops = "shops";

    /**
     * PVE主线副本
     */
    public static final String Pve = "pve";

    /**
     * 武将
     */
    public static final String Heros = "heros";


    public enum ItemIoGmSupport {
        /**
         * <pre>
         * ItemIo操作如：
         * 1:100
         * 1:-100,2:100,3:100
         * </pre>
         */
        Op("Op"),;

        private String str;

        public String getStr() {
            return str;
        }

        ItemIoGmSupport(String str) {
            this.str = str;
        }
    }

    public enum PlayerIoGmSupport {
        /**
         * 直接设置等级
         */
        Lv("lv"),
        /**
         * 增加等级
         */
        ALv("alv"),
        /**
         * 增加经验
         */
        Exp("exp"),
        /**
         * 增加vip等级
         */
        VpLv("vplv"),;

        private String str;

        public String getStr() {
            return str;
        }

        PlayerIoGmSupport(String str) {
            this.str = str;
        }
    }

    public enum ShopsGmSupportEnum {
        /**
         * 触发神秘商店
         */
        TriggerMysterious("tms"),
        //
        ;
        private String str;

        public String getStr() {
            return str;
        }

        ShopsGmSupportEnum(String str) {
            this.str = str;
        }
    }

    public enum PveGmSupport {
        /**
         * <pre>
         *     开启章节-全部3星
         * </pre>
         */
        OpenOneChapter("ooc"),
        /**
         * <pre>
         *     开启全部章节-全部3星
         * </pre>
         */
        OpenAllChapter("oac"),
        //
        ;

        private String str;

        public String getStr() {
            return str;
        }

        PveGmSupport(String str) {
            this.str = str;
        }
    }

    public enum HerosGmSupport {
        /**
         * <pre>
         *     计算武将详细属性
         * </pre>
         */
        calcu_Hero_attr("cha"),
        //
        ;

        private String str;

        public String getStr() {
            return str;
        }

        HerosGmSupport(String str) {
            this.str = str;
        }
    }
}
