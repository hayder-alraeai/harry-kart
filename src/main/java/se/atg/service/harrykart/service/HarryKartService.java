package se.atg.service.harrykart.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.HorseInRace;
import se.atg.service.harrykart.model.ResultResponse;
import se.atg.service.harrykart.type.*;
import java.util.List;

@Service
public interface HarryKartService {
    ResultResponse play(HarryKart harryKart);
    List<HorseInRace> prepareHorsesToTheRacing(List<ParticipantType> participantType);
    List<HorseInRace> startRacing(HarryKart harryKart,List<HorseInRace> horsesInRace);
    ResultResponse getRanking(List<HorseInRace> horsesInRace);
}
