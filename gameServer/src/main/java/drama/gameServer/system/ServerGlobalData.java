package drama.gameServer.system;

import drama.gameServer.system.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class ServerGlobalData {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerGlobalData.class);
    private static Locale locale = null;

    public static void init() {
        initLocale();
        initRealmData();
    }

    private static void initRealmData() {

    }

    private static void initLocale() {
        String lang = AppConfig.getString(AppConfig.Key.DM_Common_Config_lang);
        String country = AppConfig.getString(AppConfig.Key.DM_Common_Config_country);
        locale = new Locale(lang, country);
        LOGGER.debug("当前语言环境:lang={} country={}", locale.getLanguage(), locale.getCountry());
    }

    public static Locale getLocale() {
        return locale;
    }
}
