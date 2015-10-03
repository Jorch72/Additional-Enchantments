package net.darkhax.aem.util;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    public static final String MOD_ID = "AEM";
    public static final String MOD_NAME = "Additional Enchantments";
    public static final String VERSION_NUMBER = "1.0.0";
    public static final String CLIENT_PROXY_CLASS = "net.darkhax.aem.client.ProxyClient";
    public static final String SERVER_PROXY_CLASS = "net.darkhax.aem.common.ProxyCommon";
    public static final String FACTORY = "net.darkhax.aem.client.gui.GuiFactoryAdditionalEnchantments";
    
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}
