package tech.myevents.hq;

public final class DBContract {

    public DBContract() {
    }

    static abstract class User{

        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_LOCATION_CODE = "location_code";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_APP_VERSION = "app_version";
    }

    static abstract class SignUps {
        static final String TABLE_NAME = "sign_ups";
        static final String COLUMN_ID = "_id";
        static final String PHONE_NUMBER = "phone_number";
        static final String CONF_CODE = "conf_code";
    }

    static abstract class MaxIds {

        static final String TABLE_NAME = "max_ids";
        static final String COLUMN_ID = "_id";
        static final String LOCATION_CODE = "location_code";
        static final String EVENT_INTEREST_CODE = "event_interest_code";
        static final String AD_INTEREST_CODE = "ad_interest_code";
        static final String MAX_EVENT_ID = "max_event_id";
        static final String MAX_AD_ID = "max_ad_id";

    }

    static abstract class NatMaxId {

        static final String TABLE_NAME = "nat_max_id";
        static final String COLUMN_ID = "_id";
        static final String LOCATION_CODE = "location_code";
        static final String EVENT_INTEREST_CODE = "event_interest_code";
        static final String AD_INTEREST_CODE = "ad_interest_code";
        static final String MAX_EVENT_ID = "max_event_id";
        static final String MAX_AD_ID = "max_ad_id";

    }

    static abstract class CityMaxId {
        static final String TABLE_NAME = "city_max_id";
        static final String COLUMN_ID = "_id";
        static final String LOCATION_CODE = "location_code";
        static final String EVENT_INTEREST_CODE = "event_interest_code";
        static final String AD_INTEREST_CODE = "ad_interest_code";
        static final String MAX_EVENT_ID = "max_event_id";
        static final String MAX_AD_ID = "max_ad_id";
    }

    static abstract class LocMaxId {
        static final String TABLE_NAME = "loc_max_id";
        static final String COLUMN_ID = "_id";
        static final String LOCATION_CODE = "location_code";
        static final String EVENT_INTEREST_CODE = "event_interest_code";
        static final String AD_INTEREST_CODE = "ad_interest_code";
        static final String MAX_EVENT_ID = "max_event_id";
        static final String MAX_AD_ID = "max_ad_id";
    }

    public static abstract class Event{

        public static final String TABLE_NAME = "event";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_EVENT_ID = "event_id";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
        public static final String COLUMN_LOCATION_CODE = "location_code";
        public static final String COLUMN_EVENT_NAME = "event_name";
        public static final String COLUMN_EVENT_VENUE = "venue";
        public static final String COLUMN_EVENT_DETAILS = "event_details";
        public static final String COLUMN_MIN_PRICE = "min_price";
        public static final String COLUMN_MAX_PRICE = "max_price";
        public static final String COLUMN_START_TIMESTAMP = "start_timestamp";
        public static final String COLUMN_END_TIMESTAMP = "end_timestamp";
        public static final String COLUMN_BITMAP_NAME = "bitmap_name";
        public static final String COLUMN_POSTER_BITMAP = "poster_bitmap";
        public static final String COLUMN_EVENT_STATUS = "event_status";
        public static final String COLUMN_EVENT_UPDATE = "event_update";
        public static final String COLUMN_VIEWS = "event_views";
        public static final String COLUMN_LIKES = "event_likes";
        public static final String COLUMN_VIEWED = "viewed";
        public static final String COLUMN_LIKED = "liked";
        static final String COLUMN_LIKE_STATUS = "like_status";
        static final String COLUMN_VIEW_STATUS = "view_status";
        static final String CONF_CODE = "conf_code";
        static final String BROADCAST_CHARGE = "broadcast_charge";
        static final String COLUMN_WHEN = "received_when";
        public static final String COLUMN_VIDEO = "video";

    }

    public static abstract class Ad{
        public static final String TABLE_NAME = "ad";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_AD_ID = "ad_id";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
        public static final String COLUMN_BRAND_NAME = "brand_name";
        static final String COLUMN_TITLE = "ad_title";
        public static final String COLUMN_DESC_1 = "desc_one";
        static final String COLUMN_DESC_2 = "desc_two";
        static final String COLUMN_DESC_3 = "desc_three";
        static final String COLUMN_DESC_4 = "desc_four";
        static final String COLUMN_BITMAP_NAME = "bitmap_name";
        static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_AD_UPDATE = "ad_update";
        public static final String COLUMN_VIEWS = "ad_views";
        public static final String COLUMN_LIKES = "ad_likes";
        public static final String COLUMN_VIEWED = "viewed";
        public static final String COLUMN_LIKED = "liked";
        static final String COLUMN_LIKE_STATUS = "like_status";
        static final String COLUMN_VIEW_STATUS = "view_status";
        static final String CONF_CODE = "conf_code";
        static final String BROADCAST_CHARGE = "broadcast_charge";
        static final String COLUMN_WHEN = "received_when";
        static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_AD_STATUS = "ad_status";
    }

    public static abstract class EventStats{
        public static final String TABLE_NAME = "event_stats";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_EVENT_ID = "event_id";
    }

    public static abstract class AdStats{
        public static final String TABLE_NAME = "ad_stats";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_AD_ID = "ad_id";
    }

    public static abstract class UserPref{
        public static final String TABLE_NAME = "user_pref";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_LOCATION_CODE = "location_code";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
    }

    public static abstract class PreEvent{
        public static final String TABLE_NAME = "pre_event";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
        public static final String COLUMN_LOCATION_CODE = "location_code";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VENUE = "venue";
        public static final String COLUMN_DETAILS = "details";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_START_TIMESTAMP = "start_timestamp";
        public static final String COLUMN_END_TIMESTAMP = "end_timestamp";
        public static final String COLUMN_POSTER_NAME = "poster_name";
        public static final String COLUMN_POSTER_BITMAP = "poster_bitmap";
        public static final String COLUMN_BROADCAST_CODE = "broadcast_code";
        public static final String COLUMN_STATUS = "status";
    }

    public static abstract class Interest{

        public static final String TABLE_NAME = "interest";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_INTEREST_CODE = "interest_code";
        public static final String COLUMN_INTEREST_NAME = "interest_name";
        public static final String COLUMN_BROADCAST_RANGE = "broadcast_range";

        public static final String VALUE_INTEREST_CODE_AMUSEMENT_PARK = "1a";
        public static final String VALUE_INTEREST_NAME_AMUSEMENT_PARK = "Amusement Park";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_AMUSEMENT_PARK = "national";

        public static final String VALUE_INTEREST_CODE_BUSINESS = "1b";
        public static final String VALUE_INTEREST_NAME_BUSINESS = "Business";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_BUSINESS = "national";

        public static final String VALUE_INTEREST_CODE_CLUBBING = "1c";
        public static final String VALUE_INTEREST_NAME_CLUBBING = "Clubbing";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_CLUBBING = "national";

        public static final String VALUE_INTEREST_CODE_CONCERT = "1d";
        public static final String VALUE_INTEREST_NAME_CONCERT = "Concert";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_CONCERT = "national";

        public static final String VALUE_INTEREST_CODE_EXHIBITION = "1e";
        public static final String VALUE_INTEREST_NAME_EXHIBITION = "Exhibition";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_EXHIBITION = "city";

        public static final String VALUE_INTEREST_CODE_FESTIVAL = "1f";
        public static final String VALUE_INTEREST_NAME_FESTIVAL = "Festival";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_FESTIVAL = "city";

        public static final String VALUE_INTEREST_CODE_MUSICAL_PERFORMANCE = "1g";
        public static final String VALUE_INTEREST_NAME_MUSICAL_PERFORMANCE = "Musical Performance";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_MUSICAL_PERFORMANCE = "national";

        public static final String VALUE_INTEREST_CODE_MUSIC = "1h";
        public static final String VALUE_INTEREST_NAME_MUSIC = "Music";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_MUSIC = "national";

