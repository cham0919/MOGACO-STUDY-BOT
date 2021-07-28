package com.flat.mogaco;

public class MogackoTable {

    public class MemberTable {
        public static final String TABLE_NAME = "MEMBER";
        public static final String PK = "member_id";
        public static final String NICKNAME = "nickname";
        public static final String USERID = "userId";
        public static final String CHANNEL = "channel";
        public static final String JOINDATE = "joinDate";
        public static final String TODAYJOINTIME = "todayJoinTime";
    }

    public class JoinRecordTable {
        public static final String TABLE_NAME = "JOIN_RECORD";
        public static final String PK = "join_record_id";
        public static final String JOIN_TIME = "joinTime";
        public static final String LEAVE_TIME = "leaveTime";
    }

    public class RankTable {
        public static final String TABLE_NAME = "JOIN_RANK";
        public static final String PK = "rank_id";
        public static final String TOTAL_JOIN_TIME = "totalJoinTime";
    }
}
