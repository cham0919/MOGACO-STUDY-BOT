package com.flat.mogacko;

public class MogackoTable {

    public class MemberTable {
        public static final String TABLE_NAME = "MEMBER";
        public static final String PK = "key";
        public static final String NICKNAME = "nickname";
        public static final String SERVER = "server";
        public static final String NAME = "name";
        public static final String JOINDATE = "joinDate";
    }

    public class JoinRecordTable {
        public static final String TABLE_NAME = "JOIN_RECORD";
        public static final String PK = "key";
        public static final String JOIN_TIME = "joinTime";
        public static final String LEAVE_TIME = "leaveTime";
    }

    public class RankTable {
        public static final String TABLE_NAME = "RANK";
        public static final String PK = "key";
        public static final String TOTAL_JOIN_TIME = "totalJoinTime";
    }
}