        public static final String VALUE_INTEREST_CODE_THEATRE = "1i";
        public static final String VALUE_INTEREST_NAME_THEATRE = "Theatre";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_THEATRE = "national";

        public static final String VALUE_INTEREST_CODE_COMEDY = "1j";
        public static final String VALUE_INTEREST_NAME_COMEDY = "Comedy";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_COMEDY = "national";

        public static final String VALUE_INTEREST_CODE_RETREAT = "1k";
        public static final String VALUE_INTEREST_NAME_RETREAT = "Retreat";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_RETREAT = "national";

        public static final String VALUE_INTEREST_CODE_FASHION = "1l";
        public static final String VALUE_INTEREST_NAME_FASHION = "Fashion";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_FASHION = "national";

        public static final String VALUE_INTEREST_CODE_RECREATIONAL = "1m";
        public static final String VALUE_INTEREST_NAME_RECREATIONAL = "Recreational";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_RECREATIONAL = "national";

        public static final String VALUE_INTEREST_CODE_PARTY = "1n";
        public static final String VALUE_INTEREST_NAME_PARTY = "Party";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_PARTY = "national";

        public static final String VALUE_INTEREST_CODE_OUTDOOR = "1o";
        public static final String VALUE_INTEREST_NAME_OUTDOOR = "Outdoor";
        public static final String VALUE_INTEREST_BROADCAST_RANGE_OUTDOOR = "national";
    }

    public static abstract class Continent{

        public static final String TABLE_NAME = "continent";
        public static final String COLUMN_CONTINENT_ID = "_id";
        public static final String COLUMN_CONTINENT_NAME = "continent_name";
        public static final String COLUMN_CONTINENT_BROADCAST_RANGE = "broadcast_range";

        public static final String VALUE_CONTINENT_NAME_AFRICA = "Africa";
        public static final String VALUE_CONTINENT_BROADCAST_RANGE_AFRICA = "international";
    }

    public static abstract class Country{
        public static final String TABLE_NAME = "country";
        public static final String COLUMN_COUNTRY_ID = "_id";
        public static final String COLUMN_CONTINENT_ID = "continent_id";
        public static final String COLUMN_COUNTRY_NAME = "country_name";
        public static final String COLUMN_COUNTRY_BROADCAST_RANGE = "broadcast_range";

        public static final String VALUE_COUNTRY_NAME_ZIMBABWE = "Zimbabwe";
        public static final String VALUE_CONTINENT_ID_ZIMBABWE = "1";
        public static final String VALUE_BROADCAST_RANGE_ZIMBABWE = "continent";
    }

    public static abstract class Province{
        public static final String TABLE_NAME = "province";
        public static final String COLUMN_PROVINCE_ID = "_id";
        public static final String COLUMN_COUNTRY_ID = "country_id";
        public static final String COLUMN_PROVINCE_NAME = "province_name";

        public static final String VALUE_COUNTRY_ID_BULAWAYO = "1";
        public static final String VALUE_PROVINCE_NAME_BULAWAYO = "Bulawayo";

        public static final String VALUE_COUNTRY_ID_HARARE = "1";
        public static final String VALUE_PROVINCE_NAME_HARARE = "Harare";

        public static final String VALUE_COUNTRY_ID_MANICALAND = "1";
        public static final String VALUE_PROVINCE_NAME_MANICALAND = "Manicaland";

        public static final String VALUE_COUNTRY_ID_MASHONALAND_CENTRAL = "1";
        public static final String VALUE_PROVINCE_NAME_MASHONALAND_CENTRAL = "Mashonaland Central";

        public static final String VALUE_COUNTRY_ID_MASHONALAND_EAST = "1";
        public static final String VALUE_PROVINCE_NAME_MASHONALAND_EAST = "Mashonaland East";

        public static final String VALUE_COUNTRY_ID_MASHONALAND_WEST = "1";
        public static final String VALUE_PROVINCE_NAME_MASHONALAND_WEST = "Mashonaland West";

        public static final String VALUE_COUNTRY_ID_MASVINGO = "1";
        public static final String VALUE_PROVINCE_NAME_MASVINGO = "Masvingo";

        public static final String VALUE_COUNTRY_ID_MATABELELAND_NORTH = "1";
        public static final String VALUE_PROVINCE_NAME_MATABELELAND_NORTH = "Matabeleland North";

        public static final String VALUE_COUNTRY_ID_MATABELELAND_SOUTH = "1";
        public static final String VALUE_PROVINCE_NAME_MATABELELAND_SOUTH = "Matabeleland South";

        public static final String VALUE_COUNTRY_ID_MIDLANDS = "1";
        public static final String VALUE_PROVINCE_NAME_MIDLANDS = "Midlands";
    }

    public static abstract class City{
        public static final String TABLE_NAME = "city";
        public static final String COLUMN_CITY_ID = "_id";
        public static final String COLUMN_PROVINCE_ID = "province_id";
        public static final String COLUMN_COUNTRY_ID = "country_id";
        public static final String COLUMN_CITY_NAME = "city_name";

        public static final String VALUE_PROVINCE_ID_BEITBRIDGE = "9";
        public static final String VALUE_COUNTRY_ID_BEITBRIDGE = "1";
        public static final String VALUE_CITY_NAME_BEITBRIDGE = "Beitbridge";

        public static final String VALUE_PROVINCE_ID_BINDURA = "";
        public static final String VALUE_COUNTRY_ID_BINDURA = "1";
        public static final String VALUE_CITY_NAME_BINDURA = "Bindura";

        public static final String VALUE_PROVINCE_ID_BULAWAYO = "1";
        public static final String VALUE_COUNTRY_ID_BULAWAYO = "1";
        public static final String VALUE_CITY_NAME_BULAWAYO = "Bulawayo";

        public static final String VALUE_PROVINCE_ID_CHEGUTU = "";
        public static final String VALUE_COUNTRY_ID_CHEGUTU = "1";
        public static final String VALUE_CITY_NAME_CHEGUTU = "Chegutu";

        public static final String VALUE_PROVINCE_ID_CHINHOYI = "";
        public static final String VALUE_COUNTRY_ID_CHINHOYI = "1";
        public static final String VALUE_CITY_NAME_CHINHOYI = "Chinhoyi";

        public static final String VALUE_PROVINCE_ID_CHIPINGE = "3";
        public static final String VALUE_COUNTRY_ID_CHIPINGE = "1";
        public static final String VALUE_CITY_NAME_CHIPINGE = "Chipinge";

        public static final String VALUE_PROVINCE_ID_CHIREDZI = "7";
        public static final String VALUE_COUNTRY_ID_CHIREDZI = "1";
        public static final String VALUE_CITY_NAME_CHIREDZI = "Chiredzi";

        public static final String VALUE_PROVINCE_ID_CHITUNGWIZA = "";
        public static final String VALUE_COUNTRY_ID_CHITUNGWIZA = "1";
        public static final String VALUE_CITY_NAME_CHITUNGWIZA = "Chitungwiza";

        public static final String VALUE_PROVINCE_ID_GWANDA = "9";
        public static final String VALUE_COUNTRY_ID_GWANDA = "1";
        public static final String VALUE_CITY_NAME_GWANDA = "Gwanda";

        public static final String VALUE_PROVINCE_ID_GWERU = "10";
        public static final String VALUE_COUNTRY_ID_GWERU = "1";
        public static final String VALUE_CITY_NAME_GWERU = "Gweru";

        public static final String VALUE_PROVINCE_ID_HARARE = "2";
        public static final String VALUE_COUNTRY_ID_HARARE = "1";
        public static final String VALUE_CITY_NAME_HARARE = "Harare";

