package ru.x5.migration.service.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.x5.migration.dao.inventory.ZloInvTaskHDao;
import ru.x5.migration.dao.inventory.ZloInvTaskPosDao;
import ru.x5.migration.dao.inventory.ZloInvTaskZoneDao;
import ru.x5.migration.domain.inventory.ZloInvTaskH;
import ru.x5.migration.domain.inventory.ZloInvTaskPos;
import ru.x5.migration.domain.inventory.ZloInvTaskZone;
import ru.x5.migration.dto.context.ParseContext;
import ru.x5.migration.dto.xml.inventory.IDOC;
import ru.x5.migration.dto.xml.inventory.mapper.InventoryMapper;
import ru.x5.migration.reader.XmlFileReader;

import java.math.BigInteger;
import java.util.List;

@Service
public class InventoryService {

    private final XmlFileReader reader;
    private final ZloInvTaskHDao taskHDao;
    private final ZloInvTaskPosDao taskPosDao;
    private final ZloInvTaskZoneDao taskZoneDao;
    private final InventoryMapper inventoryMapper;

    public InventoryService(@Qualifier("inventoryXmlReader") XmlFileReader reader,
                            ZloInvTaskHDao taskHDao,
                            ZloInvTaskPosDao taskPosDao,
                            ZloInvTaskZoneDao taskZoneDao,
                            InventoryMapper inventoryMapper
    ) {
        this.reader = reader;
        this.taskHDao = taskHDao;
        this.taskPosDao = taskPosDao;
        this.taskZoneDao = taskZoneDao;
        this.inventoryMapper = inventoryMapper;
    }

    public void parseXml(String fileName) {
        ParseContext parseContext = reader.read(fileName);
        var result = parseContext.getResult();
        var idoc = (IDOC) result.getParsedTags().pollFirst();
        saveIdoc(idoc);
    }

    @Getter
    @AllArgsConstructor
    static class InventoryEntities {
        ZloInvTaskH taskH;
        List<ZloInvTaskPos> taskPos;
        List<ZloInvTaskZone> taskZone;
    }

    public void saveIdocs(List<IDOC> idocs) {
        var dbEntities = idocs.stream().map(idoc -> {
            var taskH = inventoryMapper.toZloInvTaskH(idoc.EDI_DC40, idoc.E1WVINH);
            var taskPoss = inventoryMapper.toZloInvTaskPos(idoc.EDI_DC40, idoc.E1WVINH);
            var taskZones = inventoryMapper.toZloInvTaskZone(idoc.EDI_DC40, idoc.E1WVINH);
            return new InventoryEntities(taskH, taskPoss, taskZones);
        }).toList();

        var taskHs = dbEntities.stream().map(InventoryEntities::getTaskH).toList();
        taskHDao.batchUpdate(taskHs, taskHs.size());

        dbEntities.forEach(comb -> {
            BigInteger id = comb.taskH.getId();
            comb.taskPos.forEach(it -> it.setParentid(id));
            comb.taskZone.forEach(it -> it.setParentid(id));
        });

        var taskPoss = dbEntities.stream().flatMap(comb -> comb.getTaskPos().stream()).toList();
        var taskZones = dbEntities.stream().flatMap(comb -> comb.getTaskZone().stream()).toList();

        taskPosDao.batchUpdate(taskPoss, taskPoss.size());
        taskZoneDao.batchUpdate(taskZones, taskZones.size());
    }

    public void saveIdoc(IDOC idoc) {
        var edidc40 = idoc.EDI_DC40;
        var e1WVINH = idoc.E1WVINH;

        var taskH = inventoryMapper.toZloInvTaskH(edidc40, e1WVINH);
        var taskPoss = inventoryMapper.toZloInvTaskPos(edidc40, e1WVINH);
        var taskZones = inventoryMapper.toZloInvTaskZone(edidc40, e1WVINH);

        taskHDao.batchUpdate(List.of(taskH), 1);

        taskPoss.forEach(it -> it.setParentid(taskH.getId()));
        taskZones.forEach(it -> it.setParentid(taskH.getId()));

        taskPosDao.batchUpdate(taskPoss, taskPoss.size());
        taskZoneDao.batchUpdate(taskZones, taskZones.size());
    }
}
