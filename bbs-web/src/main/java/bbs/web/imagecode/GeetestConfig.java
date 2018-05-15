package bbs.web.imagecode;

/**
 * GeetestWeb配置文件
 */
public class GeetestConfig {

    // geetest email 314977908@qq.com
    private static final String geetest_id = "44ea4705ad4cd750703a9c5011d91caf";
    private static final String geetest_key = "c32931bec97f5a9db971dc7e9b6c1d51";
    //	private static final String geetest_id = "002bc30ff1eef93e912f45814945e752";
    //	private static final String geetest_key = "4193a0e3247b82a26f563d595c447b1a";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }

}