        public static final String VALUE_PROVINCE_ID_KADOMA = "";
        public static final String VALUE_COUNTRY_ID_KADOMA = "1";
        public static final String VALUE_CITY_NAME_KADOMA = "Kadoma";

        public static final String VALUE_PROVINCE_ID_KARIBA = "";
        public static final String VALUE_COUNTRY_ID_KARIBA = "1";
        public static final String VALUE_CITY_NAME_KARIBA = "Kariba";

        public static final String VALUE_PROVINCE_ID_KAROI = "";
        public static final String VALUE_COUNTRY_ID_KAROI = "1";
        public static final String VALUE_CITY_NAME_KAROI = "Karoi";

        public static final String VALUE_PROVINCE_ID_KWEKWE = "10";
        public static final String VALUE_COUNTRY_ID_KWEKWE = "1";
        public static final String VALUE_CITY_NAME_KWEKWE = "Kwekwe";

        public static final String VALUE_PROVINCE_ID_MARONDERA = "";
        public static final String VALUE_COUNTRY_ID_MARONDERA = "1";
        public static final String VALUE_CITY_NAME_MARONDERA = "Marondera";

        public static final String VALUE_PROVINCE_ID_MASVINGO = "7";
        public static final String VALUE_COUNTRY_ID_MASVINGO = "1";
        public static final String VALUE_CITY_NAME_MASVINGO = "Masvingo";

        public static final String VALUE_PROVINCE_ID_MUTARE = "3";
        public static final String VALUE_COUNTRY_ID_MUTARE = "1";
        public static final String VALUE_CITY_NAME_MUTARE = "Mutare";

        public static final String VALUE_PROVINCE_ID_NYANGA = "3";
        public static final String VALUE_COUNTRY_ID_NYANGA = "1";
        public static final String VALUE_CITY_NAME_NYANGA = "Nyanga";

        public static final String VALUE_PROVINCE_ID_PLUMTREE = "9";
        public static final String VALUE_COUNTRY_ID_PLUMTREE = "1";
        public static final String VALUE_CITY_NAME_PLUMTREE = "Plumtree";

        public static final String VALUE_PROVINCE_ID_RUSAPE = "";
        public static final String VALUE_COUNTRY_ID_RUSAPE = "1";
        public static final String VALUE_CITY_NAME_RUSAPE = "Rusape";

        public static final String VALUE_PROVINCE_ID_SHURUGWI = "10";
        public static final String VALUE_COUNTRY_ID_SHURUGWI = "1";
        public static final String VALUE_CITY_NAME_SHURUGWI = "Shurugwi";

        public static final String VALUE_PROVINCE_ID_VICTORIA_FALLS = "";
        public static final String VALUE_COUNTRY_ID_VICTORIA_FALLS = "1";
        public static final String VALUE_CITY_NAME_VICTORIA_FALLS = "Victoria Falls";

        public static final String VALUE_PROVINCE_ID_ZVISHAVANE = "";
        public static final String VALUE_COUNTRY_ID_ZVISHAVANE = "1";
        public static final String VALUE_CITY_NAME_ZVISHAVANE = "Zvishavane";
    }

    public static abstract class Location{
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_LOCATION_ID = "_id";
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_LOCATION_NAME = "location_name";
        public static final String COLUMN_LOCATION_BROADCAST_RANGE = "broadcast_range";

        public static final String VALUE_BEITBRIDGE_CITY_ID = "1";
        public static final String VALUE_BEITBRIDGE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_BEITBRIDGE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_BEITBRIDGE_LOCATION_NAME_DULIBADZIMU = "Dulibadzimu";
        public static final String VALUE_BEITBRIDGE_LOCATION_BROADCAST_RANGE_DULIBADZIMU = "local";

        public static final String VALUE_BINDURA_CITY_ID = "2";
        public static final String VALUE_BINDURA_LOCATION_NAME_AERODROME = "Aerodrome";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_AERODROME = "local";

        public static final String VALUE_BINDURA_LOCATION_NAME_BINDURA_SUBURB = "Bindura Suburb";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_BINDURA_SUBURB = "local";

        public static final String VALUE_BINDURA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_BINDURA_LOCATION_NAME_CHIPADZE = "Chipadze";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CHIPADZE = "local";

        public static final String VALUE_BINDURA_LOCATION_NAME_CHIWARIDZO = "Chiwaridzo";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_CHIWARIDZO = "local";

        public static final String VALUE_BINDURA_LOCATION_NAME_GARIKAI = "Garikai";
        public static final String VALUE_BINDURA_LOCATION_BROADCAST_RANGE_GARIKAI = "local";

