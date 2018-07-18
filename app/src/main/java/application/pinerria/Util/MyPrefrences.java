package application.pinerria.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 2/7/2017.
 */

public class MyPrefrences {

    static SharedPreferences loginPreferences;
    public static SharedPreferences userIdPrefences;
    public static SharedPreferences mobilePrefences;
    public static SharedPreferences emailPrefences;
    public static SharedPreferences name;
    public static SharedPreferences PASSWORD;
    public static SharedPreferences BUYCATPREF;
    public static SharedPreferences HOMETYPE;
    public static SharedPreferences CITYTYPEID;
    public static SharedPreferences CITYTYNAME;
    public static SharedPreferences AREATYPEID;
    public static SharedPreferences COMPTYPEID;
    public static SharedPreferences AGENTID;
    public static SharedPreferences POSTINGID;
    public static SharedPreferences POSTINGID2;
    public static SharedPreferences PAYMENTID;

    public static String USER_LOGIN = "userlogin";
    public static String USER_ID = "user_id";
    public static String MOBILE_NO = "MOBILE_NO";
    public static String EMAIL_ID = "EMAIL_ID";
    public static String FULLNAME = "FULLNAME";
    public static String PASS = "PASS";
    public static String BUYCAT = "BUYCAT";
    public static String HOMET = "HOMET";
    public static String CID = "CID";
    public static String CNAME = "CNAME";
    public static String AID = "AID";
    public static String CTID = "CTID";
    public static String AGEID = "AGEID";
    public static String PID = "PID";
    public static String PID2 = "PID2";
    public static String PUMTID = "PUMTID";


    public static void resetPrefrences(Context context) {

        setUserLogin(context, false);
        setUserID(context, "");

    }

    public static void setUserLogin(Context context, boolean is) {
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = loginPreferences.edit();
        editor.putBoolean(USER_LOGIN, is);
        editor.commit();
    }

    public static boolean getUserLogin(Context context) {
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return loginPreferences.getBoolean(USER_LOGIN,false);

    }


    public static void setUserID(Context context, String is) {
        userIdPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = userIdPrefences.edit();
        editor.putString(USER_ID, is);
        editor.commit();
    }

    public static String getUserID(Context context) {
        userIdPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return userIdPrefences.getString(USER_ID,"");
    }


    public static void setMobile(Context context, String is) {
        mobilePrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mobilePrefences.edit();
        editor.putString(MOBILE_NO, is);
        editor.commit();
    }

    public static String getMobile(Context context) {
        mobilePrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return mobilePrefences.getString(MOBILE_NO,"");
    }


    public static void setEmilId(Context context, String is) {
        emailPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = emailPrefences.edit();
        editor.putString(EMAIL_ID, is);
        editor.commit();
    }

    public static String getEmilId(Context context) {
        emailPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return emailPrefences.getString(EMAIL_ID,"");
    }



    public static void setName(Context context, String is) {
        name = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = name.edit();
        editor.putString(FULLNAME, is);
        editor.commit();
    }

    public static String getName(Context context) {
        name = PreferenceManager.getDefaultSharedPreferences(context);
        return name.getString(FULLNAME,"");
    }

    public static void setPassword(Context context, String is) {
        PASSWORD = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = PASSWORD.edit();
        editor.putString(PASS, is);
        editor.commit();
    }

    public static String getPassword(Context context) {
        PASSWORD = PreferenceManager.getDefaultSharedPreferences(context);
        return PASSWORD.getString(PASS,"");
    }



    public static void setBuyCat(Context context, String is) {
        BUYCATPREF = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = BUYCATPREF.edit();
        editor.putString(BUYCAT, is);
        editor.commit();
    }

    public static String getBuyCat(Context context) {
        BUYCATPREF = PreferenceManager.getDefaultSharedPreferences(context);
        return BUYCATPREF.getString(BUYCAT,"");
    }



    public static void setHomeType(Context context, String is) {
        HOMETYPE = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = HOMETYPE.edit();
        editor.putString(HOMET, is);
        editor.commit();
    }

    public static String getHomeType(Context context) {
        HOMETYPE = PreferenceManager.getDefaultSharedPreferences(context);
        return HOMETYPE.getString(HOMET,"");
    }


    public static void setCityId(Context context, String is) {
        CITYTYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = CITYTYPEID.edit();
        editor.putString(CID, is);
        editor.commit();
    }

    public static String getCityId(Context context) {
        CITYTYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        return CITYTYPEID.getString(CID,"");
    }

    public static void setCityName(Context context, String is) {
        CITYTYNAME = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = CITYTYNAME.edit();
        editor.putString(CNAME, is);
        editor.commit();
    }

    public static String getCityName(Context context) {
        CITYTYNAME = PreferenceManager.getDefaultSharedPreferences(context);
        return CITYTYNAME.getString(CNAME,"");
    }


    public static void setAreaId(Context context, String is) {
        AREATYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = AREATYPEID.edit();
        editor.putString(AID, is);
        editor.commit();
    }

    public static String getAreaId(Context context) {
        AREATYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        return AREATYPEID.getString(AID,"");
    }


    public static void setCompId(Context context, String is) {
        COMPTYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = COMPTYPEID.edit();
        editor.putString(CTID, is);
        editor.commit();
    }

    public static String getCompId(Context context) {
        COMPTYPEID = PreferenceManager.getDefaultSharedPreferences(context);
        return COMPTYPEID.getString(CTID,"");
    }




    public static void setAgentId(Context context, String is) {
        AGENTID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = AGENTID.edit();
        editor.putString(AGEID, is);
        editor.commit();
    }

    public static String getAgentId(Context context) {
        AGENTID = PreferenceManager.getDefaultSharedPreferences(context);
        return AGENTID.getString(AGEID,"");
    }


    public static void setPostingId(Context context, String is) {
        POSTINGID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = POSTINGID.edit();
        editor.putString(PID, is);
        editor.commit();
    }

    public static String getPostingId(Context context) {
        POSTINGID = PreferenceManager.getDefaultSharedPreferences(context);
        return POSTINGID.getString(PID,"");
    }



    ///
    public static void setPostingId2(Context context, String is) {
        POSTINGID2 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = POSTINGID2.edit();
        editor.putString(PID2, is);
        editor.commit();
    }

    public static String getPostingId2(Context context) {
        POSTINGID2 = PreferenceManager.getDefaultSharedPreferences(context);
        return POSTINGID2.getString(PID2,"");
    }


    ///
    ///  payment
    public static void setPaymentId(Context context, String is) {
        PAYMENTID = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = PAYMENTID.edit();
        editor.putString(PUMTID, is);
        editor.commit();
    }

    public static String getPaymentId(Context context) {
        PAYMENTID = PreferenceManager.getDefaultSharedPreferences(context);
        return PAYMENTID.getString(PUMTID,"");
    }





}
