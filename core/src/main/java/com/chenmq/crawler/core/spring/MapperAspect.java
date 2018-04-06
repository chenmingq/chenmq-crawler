package com.chenmq.crawler.core.spring;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author 创建人:< Chenmq>
 * @project 项目:<oms>
 * @date 创建时间:< 2017/12/23>
 * @comments: 说明:< //Aop监听sql执行时间 >
 */

@Aspect
@Component
public class MapperAspect {

    private static final Logger logger = LoggerFactory.getLogger(MapperAspect.class);

    /**
     * 当Sql执行时间超过该值时，则进行log warn级别题型，否则记录INFO日志。
     */
//    private long warnWhenOverTime = 2 * 60 * 1000L;

//    @Around("execution(* *..*Mapper.*(..))")
//    public Object logSqlExecutionTime(ProceedingJoinPoint joinPoint)
//            throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long costTime = System.currentTimeMillis() - startTime;
//        if (costTime > warnWhenOverTime) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("execute method :").append(joinPoint.getSignature());
//            sb.append("args: ").append(arrayToString(joinPoint.getArgs()));
//            sb.append(" cost time[").append(costTime).append("]ms");
//            logger.warn("{}",sb);
//        } else if (logger.isInfoEnabled()) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("execute method :").append(joinPoint.getSignature());
//            sb.append("args: ").append(arrayToString(joinPoint.getArgs()));
//            sb.append(" cost time[").append(costTime).append("]ms");
//            logger.info("{}",sb);
//        }
//        return result;
//    }
//
//    private static String arrayToString(Object[] a) {
//        if (a == null)
//            return "null";
//
//        int iMax = a.length - 1;
//        if (iMax == -1)
//            return "[]";
//
//        StringBuilder b = new StringBuilder();
//        b.append('[');
//        for (int i = 0;; i++) {
//            if (a[i] instanceof Object[]) {
//                b.append(arrayToString((Object[]) a[i]));
//            } else {
//                b.append(String.valueOf(a[i]));
//            }
//            if (i == iMax)
//                return b.append(']').toString();
//            b.append(", ");
//        }
//    }

    @AfterReturning("execution(* *..*SourcesCache.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        logger.info("Completed: " + joinPoint);
    }


    /**
     * 监控com.chenmq.crawler.cache.sources.*SourcesCache包及其子包的所有public方法
     */
    @Pointcut("execution(* *..*SourcesCache.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 声明环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.nanoTime();
        Object obj = pjp.proceed();
        long end = System.nanoTime();

        logger.info("redis执行结果: \n{}",obj!=null? JSON.toJSONString(obj):"");

        logger.info("调用RedisSourcesCache方法：↓ {}，参数：{}，执行耗时：{}纳秒，耗时：{}毫秒",
                pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()),
                (end - begin), (end - begin) / 1000000);
        return obj;
    }

}

