package com.example.wppl.config;

import com.example.wppl.creator.inventory.InventoryCreator;
import com.example.wppl.reader.handler.XmlStaxEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Bean
    public XmlStaxEventHandler inventoryXmlStaxEventHandler(InventoryCreator inventoryCreator){
        return new XmlStaxEventHandler(inventoryCreator);
    }
}
