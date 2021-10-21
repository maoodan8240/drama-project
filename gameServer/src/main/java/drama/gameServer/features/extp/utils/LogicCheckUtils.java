package drama.gameServer.features.extp.utils;

import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos;
import ws.common.table.table.interfaces.Row;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

/**
 * Created by lee on 16-9-20.
 */
public class LogicCheckUtils {

    /**
     * 是否是指定类型的特殊Id
     *
     * @param id
     * @param type
     * @return
     */
    public static boolean isSpecialedId(int id, IdItemTypeEnum type) {
        IdItemTypeEnum idItemType = IdItemTypeEnum.parseByItemTemplateId(id);
        return idItemType == type;
    }


    /**
     * 校验参数基本合法性
     *
     * @param type
     * @param value
     * @param <T>
     */
    public static <T> void validateParam(Class<T> type, T value) {
        requireNonNull(value, type);
        String msg = String.format("参数Class=[%s]异常, value=%s", type, value);
        if (type.equals(Long.class)) {
            if ((Long) value <= 0) {
                throw new BusinessLogicMismatchConditionException(msg);
            }
        } else if (type.equals(String.class)) {
            if (((String) value).length() == 0) {
                throw new BusinessLogicMismatchConditionException(msg);
            }
        } else if (type.equals(Double.class)) {
            if ((Double) value <= 0) {
                throw new BusinessLogicMismatchConditionException(msg);
            }
        } else if (type.equals(Integer.class)) {
            if ((Integer) value <= 0) {
                throw new BusinessLogicMismatchConditionException(msg);
            }
        } else if (type.equals(Float.class)) {
            if ((Float) value <= 0) {
                throw new BusinessLogicMismatchConditionException(msg);
            }
        }
    }


    /**
     * 空检查
     *
     * @param t
     */
    public static <T> void requireNonNull(T t, Class<T> clzz) {
        if (t == null) {
            String msg = String.format("对象不能为空！Class=%s", clzz.getSimpleName());
            throw new BusinessLogicMismatchConditionException(msg);
        }
    }

    /**
     * 检查策划表数据
     *
     * @param t
     * @param clzz
     * @param tpId
     * @param <T>
     */
    public static <T> void requireNonNullForTable(T t, Class<T> clzz, int tpId) {
        if (t == null) {
            String msg = String.format("策划数据不能为空！Table=%s  tpId=%s", clzz.getSimpleName(), tpId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
    }

//    /**
//     * 检测是否是特殊id
//     *
//     * @param id
//     */
//    public static void specialItemIdCheck(int id) {
//        if (!ItemBagUtils.isSpecialItemId(id)) {
//            String msg = String.format("id=%s 不是特殊id！", id);
//            throw new BusinessLogicMismatchConditionException(msg);
//        }
//    }
//
//
//    /**
//     * 检测资源物品道具是否足够
//     */
//    public static void canRemove(ItemIoCtrl itemIoCtrl, IdMaptoCount idMaptoCount) {
//        if (!itemIoCtrl.canRemove(idMaptoCount)) {
//            String msg = String.format("Item=%s，资源不足！", idMaptoCount.toString());
//            throw new BusinessLogicMismatchConditionException(msg);
//        }
//    }
//
//    /**
//     * 检测资源物品道具是否足够
//     */
//    public static void canRemove(ItemIoCtrl itemIoCtrl, IdAndCount idAndCount) {
//        if (!itemIoCtrl.canRemove(idAndCount)) {
//            String msg = String.format("Item=%s，资源不足！", idAndCount.toString());
//            throw new BusinessLogicMismatchConditionException(msg);
//        }
//    }
//
//    /**
//     * 检测资源物品道具是否可以增加
//     */
//    public static void canAdd(ItemIoCtrl itemIoCtrl, IdMaptoCount idMaptoCount) {
//        if (!itemIoCtrl.canAdd(idMaptoCount)) {
//            String msg = String.format("不能增加Item=%s ！", idMaptoCount.toString());
//            throw new BusinessLogicMismatchConditionException(msg);
//        }
//    }

    /**
     * 检查数据表是否包含TpId
     *
     * @param rowType
     * @param tpId
     * @param <RowType>
     */
    public static <RowType extends Row> void checkTableHasTpId(Class<RowType> rowType, int tpId) {
        if (!RootTc.has(rowType, tpId)) {
            String msg = String.format("数据表=%s ！不含该数据行，Id=%s ", rowType, tpId);
            throw new BusinessLogicMismatchConditionException(msg);
        }
    }

    /**
     * 检查InnerMsg Response是否正常
     *
     * @param response
     */
    public static void checkResponse(InnerMsg response) {
        if (response.getResultCode() != null && response.getResultCode().getCode() != ResultCodeEnum.SUCCESS.getCode()) {
            throw new BusinessLogicMismatchConditionException(response.getResultCode().getMessage(), EnumsProtos.ErrorCodeEnum.valueOf(response.getResultCode().getCode()));
        }
    }


    /**
     * 创建异常ResultCode
     *
     * @param e
     * @return
     */
    public static ResultCode createResultCode(BusinessLogicMismatchConditionException e) {
        ResultCode resultCode = new ResultCode() {
            @Override
            public int getCode() {
                return e.getErrorCodeEnum().getNumber();
            }

            @Override
            public String getMessage() {
                return e.getMessage();
            }
        };
        return resultCode;
    }
}
