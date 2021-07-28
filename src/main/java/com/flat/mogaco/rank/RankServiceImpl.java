package com.flat.mogaco.rank;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.Join.JoinRecordRepository;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;
    private final JoinRecordRepository joinRecordRepository;


    @Override
    public void save(Rank rank) {
        rankRepository.save(rank);
    }

    @Override
    public void updateAll() {
//        List<Rank> rankList  = rankRepository.findAll();
//        rankList.forEach(rank -> {
//            LocalTime localTime = rank.getMember().getTodayJoinTime();
//            rank.plusTime(localTime);
//        });
    }

    @Override
    @Transactional
    public String fetchCurrentRankMessage(String channel) {
        List<Rank> rankList = fetchCurrentRank(channel);
        List<RankDto> rankDtoList = new LinkedList<>();

        if (rankList.size() > 0 ) {
            for (int i = 1; i <= rankList.size(); i++) {
                Rank rank = rankList.get(i-1);
                Duration totalTime = plusTotalTimeAndCurrentTime(rank);
                RankDto dto = new RankDto().setNickName(rank.getMember().getNickName())
                        .setTotalTime(totalTime);
                rankDtoList.add(dto);
            }
            rankDtoList.sort(Collections.reverseOrder());

            return Message.CURRENT_RANK.getMessage(rankDtoList);
        } else {
            return Message.NO_EXIST_MEMBER.getMessage();
        }
    }

    @Override
    public void initRank() {
        List<Rank> rankList = rankRepository.findAll();
        rankList.forEach(rank -> {
            rank.setTotaljointime(Duration.ZERO);
        });
    }

    private Duration plusTotalTimeAndCurrentTime(Rank rank) {
        Duration totalTime = rank.getTotaljointime();
        LocalTime todayTime = rank.getMember().getTodayJoinTime();
        totalTime = TimeUtils.plusTime(totalTime, todayTime);

        JoinRecord joinRecord = joinRecordRepository.findFirst1ByMemberOrderByKeyDesc(rank.getMember());

        if (joinRecord != null && joinRecord.getLeaveTime() == null) {
            LocalTime currentTime = joinRecord.getJoinTime().toLocalTime();
            currentTime = TimeUtils.minusLocalTime(LocalTime.now(), currentTime);
            totalTime = TimeUtils.plusTime(totalTime, currentTime);
        }

        return totalTime;
    }



    @Override
    public List<Rank> fetchCurrentRank(String channel) {
        return rankRepository.findAllByMember_Channel(channel);
    }
}
