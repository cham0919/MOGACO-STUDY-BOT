package com.flat.mogaco.rank;

import com.flat.mogaco.Join.JoinRecord;
import com.flat.mogaco.Join.JoinRecordRepository;
import com.flat.mogaco.common.util.TimeUtils;
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
        StringBuilder respMessage = null;
        List<RankDto> rankDtoList = new LinkedList<>();
        if (rankList.size() > 0 ) {
            respMessage = new StringBuilder("현재 랭킹입니다! \n ");
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

            for (int i = 0; i < rankDtoList.size(); i++) {
                RankDto dto = rankDtoList.get(i);
                respMessage.append((i+1)+". "+dto.getNickName()+ "님 ("+ getTimeFormat(dto.getTotalTime()) +")\n");
            }
        } else {
            respMessage = new StringBuilder("참여 인원이 없어요 :(");
        }

        return respMessage.toString();
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

    private String getTimeFormat(LocalTime localTime){
        return localTime.getHour() + "시간 " + localTime.getMinute() + "분 " + localTime.getSecond() + "초";
    }


    @Override
    public List<Rank> fetchCurrentRank(String channel) {
        return rankRepository.findAllByMember_Channel(channel);
    }
}
