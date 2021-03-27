package se.atg.service.harrykart.service;

import org.springframework.stereotype.Service;
import se.atg.service.harrykart.model.*;
import se.atg.service.harrykart.model.HorseInRace;
import java.util.List;

@Service
public interface HarryKartService {
    ResultResponseType play(HarryKartType harryKartType);
    List<HorseInRace> prepareHorsesToTheRacing(List<ParticipantType> participantType);
    List<HorseInRace> startRacing(HarryKartType harryKartType,List<HorseInRace> horsesInRace);
    ResultResponseType getRanking(List<HorseInRace> horsesInRace);
}
