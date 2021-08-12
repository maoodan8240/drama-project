package drama.gameServer.features.actor.playerIO.utils;

import dm.relationship.base.MagicWords_Ftp;
import dm.relationship.utils.FtpUtils;
import drama.gameServer.system.config.AppConfig;

import java.io.ByteArrayInputStream;

public class PlayerIConUploadUtils {
    public static String uploadPlayerIcon(String fileName, byte[] bytes) {
        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bytes);
        FtpUtils.uploadFile(
                AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_host),//
                AppConfig.getInt(AppConfig.Key.DM_Common_Config_ftp_port),//
                AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_userName),//
                AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_password),//
                AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_basePath),//
                MagicWords_Ftp.PlayerIConPath,//
                fileName + MagicWords_Ftp.IConSuffix,//
                fileInputStream
        );
        return MagicWords_Ftp.HttpPrefix + AppConfig.getString(AppConfig.Key.DM_Common_Config_ftp_host) + MagicWords_Ftp.PlayerIConPath + fileName + MagicWords_Ftp.IConSuffix;
    }
}
