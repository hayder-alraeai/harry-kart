package se.atg.service.harrykart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponseType {
    protected List<RankingType> ranking;

}