        public static final String VALUE_BULAWAYO_CITY_ID = "3";
        public static final String VALUE_BULAWAYO_LOCATION_NAME_ASCOT = "Ascot";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ASCOT = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BARBOUR_FIELDS = "Barbour Fields";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BARBOUR_FIELDS = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BARHAM_GREEN = "Barham Green";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BARHAM_GREEN = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BEACON_HILL = "Beacon Hill";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BEACON_HILL = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BELLEVUE = "Bellevue";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELLEVUE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BELMONT = "Belmont";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELMONT = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BELMONT_INDUSTRIAL_AREA = "Belmont Industrial Area";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BELMONT_INDUSTRIAL_AREA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BRADFIELD = "Bradfield";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BRADFIELD = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_BURNSIDE = "Burnside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_BURNSIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_CEMENT = "Cement";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_CEMENT = "national";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_CITY_CENTER = "City Center";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_CITY_CENTER = "national";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_COWDRAY_PARK = "Cowdray Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_COWDRAY_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_DONNINGTON = "Donnington";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DONNINGTON = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_DONNINGTON_WEST = "Donnington West";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DONNINGTON_WEST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_DOUGLASDALE = "Douglasdale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_DOUGLASDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ELOANA = "Eloana";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ELOANA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_EMAKHANDENI = "Emakhandeni";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_EMAKHANDENI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_EMGANWINI = "Emganwini";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_EMGANWINI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ENQAMENI = "Enqameni";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENQAMENI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ENQOTSHENI = "Enqotsheni";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENQOTSHENI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ENTUMBANE = "Entumbane";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ENTUMBANE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_FAGADOLA = "Fagadola";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FAGADOLA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_FAMONA = "Famona";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FAMONA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_FORTUNES_GATE = "Fortunes Gate";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FORTUNES_GATE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_FOUR_WINDS = "Four Winds";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_FOUR_WINDS = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GLENCOE = "Glencoe";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENCOE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GLENGARY = "Glengary";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENGARY = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GLENVILLE = "Glenville";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GLENVILLE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GRANITE_PARK = "Granite Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GRANITE_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GREEN_HILL = "Green Hill";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GREEN_HILL = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_GWABALANDA = "Gwabalanda";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_GWABALANDA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HARRISVALE = "Harrisvale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HARRISVALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HELENVALE = "Helenvale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HELENVALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HIGHMOUNT = "Highmount";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HIGHMOUNT = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HILLCREST = "Hillcrest";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLCREST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HILLSIDE = "Hillside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLSIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HILLSIDE_SOUTH = "Hillside South";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HILLSIDE_SOUTH = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HUME_PARK = "Hume Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HUME_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_HYDE_PARK = "Hyde Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_HYDE_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_Ilanda = "Ilanda";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_Ilanda = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_IMINYELA = "Iminyela";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_IMINYELA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_INTINI = "Intini";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_INTINI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_JACARANDA = "Jacaranda";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_JACARANDA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KELVIN = "Kelvin";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KELVIN = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KENILWORTH = "Kenilworth";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KENILWORTH = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KHUMALO = "Khumalo";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KHUAMLO = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KHUMALO_NORTH = "Khumalo North";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KHUMALO_NORTH = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KILALLO = "Kilallo";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KILALLO = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KILLARNEY = "Killarney";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KILLARNEY = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_KINGSDALE = "Kingsdale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_KINGSDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_LAKESIDE = "Lakeside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LAKESIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_LOBENGULA = "Lobengula";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOBENGULA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_LOBENVALE = "Lobenvale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOBENVALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_LOCHVIEW = "Lochview";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LOCHVIEW = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_LUVEVE = "Luveve";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_LUVEVE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MABUTHWENI = "Mabuthweni";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MABUTHWENI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE = "Magwegwe";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE_NORTH = "Magwegwe North";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE_NORTH = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MAGWEGWE_WEST = "Magwegwe West";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAGWEGWE_WEST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MAHATSHULA = "Mahatshula";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAHATSHULA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MAKOKOBA = "Makokoba";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MAKOKOBA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MALINDELA = "Malindela";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MALINDELA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MANNINGDALE = "Manningdale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MANNINGDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MARLANDS = "Marlands";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MARLANDS = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MATSHEUMHLOPHE = "Matsheumhlophe";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MATSHEUMHLOPHE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MATSHOBANA = "Matshonbana";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MATSHOBANA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MONTGOMERY = "Montgomery";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MONTGOMERY = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MONTROSE = "Montrose";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MONTROSE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MORNINGSIDE = "Morningside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MORNINGSIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MPOPOMA = "Mpopoma";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MPOPOMA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MUNDA = "Munda";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MUNDA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_MZILIKAZI = "Mzilikazi";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_MZILIKAZI = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NewLuveve = "New Luveve";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NewLuveve = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NEWSMANSFORD = "Newsmansford";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWSMANSFORD = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NEWTON = "Newton";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWTON = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NEWTON_WEST = "Newton West";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NEWTON_WEST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NGUBOYENJA = "Nguboyenja";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NGUBOYENJA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NJUBE = "Njube";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NJUBE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NKETA = "Nketa";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NKETA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NKULUMANE = "Nkulumane";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NKULUMANE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NORTH_END = "North End";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_END = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NORTH_LYNNE = "North Lynne";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_LYNNE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NORTH_TRENANCE = "North Trenance";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTH_TRENANCE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NORTHLEA = "Northlea";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTHLEA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NORTHVALE = "Northvale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NORTHVALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_NTABA_MOYO = "Ntaba Moyo";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_NTABA_MOYO = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ORANGE_GROVE = "Orange Grove";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ORANGE_GROVE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PADDONHURST = "Paddonhurst";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PADDONHURST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PARKLANDS = "Parklands";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PARKLANDS = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PARKVIEW = "Parkview";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PARKVIEW = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PHELANDABA = "Phelandaba";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PHELANDABA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PUMULA = "Pumula";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PUMULA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_PUMULA_SOUTH = "Pumula South";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_PUMULA_SOUTH = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK = "Queens Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK_EAST = "Queens Park East";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK_EAST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_QUEENS_PARK_WEST = "Queens Park West";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_QUEENS_PARK_WEST = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_RANGEMORE = "Rangemore";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RANGEMORE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_RAYLTON = "Raylton";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RAYLTON = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_RICHMOND = "Richmond";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RICHMOND = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_RIVERSIDE = "Riverside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_RIVERSIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_ROMNEY_PARK = "Romney Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_ROMNEY_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_SIZINDA = "Sizinda";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SIZINDA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_SOUTHDALE = "Southdale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SOUTHDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_SOUTHWOLD = "Southwold";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SOUTHWOLD = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_STEELDALE = "Steeldale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_STEELDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_SUNNINGHILL = "Sunninghill";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SUNNINGHILL = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_SUNNYSIDE = "Sunnyside";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_SUNNYSIDE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_TEGELA = "Tegela";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TEGELA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_THE_JUNGLE = "The Jungle";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_THE_JUNGLE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_THORNGROVE = "Thorngrove";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_THORNGROVE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_TRENANCE = "Trenance";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TRENANCE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_TSHABALALA = "Tshabalala";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TSHABALALA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_TSHABALALA_EXTENSION = "Tshabalala Extension";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_TSHABALALA_EXTENSION = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_UMGUZA_ESTATE = "Umguza Estate";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_UMGUZA_ESTATE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_UPPER_RANGEMORE = "Upper Rangemore";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_UPPER_RANGEMORE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WATERFORD = "Waterford";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WATERFORD = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WATERLEA = "Waterlea";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WATERLEA = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WEST_SOMERTON = "West Somerton";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WEST_SOMERTON = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WESTGATE = "Westgate";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WESTGATE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WESONDALE = "Westondale";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WESONDALE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WILSGROVE = "Wilsgrove";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WILSGROVE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WINDSOR_PARK = "Windsor Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WINDSOR_PARK = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WOODLANDS = "Woodlands";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODLANDS = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WOODVILLE = "Woodville";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODVILLE = "local";

        public static final String VALUE_BULAWAYO_LOCATION_NAME_WOODVILLE_PARK = "Woodville Park";
        public static final String VALUE_BULAWAYO_LOCATION_BROADCAST_RANGE_WOODVILLE_PARK = "local";

        public static final String VALUE_CHEGUTU_CITY_ID = "4";
        public static final String VALUE_CHEGUTU_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_CHEGUTU_LOCATION = "Chegutu Location";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CHEGUTU_LOCATION = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_CHESTGATE = "Chestgate";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_CHESTGATE = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_HINTON_VILLE = "Hinton Ville";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_HINTON_VILLE = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_KAGUVI = "Kaguvi";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_KAGUVI = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_MVOVO = "Mvovo";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_MVOVO = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_PFUPAJENA = "Pfupajena";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_PFUPAJENA = "local";

        public static final String VALUE_CHEGUTU_LOCATION_NAME_RIFLE_RANGE_ZMDC = "Rifle Range ZMDC";
        public static final String VALUE_CHEGUTU_LOCATION_BROADCAST_RANGE_RIFLE_RANGE_ZMDC = "local";

        public static final String VALUE_CHINHOYI_CITY_ID = "5";
        public static final String VALUE_CHINHOYI_LOCATION_NAME_BRUNDISH = "Brundish";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_BRUNDISH = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_CHIKONOHONO = "Chikonohono";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_CHIKONOHONO = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_COLD_STREAM = "Cold Stream";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_COLD_STREAM = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_GAZEMA = "Gazema";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_GAZEMA = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_GUNHILL = "Gunhill";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_GUNHILL = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_HUNYANI = "Hunyani";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_HUNYANI = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_MUTAPA_SECTION = "Mutapa Section";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_MUTAPA_SECTION = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_MUZARE = "Muzare";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_MUZARE = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_ORANGE_GROOVE = "Orange Groove";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_ORANGE_GROOVE = "local";

        public static final String VALUE_CHINHOYI_LOCATION_NAME_RUVIMBO = "Ruvimbo";
        public static final String VALUE_CHINHOYI_LOCATION_BROADCAST_RANGE_RUVIMBO = "local";

        public static final String VALUE_CHIPINGE_CITY_ID = "6";
        public static final String VALUE_CHIPINGE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_CHIPINGE_LOCATION_NAME_GAZA_TOWNSHIP = "Gaza Township";
        public static final String VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_GAZA_TOWNSHIP = "local";

        public static final String VALUE_CHIPINGE_LOCATION_NAME_LOW_DENSITY = "Low Density";
        public static final String VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_LOW_DENSITY = "local";

        public static final String VALUE_CHIPINGE_LOCATION_NAME_MEDIUM_DENSITY = "Medium Density";
        public static final String VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_MEDIUM_DENSITY = "local";

        public static final String VALUE_CHIPINGE_LOCATION_NAME_USANGA = "Usanga";
        public static final String VALUE_CHIPINGE_LOCATION_BROADCAST_RANGE_USANGA = "local";

