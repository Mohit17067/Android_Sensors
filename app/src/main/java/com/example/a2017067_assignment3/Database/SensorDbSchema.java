package com.example.a2017067_assignment3.Database;

public class SensorDbSchema {
    public static final class AccelerometerTable{
        public static final String Name = "accelerometer";

        public static final class AccCols {
            public static final String TIME_STAMP = "time_stamp";
            public static final String XVALUE = "xvalue";
            public static final String YVALUE = "yvalue";
            public static final String ZVALUE = "zvalue";
        }
    }

    public static final class GPSTable{
        public static final String Name = "gps";

        public static final class GPSCols {
            public static final String TIME_STAMP = "time_stamp";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }

    }

    public static final class WifiTable{
        public static final String Name = "wifi";

        public static final class WifiCols {
            public static final String TIME_STAMP = "time_stamp";
            public static final String AP_NAMES = "AP_names";
            public static final String AP_STRENGTHS = "AP_strengths";
        }
    }


}
