package se.atg.service.harrykart.utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import se.atg.service.harrykart.model.*;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Utils {
    public List<TimeToReachTheEnd> createRacing(HarryKartType harryKartType) {
        BigInteger numberOfLoops = harryKartType.getNumberOfLoops(); //Never been needed!!!
        List<ParticipantType> participants = harryKartType.getStartList().getParticipant();
        List<TimeToReachTheEnd> horsesInRace = new ArrayList<>();
        participants.forEach(p -> horsesInRace.add(new TimeToReachTheEnd(p.getName(), 0)));
            participants.forEach(participant -> {
                harryKartType.getPowerUps().getLoop().stream().forEach(loop -> {
                    loop.getLane().forEach(lane -> {
                        if (participant.getLane().intValue() == lane.getNumber().intValue()){
                            calcBaseSpeed(participant, lane);
                            horsesInRace.forEach(horseInRace -> calcTime(horseInRace, participant));
                        }
                    });
                });
            });
        horsesInRace.forEach(horseInRace -> horseInRace.calcTime());
        Collections.sort(horsesInRace);
        horsesInRace.forEach(h -> System.out.println(h.getHorseName() + " time: " + h.getTime()));
        return horsesInRace;
    }
    public ResultResponseType getRanking(List<TimeToReachTheEnd> timeToReachTheEnds){
        List<RankingType> rankingList = new ArrayList<>();
        int i = 0;
        while (i < 3){
            rankingList.add(new RankingType(i + 1, timeToReachTheEnds.get(i).getHorseName()));
            i ++;
        }
        return new ResultResponseType(rankingList);
    }
    private void calcTime(TimeToReachTheEnd hors, ParticipantType participant){
        if (hors.getHorseName().equals(participant.getName())){
            if (participant.getBaseSpeed().intValue() == 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> cannot be zero");
            }

            hors.addTimeToLoopTime( 1000 / participant.getBaseSpeed().intValue());
        }
    }
    private void calcBaseSpeed(ParticipantType participant, LaneType lane){
        try{
            if (participant.getLane().intValue() == lane.getNumber().intValue()){
                participant.setBaseSpeed(
                        BigInteger.valueOf(participant.getBaseSpeed().intValue() + (lane.getValue().intValue()))
                );
            }
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBS!! <baseSpeed> element is missed somewhere!");
        }
    }
    public String readXmlFileFromResources(String fileName) throws IOException {
        String xmlString = "";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null){
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                xmlString += "\n" + line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlString;
    }
}
