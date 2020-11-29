package cn.chiu.haveatry.UtilsTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class OAuth2UriTest {

    public static final String BASE_URI = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    public static final String CORPID = "ww81b13801f9314220";
    public static final String REDIRECT_URI = "https://hscrm.ac-diary.cn/hscrm/management/devlogin/index.html";
    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPE = "snsapi_base";
    public static final String STATE = "#wechat_redirect";

    public static void main(String[] args) throws UnsupportedEncodingException {

        String redirectUriEncode = URLEncoder.encode(REDIRECT_URI, "utf-8" );

        // OAuth2 url
        String OAuth2Url = BASE_URI
                + "appid=" + CORPID
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "utf-8" )
                + "&response_type="+ RESPONSE_TYPE
                + "&scope=" + SCOPE
                + "&state=" + STATE;

        System.out.println("OAuth2Url = " + OAuth2Url);

        String urlStrEncode = URLEncoder.encode(REDIRECT_URI, "utf-8" );
        String urlStrDecode = URLDecoder.decode(urlStrEncode, "utf-8");

        System.out.println("urlStrEncode = " + urlStrEncode);
        System.out.println("urlStrDecode = " + urlStrDecode);

    }

}