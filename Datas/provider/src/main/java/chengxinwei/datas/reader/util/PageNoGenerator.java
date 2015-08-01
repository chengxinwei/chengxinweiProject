package chengxinwei.datas.reader.util;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
public final class PageNoGenerator {

    public static String getPageNoKey(String tableName){
        return tableName + "pageNo";
    }
}
