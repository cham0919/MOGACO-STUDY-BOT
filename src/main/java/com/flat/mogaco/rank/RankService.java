package com.flat.mogaco.rank;

import java.util.List;

public interface RankService {


    void save(Rank rank);

    void updateAll();

    List<Rank> fetchCurrentRank(String channel);

    String fetchCurrentRankMessage(String channel);

    void initRank();
}