        public static final String VALUE_CHIREDZI_CITY_ID = "7";
        public static final String VALUE_CHIREDZI_LOCATION_NAME_BUFFALO_RANGE = "Buffalo Range";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_BUFFALO_RANGE = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_HIPPO_VALLEY = "Hippo Valley";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_HIPPO_VALLEY = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_MALILANGWE = "Malilangwe";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_MALILANGWE = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_MUKWASINI = "Mukwasini";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_MUKWASINI = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_NANDI = "Nandi";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_NANDI = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_TOWN = "Town";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TOWN = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_TRIANGLE = "TRIANGLE";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TRIANGLE = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_TSHOVANI = "Tshovani";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_TSHOVANI = "local";

        public static final String VALUE_CHIREDZI_LOCATION_NAME_ZSA = "ZSA";
        public static final String VALUE_CHIREDZI_LOCATION_BROADCAST_RANGE_ZSA = "local";

        public static final String VALUE_CHITUNGWIZA_CITY_ID = "8";
        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_MAKONI = "Makoni";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MAKONI = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_MANYAME_PARK = "Manyame Park";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MANYAME_PARK = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_MAYAMBARA = "Mayambara";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_MAYAMBARA = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_NYATSIME = "Nyatsime";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_NYATSIME = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_ROCKVIEW = "Rockview";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ROCKVIEW = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_SEKE = "Seke";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_SEKE = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_SEKE_RURAL = "Seke Rural";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_SEKE_RURAL = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_ST_MARYS = "St Mary's";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ST_MARYS = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_ZENGEZA = "Zengeza";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ZENGEZA = "local";

        public static final String VALUE_CHITUNGWIZA_LOCATION_NAME_ZIKO = "Ziko";
        public static final String VALUE_CHITUNGWIZA_LOCATION_BROADCAST_RANGE_ZIKO = "local";

        public static final String VALUE_GWANDA_CITY_ID = "9";
        public static final String VALUE_GWANDA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_GWANDA_LOCATION_NAME_GENEVA = "Geneva";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_GENEVA = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_JACARANDA = "Jacaranda";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_JACARANDA = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_JAHUNDA = "Jahunda";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_JAHUNDA = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_MARRIAGE = "Marriage";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_MARRIAGE = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_PHAKAMA = "Phakama";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_PHAKAMA = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_SPITZKOP = "Spitzkop";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_SPITZKOP = "local";

        public static final String VALUE_GWANDA_LOCATION_NAME_ULTRA_HIGH = "Ultra High";
        public static final String VALUE_GWANDA_LOCATION_BROADCAST_RANGE_ULTRA_HIGH = "local";

        public static final String VALUE_GWERU_CITY_ID = "10";
        public static final String VALUE_GWERU_LOCATION_NAME_ASCOT = "Ascot";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_ASCOT = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_ATHLONE = "Athlone";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_ATHLONE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_BRACKENHURST = "Brackenhurst";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_BRACKENHURST = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_GWERU_LOCATION_NAME_CHRISTMAS_GIFT = "Christmas Gift";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_CHRISTMAS_GIFT = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_CLIFTON_PARK = "Clifton Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_CLIFTON_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_DAYLESFORD = "Daylesford";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_DAYLESFORD = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_ENFIELD = "Enfield";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_ENFIELD = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_GWERU_EAST = "Gweru East";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_GWERU_EAST = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_HARBEN_PARK = "Harben Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_HARBEN_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_HERTFORDSHIRE = "Hertfordshire";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_HERTFORDSHIRE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_IVENE = "Ivene";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_IVENE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_KOPJE = "Kopje";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_KOPJE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_LUNDI_PARK = "Lundi Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_LUNDI_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_MAMBO = "Mambo";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_MAMBO = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_MKOBA = "Mkoba";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_MKOBA = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_MTAPA = "Mtapa";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_MTAPA = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_MTAUSI_PARK = "Mtausi Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_MTAUSI_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_NORTHLEA = "Northlea";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_NORTHLEA = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_RIDGEMOND = "Ridgemond";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_RIDGEMOND = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_RIVERSIDE = "Riverside";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_RIVERSIDE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_RUNDOLF_PARK = "Rundolf Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_RUNDOLF_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_SENGA = "Senga";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_SENGA = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_NEHOSHO = "Nehosho";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_NEHOSHO = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_SHAMROCK_PARK = "Shamrock Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_SHAMROCK_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_SITHABILE_PARK = "Sithabile Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_SITHABILE_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_SOUTHDOWNS = "Southdowns";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_SOUTHDOWNS = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_SOUTHVIEW = "Southview";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_SOUTHVIEW = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_ST_ANNES_DRIVE = "St Annes Drive";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_ST_ANNES_DRIVE = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_WINDSOR_PARK = "Windsor Park";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_WINDSOR_PARK = "local";

        public static final String VALUE_GWERU_LOCATION_NAME_WOODLANDS = "Woodlands";
        public static final String VALUE_GWERU_LOCATION_BROADCAST_RANGE_WOODLANDS = "local";

        public static final String VALUE_HARARE_CITY_ID = "11";
        public static final String VALUE_HARARE_LOCATION_NAME_ADYLINN = "Adylinn";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ADYLINN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ALEXANDRA_PARK = "Alexandra Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ALEXANDRA_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_AMBY = "Amby";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_AMBY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ARCADIA = "Arcadia";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARCADIA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ARDBENNIE = "Ardbennie";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARDBENNIE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ARLINGTON = "Arlington";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ARLINGTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ASHBRITTLE = "Ashbrittle";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASHBRITTLE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ASHDOWN_PARK = "Ashdown Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASHDOWN_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ASPINDALE_PARK = "Aspindale Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ASPINDALE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ATHLONE = "Athlone";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ATHLONE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_AVENUES = "Avenues";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVENUES = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_AVONDALE = "Avondale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_AVONDALE_WEST = "Avondale West";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONDALE_WEST = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_AVONLEA = "Avonlea";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_AVONLEA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BALLANTYNE_PARK = "Ballantyne Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BALLANTYNE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BELGRAVIA = "Belgravia";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BELGRAVIA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BELVEDERE = "Belvedere";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BELVEDERE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BEVERLEY = "Beverley";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BEVERLEY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BEVERLEY_WEST = "Beverley West";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BEVERLEY_WEST = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BLOOMINGDALE = "Bloomingdale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BLOOMINGDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BLUFF_HILL = "Bluff Hill";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BLUFF_HILL = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BORROWDALE = "Borrowdale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BORROWDALE_BROOKE = "Borrowdale Brooke";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE_BROOKE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BORROWDALE_BROOKE_WEST = "Borrowdale Brooke West";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BORROWDALE_BROOKE_WEST = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BRAESIDE = "Braeside";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BRAESIDE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BROOKE_RIDGE = "Brooke Ridge";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BROOKE_RIDGE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_BUDIRIRO = "Budiriro";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_BUDIRIRO = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CARRICK_CREAGH = "Carrick Creagh";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CARRICK_CREAGH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHADCOMBE = "Chadcombe";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHADCOMBE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHIKURUBI = "Chikurubi";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIKURUBI = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHIPUKUTU = "Chipukutu";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIPUKUTU = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHIREMBA_PARK = "Chiremba Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIREMBA_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHISIPITI = "Chisipiti";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHISIPITI = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CHIZHANJE = "Chizhanje";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CHIZHANJE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CITY_CENTRE = "City Centre";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CITY_CENTRE = "national";

