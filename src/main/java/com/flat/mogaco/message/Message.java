package com.flat.mogaco.message;

import com.flat.mogaco.common.util.TimeUtils;
import com.flat.mogaco.rank.RankDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@Getter
public enum Message {

    // 설명
    INFO("안녕하세요 모각코봇이예요 :)\n" +
            "명령어는 다음과 같아요\n" +
            "\n" +
            "1. !참여 - 모각코랭크에 참여해요\n" +
            "2. !참여인원 - 현재 참여인원을 볼 수 있어요\n" +
            "3. !조회 - 금주 본인의 참여시간을 알 수 있어요\n" +
            "4. !랭크 - 금주 랭크를 조회해요\n" +
            "5. !공지 - 이 방의 공지를 조회해요\n" +
            "6. !공지 삭제 - 공지를 삭제해요\n" +
            "\n" +
            "참여를 한 다음 음성 채널에 들어가게 되면 자동으로 시간이 카운트됩니다!\n" +
            "랭크는 매주 월요일마다 초기화됩니다 :)"),

    // 에러
    ERROR("웁스! 에러 발생 ㅜㅜ"),

    // 공지
    SUCCESS_INSERT_NOTICE("공지가 등록되었습니다."),
    SUCCESS_DELETE_NOTICE("공지가 삭제되었습니다."),
    NOT_FIND_NOTICE("등록된 공지가 없습니다 :("),

    // 참여
    SUCCSEE_JOIN("{}님이 스터디에 참여하셨습니다!"),
    ALREADY_JOIN("{}님은 이미 참여 중입니다!"),
    NO_EXIST_MEMBER("현재 참여 인원이 없습니다"),
    CURRENT_MEMBER("현재 참여 인원입니다"){
        @Override
        public String getMessage(List nameList){
            String respMessage = message;
            respMessage += "\n";
            for (int i = 1; i <= nameList.size(); i++) {
                respMessage += i + ". " + nameList.get(i-1) + "\n";
            }
            return respMessage;
        }
    },
    NOT_JOIN_MEMBER("참여 중이 아닙니다!"),

    // 조회
    LOOKUP_JOIN_TIME("{}님은 오늘 {} 공부하셨습니다"){
        @Override
        public String getMessage(Object... params){
            String respMessage = message;
            respMessage = respMessage.replaceFirst("\\{\\}", params[0].toString());
            respMessage = respMessage.replaceFirst("\\{\\}", TimeUtils.localTimeToString((LocalTime)params[1]));
            return respMessage;
        }
    },

    //랭크
    CURRENT_RANK("현재 랭킹입니다!"){
        @Override
        public String getMessage(List rankDtoList){
            String respMessage = message;
            respMessage += "\n";
            for (int i = 0; i < rankDtoList.size(); i++) {
                RankDto dto = (RankDto)rankDtoList.get(i);
                respMessage += (i+1)+". "+dto.getNickName()+ "님 ("+ TimeUtils.durationToString(dto.getTotalTime()) +")\n";
            }
            return respMessage;
        }
    };




    protected String message;


    public String getMessage(List list){
        String respMessage = message;
        for (Object param : list) {
            respMessage = respMessage.replaceFirst("\\{\\}", param.toString());
        }
        return respMessage;
    }

    public String getMessage(Object... params){
        String respMessage = message;
        for (Object param : params) {
            respMessage = respMessage.replaceFirst("\\{\\}", param.toString());
        }
        return respMessage;
    }
}
