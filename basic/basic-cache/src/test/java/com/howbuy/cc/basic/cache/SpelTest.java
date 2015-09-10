package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.model.User;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class SpelTest {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();

        User user = new User();
        user.setId(1111);
        map.put("a" , user);

        Expression keyExp = new SpelExpressionParser().parseExpression("#a.id");
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(map);
        Object result1 = keyExp.getValue(context);
        System.out.println(result1);
    }
}
