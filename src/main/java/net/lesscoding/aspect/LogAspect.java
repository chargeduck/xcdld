package net.lesscoding.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.OperationLog;
import net.lesscoding.service.OperationLogService;
import net.lesscoding.utils.IpUtils;
import net.lesscoding.utils.ServletUtils;
import net.lesscoding.utils.UserAgentUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author eleven
 * @date 2022-11-10 19:55:27
 * @description 操作日志注解切面类
 * Generated By: lesscoding.net basic service
 * Link to: <a href="https://lesscoding.net">https://lesscoding.net</a>
 * mail to:2496290990@qq.com zjh292411@gmail.com admin@lesscoding.net
 */
@Aspect
@Component
@Slf4j
@RefreshScope
public class LogAspect {

    @Autowired
    private OperationLogService logService;

    // 配置织入点
    @Pointcut("@annotation(net.lesscoding.aspect.Log)")
    public void logPointCut() {
    }

    @NacosValue(value = "${server.port}", autoRefreshed = true)
    private String port;
    /**
     * 方法所用时长
     */
    private Long methodIntervalMs = 1L;
    /**
     * 当前登录的id
     */
    private String loginId = "1";

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        // 站长用户操作不添加日志
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            OperationLog operLog = new OperationLog();
            operLog.setStatus(0);
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setUserAgent(UserAgentUtil.userAgent());
            operLog.setOperIp(ip);
            // 返回参数
            operLog.setJsonResult(new Gson().toJson(jsonResult));
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (e != null) {
                operLog.setStatus(1);
                operLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            operLog.setOperName(loginId);
            operLog.setMethodIntervalMs(methodIntervalMs);
            operLog.setServerPort(port);
            ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 10,
                    60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(16));
            executor.submit(() -> logService.save(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 统计方法执行耗时Around环绕通知
     *
     * @param joinPoint 方法切入点
     * @return
     */
    @Around(value = "logPointCut()")
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 忽略的接口会报错
            loginId = StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            loginId = "1";
        }
        // 定义返回对象、得到方法需要的参数
        Object[] args = joinPoint.getArgs();
        TimeInterval timer = DateUtil.timer();
        Object obj = joinPoint.proceed(args);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        log.info("----------{}方法开始执行----------", methodName);
        // 打印耗时的信息
        methodIntervalMs = timer.intervalMs();
        log.info("----------{}执行耗时{}ms----------", methodName, methodIntervalMs);
        log.info("----------{}返回参数----------\n{}", methodName, new Gson().toJson(obj));
        return obj;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperationLog operLog) throws Exception {
        // 设置controller动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperationLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StrUtil.sub(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StrUtil.sub(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    //Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += new Gson().toJson(paramsArray[i]) + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}