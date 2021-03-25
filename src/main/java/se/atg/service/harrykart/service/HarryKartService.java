package se.atg.service.harrykart.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.atg.service.harrykart.model.HarryKartType;
import se.atg.service.harrykart.model.ResultResponseType;
import se.atg.service.harrykart.utils.Utils;

@AllArgsConstructor
@Service
public class HarryKartService {
    private Utils utils;

    public ResultResponseType playHarryKart(HarryKartType harryKartType) {
        return utils.getRanking(utils.createRacing(harryKartType));
    }
}
