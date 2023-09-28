package ru.x5.migration.config;

import ru.x5.migration.creator.inventory.InventoryCreator;
import ru.x5.migration.reader.handler.XmlStaxEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Bean
    public XmlStaxEventHandler inventoryXmlStaxEventHandler(InventoryCreator inventoryCreator){
        return new XmlStaxEventHandler(inventoryCreator);
    }
}