        public static final String VALUE_HARARE_LOCATION_NAME_CIVIC_CENTRE = "Civic Centre";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CIVIC_CENTRE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_COLD_COMFORT = "Cold Comfort";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLD_COMFORT = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_COLNE_VALLEY = "Colne Valley";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLNE_VALLEY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_COLRAY = "Colray";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_COLRAY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CORONATION_PARK = "Coronation Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CORONATION_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_COTSWOLD_HILLS = "Cotswold Hills";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_COTSWOLD_HILLS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CRANBOURNE_PARK = "Cranbourne Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CRANBOURNE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_CROWBOROUGH = "Crowborough";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_CROWBOROUGH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_DAMOFALLS = "Damofalls";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_DAMOFALLS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_DAWN_HILL = "Dawn Hill";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_DAWN_HILL = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_DONNYBROOK = "Donnybrook";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_DONNYBROOK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_DZIVARASEKWA = "Dzivarasekwa";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_DZIVARASEKWA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EASTLEA = "Eastlea";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EASTLEA_NORTH = "Eastlea North";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA_NORTH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EASTLEA_SOUTH = "Eastlea South";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTLEA_SOUTH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EASTVIEW = "Eastview";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EASTVIEW = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EMERALD_HILL = "Emerald Hill";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EMERALD_HILL = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_EPWORTH = "Epworth";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_EPWORTH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GEVSTEIN_PARK = "Gevstein Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GEVSTEIN_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GLAUDINA = "Glaudina";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLAUDINA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GLEN_LORNE = "Glen Lorne";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_LORNE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GLEN_NORAH = "Glen Norah";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_NORAH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GLEN_VIEW = "Glen View";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLEN_VIEW = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GLENWOOD = "Glenwood";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GLENWOOD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GRANGE = "Grange";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GRANGE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GRANITESIDE = "Graniteside";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GRANITESIDE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GREEN_GROVE = "Green Grove";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREEN_GROVE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GREENCROFT = "Greencroft";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREENCROFT = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GREENDALE = "Greendale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREENDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GREYSTONE_PARK = "Greystone Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GREYSTONE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GROBBIE_PARK = "Grobbie Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GROBBIE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GROOMBRIDGE = "Groombridge";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GROOMBRIDGE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_GUN_HILL = "Gun Hill";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_GUN_HILL = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HAIG_PARK = "Haig Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HAIG_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HOTCLIFFE = "Hotcliffe";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOTCLIFFE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HATFIELD = "Hatfield";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HATFIELD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HELENSVALE = "Helensvale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HELENSVALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HIGHFIELD = "Highfield";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HIGHFIELD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HIGHLANDS = "Highlands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HIGHLANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HILLSIDE = "Hillside";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HILLSIDE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HOGERTY_HILL = "Hogerty Hill";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOGERTY_HILL = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HOPLEY = "Hopley";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOPLEY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_HOUGHTON_PARK = "Houghton Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_HOUGHTON_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_INDUNA = "Induna";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_INDUNA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KAMBANJE = "Kambanje";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KAMBANJE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KAMBUZUMA = "Kambuzuma";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KAMBUZUMA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KENSINGTON = "Kensington";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KENSINGTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KOPJE = "Kopje";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KOPJE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KUTSAGA = "Kutsaga";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KUTSAGA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_KUWADZANA = "Kuwadzana";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_KUWADZANA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LETOMBO_PARK = "Letombo Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LETOMBO_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LEWISAM = "Lewisam";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LEWISAM = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LICHENDALE = "Lichendale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LICHENDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LICOLN_GREEN = "Lincoln Green";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LICOLN_GREEN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LITTLE_NORFOLK = "Little Norfolk";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LITTLE_NORFOLK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LOCHINVAR = "Lochinvar";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LOCHINVAR = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LOGAN_PARK = "Logan Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LOGAN_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LORELEI = "Lorelei";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LORELEI = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_LUNA = "Luna";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_LUNA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MABELREIGN = "Mabelreign";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MABELREIGN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MABVUKU = "Mabvuku";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MABVUKU = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MAINWAY_MEADOWS = "Mainway Meadows";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MAINWAY_MEADOWS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MALVERN = "Malvern";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MALVERN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MANDARA = "Mandara";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANDARA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MANIDODA_PARK = "Manidoda Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANIDODA_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MANRESA = "Manresa";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MANRESA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MARIMBA_PARK = "Marimba Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MARIMBA_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MARLBOROUGH = "Marlborough";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MARLBOROUGH = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MAYFIELD_PARK = "Mayfield Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MAYFIELD_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MBARE = "Mbare";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MBARE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MEYRICK_PARK = "Meyrick Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MEYRICK_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MIDLANDS = "Midlands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MIDLANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MILTON_PARK = "Milton Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MILTON_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MONDORA = "Mondora";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MONDORA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MONOVALE = "Monovale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MONOVALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MOUNT_HAMPDEN = "Mount Hampden";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MOUNT_HAMPDEN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MOUNT_PLEASANT = "Mount Pleasant";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MOUNT_PLEASANT = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MSASA = "Msasa";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MSASA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MSASA_PARK = "Msasa Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MSASA_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MUFAKOSE = "Mufakose";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUFAKOSE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MUKUVISI = "Mukuvisi";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUKUVISI = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MUKUVISI_PARK = "Mukuvisi Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MUKUVISI_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_NEW_RIDGEVIEW = "New Ridgeview";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_NEW_RIDGEVIEW = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_NEWLANDS = "Newlands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_NEWLANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_NKWISI_PARK = "Nkwisi Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_NKWISI_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_NORTHWOOD = "Northwood";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_NORTHWOOD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_OLD_FOREST_PARK = "Old Forest Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_OLD_FOREST_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_MEADOWLANDS = "Meadowlands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_MEADOWLANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_PARKTON = "Parkton";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_PARKTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_PHILADELPHIA = "Philadelphia";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_PHILADELPHIA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_PAMONA = "Pamona";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_PAMONA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_PROSPECT = "Prospect";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_PROSPECT = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_PROSPECT_PARK = "Prospect Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_PROSPECT_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_QUEENSDALE = "Queensdale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_QUEENSDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_QUINNINGTON = "Quinnington";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_QUINNINGTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RHODESVILLE = "Rhodesville";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RHODESVILLE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RIDGEVIEW = "Ridgeview";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RIDGEVIEW = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_REITFONTEIN = "Reitfontein";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_REITFONTEIN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RINGLEY = "Ringley";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RINGLEY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ROLF_VALLEY = "Rolf Valley";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ROLF_VALLEY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RUGARE = "Rugare";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RUGARE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RUNNINGVILLE = "Runniville";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RUNNINGVILLE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_RYELANDS = "Ryelands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_RYELANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SANGANANI_PARK = "Sanganani Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SANGANANI_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SCIENCE_PARK = "Science Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SCIENCE_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SENTOSA = "Sentosa";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SENTOSA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SHAWASHA_HILLS = "Shawasha Hills";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHAWASHA_HILLS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SHERWOOD_PARK = "Sherwood Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHERWOOD_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SHARTSON = "Shartson";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SHARTSON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SOUTHERTON = "Southerton";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SOUTHERTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ST_ANDREWS_PARK = "St Andrews Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ST_ANDREWS_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ST_MARTINS = "St Martins";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ST_MARTINS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_STRATHAVEN = "Strathaven";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_STRATHAVEN = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SUNNINGDALE = "Sunningdale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNNINGDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SUNRIDGE = "Sunridge";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNRIDGE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SUNRISE = "Sunrise";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNRISE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_SUNWAY_CITY = "Sunway City";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_SUNWAY_CITY = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_TAFARA = "Tafara";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_TAFARA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_THE_GRANGE = "The Grange";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_THE_GRANGE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_TYNWALD = "Tynwald";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_TYNWALD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_UMWINSIDALE = "Umwinsidale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_UMWINSIDALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_UPLANDS = "Uplands";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_UPLANDS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_VAINONA = "Vainona";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_VAINONA = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_VALENCEDENE = "Valencedene";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_VALENCEDENE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_VENTERSBURG = "Ventersburg";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_VENTERSBURG = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WARREN_PARK = "Warren Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WARREN_PARK = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WATERFALLS = "Waterfalls";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WATERFALLS = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WESTGATE = "Westgate";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WESTGATE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WESTWOOD = "Westwood";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WESTWOOD = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WILLOVALE = "Willovale";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WILLOVALE = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WILMINGTON = "Wilmington";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WILMINGTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_WORKINGTON = "Workington";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_WORKINGTON = "local";

