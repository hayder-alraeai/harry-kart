package se.atg.service.harrykart.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Ranking;
import se.atg.service.harrykart.model.ResultResponse;
import se.atg.service.harrykart.type.*;
import se.atg.service.harrykart.model.HorseInRace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HarryKartServiceImp implements HarryKartService {
    @Override
    public ResultResponse play(HarryKart harryKart){
        return getRanking(startRacing(harryKart, prepareHorsesToTheRacing(harryKart.getStartList().getParticipant())));
    }

    @Override
    public List<HorseInRace> prepareHorsesToTheRacing(List<ParticipantType> participantType){
        try {
            return participantType.stream()
                    .map(participant -> new HorseInRace(participant.getName(),participant.getBaseSpeed().intValue(), participant.getLane().intValue()))
                    .collect(Collectors.toList());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @Override
    public List<HorseInRace> startRacing(HarryKart harryKart,List<HorseInRace> horsesInRace) {
        int loop = 0;
        do {
            //calc powerUps for each horse
            if (harryKart.getPowerUps().getLoop().size() > loop){ //check if loops with power ups is finished
                harryKart.getPowerUps().getLoop().get(loop).getLane().forEach(lane -> {
                    horsesInRace.forEach(horseInRace -> {
                        if (horseInRace.getLaneNumber() == lane.getNumber().intValue()){
                            //to avoid dividing by zero
                            if (horseInRace.getSpeed() == 0){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> cannot be zero");
                            }
                            //start looping in each lane and calculate duration time for each horse
                            //add the calculated time to an array for each horse
                            horseInRace.addTimeToLoopTime(1000/horseInRace.getSpeed());
                            //calculate the powerUps/down with baseSpeed at the end of the track
                            try { //in case baseSpeed element is missed
                                horseInRace.setSpeed(
                                        horseInRace.getSpeed() + (lane.getValue().intValue())
                                );
                            }catch (Exception ex){
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
                            }
                        }
                    });
                });
            }else { //in case there are no powerUps
                    //start looping in each lane and calculate duration time for each horse
                    //add the calculated time to an array for each horse
                horsesInRace.forEach(horseInRace -> {
                    //to avoid dividing by zero
                    if (horseInRace.getSpeed() == 0){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> cannot be zero");
                    }
                    horseInRace.addTimeToLoopTime(1000/horseInRace.getSpeed());
                });
            }
            loop ++;

        }while (loop < harryKart.getNumberOfLoops().intValue());
        //calc the final time at end of racing for each participant
        horsesInRace.forEach(horseInRace -> horseInRace.calcTime());
        //sorting horses by their final time at end of racing
        Collections.sort(horsesInRace);
        return horsesInRace;
    }

    @Override
    public ResultResponse getRanking(List<HorseInRace> horsesInRace) {
        //extract top three horses and return the result
        List<Ranking> getWinners = new ArrayList<>();
        int index = 0;
        while (index < 3){
            getWinners.add(new Ranking(index + 1, horsesInRace.get(index).getName()));
            index ++;
        }
        return new ResultResponse(getWinners);
    }
}
