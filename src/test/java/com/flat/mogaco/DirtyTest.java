package com.flat.mogaco;

import com.flat.mogaco.rank.Rank;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DirtyTest {

    @Test
    public void test() throws InterruptedException {
        List<Rank> rankList = new ArrayList<>();
        LocalTime now = LocalTime.now();
        rankList.add(new Rank().setTotaljointime(now.plusHours(1)).setKey(3L));
        rankList.add(new Rank().setTotaljointime(now.plusMinutes(1)).setKey(2L));
        rankList.add(new Rank().setTotaljointime(now).setKey(1L));

        rankList.stream()
                .sorted(Comparator.comparing(Rank::getTotaljointime));

    }
}
