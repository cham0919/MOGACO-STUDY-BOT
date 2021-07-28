package com.flat.mogaco.rank;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.Join.JoinRecordRepository;
import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
        List<Rank> rankList  = rankRepository.findAll();
        rankList.forEach(rank -> {
            LocalTime localTime = rank.getMember().getTodayJoinTime();
            rank.plusTime(localTime);
        });
    }

    @Override
    @Transactional
    public String fetchCurrentRankMessage(String channel) {
        List<Rank> rankList = fetchCurrentRank(channel);
        List<RankDto> rankDtoList = new LinkedList<>();
        if (rankList.size() > 0 ) {
            for (int i = 1; i <= rankList.size(); i++) {
                Rank rank = rankList.get(i-1);
                LocalTime totalTime = plusTotalTimeAndFCurrentTime(rank);
                RankDto dto = new RankDto().setNickName(rank.getMember().getNickName())
                        .setTotalTime(totalTime);
                rankDtoList.add(dto);
            }

            rankDtoList = rankDtoList.stream()
                    .sorted(Comparator.comparing(RankDto::getTotalTime).reversed())
            .collect(Collectors.toList());

            return Message.CURRENT_RANK.getMessage(rankDtoList);
        } else {
            return Message.NO_EXIST_MEMBER.getMessage();
        }
    }

    @Override
    public void initRank() {
        List<Rank> rankList = rankRepository.findAll();
        rankList.forEach(rank -> {
            rank.setTotaljointime(LocalTime.of(0,0,0));
        });
    }

    private LocalTime plusTotalTimeAndFCurrentTime(Rank rank) {
        LocalTime todayTime = rank.getMember().getTodayJoinTime();
        LocalTime localTime = rank.getTotaljointime();
        LocalTime totalTime = TimeUtils.plusLocalTime(localTime, todayTime);

        JoinRecord joinRecord = joinRecordRepository.findFirst1ByMemberOrderByKeyDesc(rank.getMember());
        if (joinRecord != null && joinRecord.getLeaveTime() == null) {
            LocalTime currentTime = joinRecord.getJoinTime().toLocalTime();
            currentTime = TimeUtils.minusLocalTime(LocalTime.now(), currentTime);
            totalTime = TimeUtils.plusLocalTime(totalTime, currentTime);
        }

        return totalTime;
    }



    @Override
    public List<Rank> fetchCurrentRank(String channel) {
        return rankRepository.findAllByMember_Channel(channel);
    }
}