        public static final String VALUE_HARARE_LOCATION_NAME_ZIMRE_PARK = "Zimre Park";
        public static final String VALUE_HARARE_LOCATION_BROADCAST_RANGE_ZIMRE_PARK = "local";

        public static final String VALUE_KADOMA_CITY_ID = "12";
        public static final String VALUE_KADOMA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_KADOMA_LOCATION_NAME_CHAKARI = "Chakari";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_CHAKARI = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_COTTON_RESEARCH = "Cotton Research";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_COTTON_RESEARCH = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_EASTVIEW = "Eastview";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_EASTVIEW = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_EIFFEL_FLATS = "Eiffel Flats";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_EIFFEL_FLATS = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_INGEZI = "Ingezi";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_INGEZI = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_MORNINGTON = "Mornington";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_MORNINGTON = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_PATCHWAY = "Patchway";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_PATCHWAY = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_RIMUKA = "Rimuka";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_RIMUKA = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_RIO_TINTO = "Rio Tinto";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_RIO_TINTO = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_WAVERLY = "Waverly";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_WAVERLY = "local";

        public static final String VALUE_KADOMA_LOCATION_NAME_WESTVIEW = "Westview";
        public static final String VALUE_KADOMA_LOCATION_BROADCAST_RANGE_WESTVIEW = "local";

        public static final String VALUE_KARIBA_CITY_ID = "13";
        public static final String VALUE_KARIBA_LOCATION_NAME_AERIAL_HILL = "Aerial Hill";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_AERIAL_HILL = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_BAOBAB_RIDGE = "Baobab Ridge";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BAOBAB_RIDGE = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_BATONGA = "Batonga";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BATONGA = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_BOULDER_RIDGE = "Boulder Ridge";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_BOULDER_RIDGE = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_KARIBA_LOCATION_NAME_HEIGTHS = "Heights";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_HEIGTHS = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_HOSPITAL_HILL = "Hospital Hill";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_HOSPITAL_HILL = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_MICA_POINT = "Mica Point";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_MICA_POINT = "local";

        public static final String VALUE_KARIBA_LOCATION_NAME_NYAMHUNGA = "Nyamhunga";
        public static final String VALUE_KARIBA_LOCATION_BROADCAST_RANGE_NYAMHUNGA = "local";

        public static final String VALUE_KAROI_CITY_ID = "14";
        public static final String VALUE_KAROI_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_KAROI_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_KAROI_LOCATION_NAME_CHIEDZA = "Chiedza";
        public static final String VALUE_KAROI_LOCATION_BROADCAST_RANGE_CHIEDZA = "local";

        public static final String VALUE_KAROI_LOCATION_NAME_CHIKANGWE = "Chikangwe";
        public static final String VALUE_KAROI_LOCATION_BROADCAST_RANGE_CHIKANGWE = "local";

        public static final String VALUE_KAROI_LOCATION_NAME_FLAMBOYANT = "Flamboyant";
        public static final String VALUE_KAROI_LOCATION_BROADCAST_RANGE_FLAMBOYANT = "local";

        public static final String VALUE_KWEKWE_CITY_ID = "15";
        public static final String VALUE_KWEKWE_LOCATION_NAME_AMAVENI = "Amaveni";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_AMAVENI = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_KWEKWE_LOCATION_NAME_FITCHLEY = "Fitchley";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_FITCHLEY = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_GARIKAI = "Garikai";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GARIKAI = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_GLENHOOD = "Glenhood";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GLENHOOD = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_GOLDEN_ACRES = "Golden Acres";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_GOLDEN_ACRES = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_MASASA = "Masasa";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_MASASA = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_MBIZO = "Mbizo";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_MBIZO = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_NEW_TOWN = "New Town";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_NEW_TOWN = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_REDCLIFF = "Redcliff";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_REDCLIFF = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_RUTENDO = "Rutendo";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_RUTENDO = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_TOWNHOOD = "Townhood";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_TOWNHOOD = "local";

        public static final String VALUE_KWEKWE_LOCATION_NAME_WESTEND = "Westend";
        public static final String VALUE_KWEKWE_LOCATION_BROADCAST_RANGE_WESTEND = "local";

        public static final String VALUE_MARONDERA_CITY_ID = "16";
        public static final String VALUE_MARONDERA_LOCATION_NAME_1ST_STREET = "1st Street";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_1ST_STREET = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_2ND_STREET = "2nd Street";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_2ND_STREET = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_3RD_STREET = "3rd Street";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_3RD_STREET = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_4TH_STREET = "4th Street";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_4TH_STREET = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_MARONDERA_LOCATION_NAME_CHERIMA_RUJEKO = "Cherima/Rujeko";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CHERIMA_RUJEKO = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_CHERUTOMBO = "Cherutombo";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_CHERUTOMBO = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_DOMBOTOMBO = "Dombotombo";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_DOMBOTOMBO = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_GARIKAI_ELVESHOOD = "Garikai/Elveshood";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_GARIKAI_ELVESHOOD = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_LENDY_PARK = "Lendy Park";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_LENDY_PARK = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_MORNINGSIDE = "Morningside";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_MORNINGSIDE = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_NYAMENI = "Nyameni";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_NYAMENI = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_PARADISE = "Paradise";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_PARADISE = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_RUSIKE = "Rusike";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUSIKE = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_RUVIMBO_PARK = "Ruvimbo Park";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUVIMBO_PARK = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_RUWARE_PARK = "Ruware Park";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUWARE_PARK = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_RUZAWI_PARK = "Ruzawi Park";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_RUZAWI_PARK = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_WISTON_PARK = "Wiston Park";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_WISTON_PARK = "local";

        public static final String VALUE_MARONDERA_LOCATION_NAME_YELLOW_CITY = "Yellow City";
        public static final String VALUE_MARONDERA_LOCATION_BROADCAST_RANGE_YELLOW_CITY = "local";

        public static final String VALUE_MASVINGO_CITY_ID = "17";
        public static final String VALUE_MASVINGO_LOCATION_BUFFALO_RANGE = "Buffalo Range";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_BUFFALO_RANGE = "local";

        public static final String VALUE_MASVINGO_LOCATION_CBD = "CBD";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_MASVINGO_LOCATION_CLIPSTORN_PARK = "Clipstorn Park";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_CLIPSTORN_PARK = "local";

        public static final String VALUE_MASVINGO_LOCATION_EASTVILLE = "Eastville";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_EASTVILLE = "local";

        public static final String VALUE_MASVINGO_LOCATION_FOUR_INFANTRY_BATTALION = "Four | Infantry Battalion";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_FOUR_INFANTRY_BATTALION = "local";

        public static final String VALUE_MASVINGO_LOCATION_MORNINGSIDE = "Morningside";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_MORNINGSIDE = "local";

        public static final String VALUE_MASVINGO_LOCATION_MUCHEKE = "Mucheke";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_MUCHEKE = "local";

        public static final String VALUE_MASVINGO_LOCATION_RHODENE = "Rhodene";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RHODENE = "local";

        public static final String VALUE_MASVINGO_LOCATION_RUJEKO = "Rujeko";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RUJEKO = "local";

        public static final String VALUE_MASVINGO_LOCATION_RUNYARARO = "Runyararo";
        public static final String VALUE_MASVINGO_LOCATION_BROADCAST_RANGE_RUNYARARO = "local";

