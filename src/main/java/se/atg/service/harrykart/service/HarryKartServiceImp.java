package se.atg.service.harrykart.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.atg.service.harrykart.model.*;
import se.atg.service.harrykart.model.HorseInRace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HarryKartServiceImp implements HarryKartService {
    @Override
    public ResultResponseType play(HarryKartType harryKartType){
        return getRanking(startRacing(harryKartType, prepareHorsesToTheRacing(harryKartType.getStartList().getParticipant())));
    }

    @Override
    public List<HorseInRace> prepareHorsesToTheRacing(List<ParticipantType> participantType) {
        return participantType.stream()
                .map(participant -> new HorseInRace(participant.getName(),participant.getBaseSpeed().intValue(), participant.getLane().intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<HorseInRace> startRacing(HarryKartType harryKartType,List<HorseInRace> horsesInRace) {
        int loop = 0;
        do {
            //calc powerUps for each horse
            if (harryKartType.getPowerUps().getLoop().size() > loop){ //check if loops with power ups is finished
                harryKartType.getPowerUps().getLoop().get(loop).getLane().forEach(lane -> {
                    horsesInRace.forEach(horseInRace -> {
                        if (horseInRace.getLaneNumber() == lane.getNumber().intValue()){
                            //to avoid dividing by zero
                            if (horseInRace.getSpeed() == 0){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> cannot be zero");
                            }
                            //start racing in each lane
                            horseInRace.addTimeToLoopTime(1000/horseInRace.getSpeed());
                            //sum the powerUps with baseSpeed
                            try { //in case baseSpeed element is missed
                                horseInRace.setSpeed(
                                        horseInRace.getSpeed() + (lane.getValue().intValue())
                                );
                            }catch (Exception ex){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> element is missed!");
                            }
                        }
                    });
                });
            }else { //in case there are no powerUps
                horsesInRace.forEach(horseInRace -> {
                    //to avoid dividing by zero
                    if (horseInRace.getSpeed() == 0){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> cannot be zero");
                    }
                    horseInRace.addTimeToLoopTime(1000/horseInRace.getSpeed());
                });
            }
            loop ++;

        }while (loop < harryKartType.getNumberOfLoops().intValue());
        //calc the final time at end of racing
        horsesInRace.forEach(horseInRace -> horseInRace.calcTime());
        //sorting horses by their final time at end of racing
        Collections.sort(horsesInRace);
        return horsesInRace;
    }

    @Override
    public ResultResponseType getRanking(List<HorseInRace> horsesInRace) {
        //extract first three horses and return the result
        List<RankingType> getWinners = new ArrayList<>();
        int index = 0;
        while (index < 3){
            getWinners.add(new RankingType(index + 1, horsesInRace.get(index).getName()));
            index ++;
        }
        return new ResultResponseType(getWinners);
    }
}
