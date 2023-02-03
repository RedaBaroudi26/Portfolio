package com.smaaaak.Portfolio.share;

public class JWTUtil {

    public static final String SECRET = "mySecret123" ;
    public static final String AUT_HEADER = "Authorization" ;
    public static final String PREFIX = "Bearer "  ;
    public static final long EXPIRE_ACCESS_TOKEN =  600*60*1000 ;
    public static final long EXPIRE_REFRESH_TOKEN = 6000*60*1000 ;

}