        public static final String VALUE_MUTARE_CITY_ID = "18";
        public static final String VALUE_MUTARE_LOCATION_NAME_AVENUES = "Avenues";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_AVENUES = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_BORDERALE = "Borderale";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_BORDERALE = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_MUTARE_LOCATION_NAME_CHIKANGA = "Chikanga";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CHIKANGA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_CHIKANGA_EXTENSION = "Chikanga Extension";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_CHIKANGA_EXTENSION = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_DANGAMVURA = "Dangamvura";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_DANGAMVURA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_DARLINGTON = "Darlington";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_DARLINGTON = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_FAIRBRIDGE_PARK = "Fairbridge Park";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FAIRBRIDGE_PARK = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_FERN_VALLEY = "Fern Valley";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FERN_VALLEY = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_FLORIDA = "Florida";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_FLORIDA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_GARIKAI_HLALANI_KUHLE = "Garikai Hlalani Kuhle";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GARIKAI_HLALANI_KUHLE = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_GIMBOKI = "Gimboki";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GIMBOKI = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_GREENSIDE = "Greenside";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GREENSIDE = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_GREENSIDE_EXTENSION = "Greenside Extension";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_GREENSIDE_EXTENSION = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_HOBHOUSE = "Hobhouse";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_HOBHOUSE = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_MAI_MARIA = "Mai Maria";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MAI_MARIA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_MORNINGSIDE = "Morningside";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MORNINGSIDE = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_MURAMBI = "Murambi";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_MURAMBI = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_NATVIEW_PARK = "Natview Park";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_NATVIEW_PARK = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_PALMERSTON = "Palmerston";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_PALMERSTON = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_SAKUBVA = "Sakubva";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_SAKUBVA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_ST_JOSEPHS = "St Josephs";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ST_JOSEPHS = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_TIGERS_KLOOF = "Tiger's Kloof";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_TIGERS_KLOOF = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_TORONTO = "Toronto";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_TORONTO = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_UTOPIA = "Utopia";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_UTOPIA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_WEIRMOUTH = "Weirmouth";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_WEIRMOUTH = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_WESTLEA = "Westlea";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_WESTLEA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_YEOVIL = "Yeovil";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_YEOVIL = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_ZIMTA = "Zimta";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ZIMTA = "local";

        public static final String VALUE_MUTARE_LOCATION_NAME_ZIMUNYA = "Zimunya";
        public static final String VALUE_MUTARE_LOCATION_BROADCAST_RANGE_ZIMUNYA = "local";

        public static final String VALUE_NYANGA_CITY_ID = "19";
        public static final String VALUE_NYANGA_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_NYANGA_LOCATION_NAME_DEPE_PARK = "Depe Park";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_DEPE_PARK = "local";

        public static final String VALUE_NYANGA_LOCATION_NAME_MANGONDOZA = "Mangondoza";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_MANGONDOZA = "local";

        public static final String VALUE_NYANGA_LOCATION_NAME_NYAMUKA = "Nyamuka";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_NYAMUKA = "local";

        public static final String VALUE_NYANGA_LOCATION_NAME_NYANGANI = "Nyangani";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_NYANGANI = "local";

        public static final String VALUE_NYANGA_LOCATION_NAME_ROCHDALE = "Rochdale";
        public static final String VALUE_NYANGA_LOCATION_BROADCAST_RANGE_ROCHDALE = "local";

        public static final String VALUE_PLUMTREE_CITY_ID = "20";
        public static final String VALUE_PLUMTREE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_DINGUMUZI = "Dingumuzi";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_DINGUMUZI = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_HEBRON = "Hebron";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_HEBRON = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_KARIBA = "Kariba";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_KARIBA = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_LAKEVIE = "Lakeview";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_LAKEVIE = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_MADUBES = "Madubes";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MADUBES = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_MATHENDELE = "Mathendele";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MATHENDELE = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_MATIWAZA = "Matiwaza";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MATIWAZA = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_MEDIUM_DENSITY = "Medium Density";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_MEDIUM_DENSITY = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_RANGIORE = "Rangiore";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_RANGIORE = "local";

        public static final String VALUE_PLUMTREE_LOCATION_NAME_ZBS = "ZBS";
        public static final String VALUE_PLUMTREE_LOCATION_BROADCAST_RANGE_ZBS = "local";

        public static final String VALUE_RUSAPE_CITY_ID = "21";
        public static final String VALUE_RUSAPE_LOCATION_NAME_CASTLE_BASE = "Castle Base";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CASTLE_BASE = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_RUSAPE_LOCATION_NAME_CHINGAIRA = "Chingaira";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CHINGAIRA = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_CROCODILE = "Crocodile";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_CROCODILE = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_GOPAL = "Gopal";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_GOPAL = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_LISAPI = "Lisapi";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_LISAPI = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_MABVAZUVA = "Mabvazuva";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MABVAZUVA = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_MAGAMBA = "Magamba";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MAGAMBA = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_MBUYANEHANDA = "Mbuyanehanda";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_MBUYANEHANDA = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_NYANGA_DRIVE = "Nyanga Drive";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_NYANGA_DRIVE = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_OFF_NYANGA_DRIVE = "Off Nyanga Drive";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_OFF_NYANGA_DRIVE = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_SILVERPOOL = "Silverpool";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_SILVERPOOL = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_TSANZAGURU = "Tsanzaguru";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_TSANZAGURU = "local";

        public static final String VALUE_RUSAPE_LOCATION_NAME_VENGERE = "Vengere";
        public static final String VALUE_RUSAPE_LOCATION_BROADCAST_RANGE_VENGERE = "local";

        public static final String VALUE_SHURUGWI_CITY_ID = "22";
        public static final String VALUE_SHURUGWI_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_DARK_CITY = "Dark City";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_DARK_CITY = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_IRONSIDE = "Ironside";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_IRONSIDE = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_MAKUSHA = "Makusha";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_MAKUSHA = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_PEAKMINE = "Peakmine";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_PEAKMINE = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_RAILWAY_BLOCK = "Railway Block";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_RAILWAY_BLOCK = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_TEBEKWE = "Tebekwe";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_TEBEKWE = "local";

        public static final String VALUE_SHURUGWI_LOCATION_NAME_ZBS = "ZBS";
        public static final String VALUE_SHURUGWI_LOCATION_BROADCAST_RANGE_ZBS = "local";

        public static final String VALUE_VICTORIA_FALLS_CITY_ID = "23";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_NAME_AERODROME = "Aerodrome";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_AERODROME = "local";

        public static final String VALUE_VICTORIA_FALLS_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_VICTORIA_FALLS_LOCATION_NAME_CHINOTIMBA = "Chinotimba";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_CHINOTIMBA = "local";

        public static final String VALUE_VICTORIA_FALLS_LOCATION_NAME_MKHOSANA = "Mkhosana";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_MKHOSANA = "local";

        public static final String VALUE_VICTORIA_FALLS_LOCATION_NAME_SUBURBS = "Suburbs";
        public static final String VALUE_VICTORIA_FALLS_LOCATION_BROADCAST_RANGE_SUBURBS = "local";

        public static final String VALUE_ZVISHAVANE_CITY_ID = "24";
        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_CBD = "CBD";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_CBD = "city";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_EASTVIEW = "Eastview";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_EASTVIEW = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_HIGHLANDS = "Highlands";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_HIGHLANDS = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_KANDODO = "Kandodo";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_KANDODO = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_MABULA = "Mabula";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MABULA = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_MAGLAS = "Maglas";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MAGLAS = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_MAKWASHA = "Makwasha";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MAKWASHA = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_MANDAVA = "Mandava";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_MANDAVA = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_NEIL = "Neil";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_NEIL = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_NOVEL = "Novel";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_NOVEL = "local";

        public static final String VALUE_ZVISHAVANE_LOCATION_NAME_PLATINUM_PARK = "Platinum Park";
        public static final String VALUE_ZVISHAVANE_LOCATION_BROADCAST_RANGE_PLATINUM_PARK = "local";
    }

}