package drama.gameServer.features.actor.config;

import dm.relationship.base.MagicNumbers;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.msg.interfaces.ConfigNetWorkMsg;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.config.enums.ConfigTableNameEnums;
import drama.gameServer.features.actor.config.utils.ConfigUtils;
import drama.protos.CodesProtos;
import drama.protos.CommonProtos;
import drama.protos.MessageHandlerProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.table.data.TableDataHeader;
import ws.common.table.data.TableDataRow;

import java.util.ArrayList;
import java.util.List;

public class ConfigActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigActor.class);

    @Override
    public void onRecv(Object message) throws Exception {
        if (message instanceof ConfigNetWorkMsg) {
            ConfigNetWorkMsg msg = (ConfigNetWorkMsg) message;
            CommonProtos.Cm_Common_Config cm_common_config = (CommonProtos.Cm_Common_Config) msg.getMessage();
            CommonProtos.Sm_Common_Config.Builder br = CommonProtos.Sm_Common_Config.newBuilder();
            ConfigUtils.setAction(br, cm_common_config);
            getConfig(cm_common_config, msg.getConnection(), br);
        }
    }

    private void getConfig(CommonProtos.Cm_Common_Config cm_common_config, Connection connection, CommonProtos.Sm_Common_Config.Builder br) {
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Common_Config, br.getAction());
        br.setRequest(cm_common_config);
        String tableName = ConfigTableNameEnums.getTableNameByAction(cm_common_config.getAction());
        List<TableDataHeader> tableDataHeader = ConfigUtils.getTableDataHeader(tableName);
        if (cm_common_config.getArgsList().size() != MagicNumbers.DEFAULT_ZERO) {
            List<CommonProtos.Cm_Common_Args> argsList = cm_common_config.getArgsList();
            multiCondition(argsList, br, tableName, tableDataHeader);
        } else {
            allTable(br, tableName, tableDataHeader);
        }
        response.setResult(true);
        response.setSmCommonConfig(br.build());
        connection.send(new MessageSendHolder(response.build(), response.getSmMsgAction(), new ArrayList<>()));
    }


    private void multiCondition
            (List<CommonProtos.Cm_Common_Args> argsList, CommonProtos.Sm_Common_Config.Builder br, String
                    tableName, List<TableDataHeader> tableDataHeader) {
        List<TableDataRow> tableDataRow = ConfigUtils.getTableDataRow(tableName, argsList);
        for (TableDataRow dataRow : tableDataRow) {
            transBuilder(br, tableDataHeader, dataRow);
        }
    }

    private void allTable(CommonProtos.Sm_Common_Config.Builder br, String
            tableName, List<TableDataHeader> tableDataHeader) {
        List<TableDataRow> tableDataRow = ConfigUtils.getTableDataRow(tableName);
        for (TableDataRow dataRow : tableDataRow) {
            transBuilder(br, tableDataHeader, dataRow);
        }
    }

    private void transBuilder(CommonProtos.Sm_Common_Config.Builder
                                      br, List<TableDataHeader> tableDataHeader, TableDataRow tableDataRow) {
        CommonProtos.Sm_Common_Row.Builder brow = CommonProtos.Sm_Common_Row.newBuilder();
        brow.setRowId(tableDataRow.getRowIdx());
        for (int idx = 0; idx < tableDataRow.getCells().size(); ++idx) {
            CommonProtos.Common_KV.Builder cb = CommonProtos.Common_KV.newBuilder();
            cb.setName(tableDataHeader.get(idx).getName());
            cb.setValue(tableDataRow.getCells().get(idx).getCell());
            brow.addKv(cb.build());
        }
        br.addRow(brow.build());
    }

}

