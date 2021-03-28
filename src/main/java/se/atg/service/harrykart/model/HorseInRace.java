package se.atg.service.harrykart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HorseInRace implements Comparable<HorseInRace>{
    private String name;
    private int speed;
    private int laneNumber;
    private int finalTime;
    private List<Integer> durationTime = new ArrayList<>();

    public HorseInRace(String name, int speed, int laneNumber) {
        this.name = name;
        this.speed = speed;
        this.laneNumber = laneNumber;
    }

    public void addTimeToLoopTime(int time){
        durationTime.add(time);
    }
    public void calcTime(){
        setFinalTime(durationTime.stream().reduce(0,Integer::sum) / durationTime.size());
    }
    @Override
    public int compareTo(HorseInRace o) {
        return this.getFinalTime() - o.getFinalTime();
    }

}
