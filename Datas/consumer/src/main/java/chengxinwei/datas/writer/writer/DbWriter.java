package chengxinwei.datas.writer.writer;

import chengxinwei.datas.writer.writer.common.DataWriter;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/1.
 */
@Service
public class DbWriter extends DataWriter{

    @Override
    public void write(List<Map> list) {
        System.out.println(list);
    }
}
