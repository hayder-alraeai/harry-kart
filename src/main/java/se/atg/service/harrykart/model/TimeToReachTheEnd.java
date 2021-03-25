package se.atg.service.harrykart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeToReachTheEnd implements Comparable{
    private String horseName;
    private int time;
    private List<Integer> loopTime = new ArrayList<>();

    public TimeToReachTheEnd(String horseName, int time) {
        this.horseName = horseName;
        this.time = time;
    }

    public void addTimeToLoopTime(int time){
        loopTime.add(time);
    }
    public void calcTime(){
        setTime((int) loopTime.stream().reduce(0,Integer::sum) / loopTime.size());
    }
    @Override
    public int compareTo(Object o) {
        int comparedTime = ((TimeToReachTheEnd)o).getTime();
        return this.getTime() - comparedTime;
    }
}
