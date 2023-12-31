package net.lesscoding.common;

/**
 * @author eleven
 * @date  2022-11-10 19:55:27
 * @description 通用返回实体工厂类
 * Generated By: lesscoding.net basic service
 * Link to: <a href="https://lesscoding.net">https://lesscoding.net</a>
 * mail to:2496290990@qq.com zjh292411@gmail.com admin@lesscoding.net
 */
public class ResultFactory {
    /**
     * 通用成功返回
     * @param msg   成功信息
     * @return
     */
    public static Result success(String msg){
        return new Result(Consts.SUCCESS_CODE,msg,null);
    }

    /**
     *
     * @param msg       成功信息
     * @param data      要返回的数据信息
     * @return
     */
    public static Result success(String msg,Object data){
        return new Result(Consts.SUCCESS_CODE,msg,data);
    }
    /**
     * 通用成功返回
     * @param data  要返回的数据信息
     * @return
     */
    public static Result success(Object data){
        return new Result(Consts.SUCCESS_CODE,Consts.SUCCESS_MSG,data);
    }

    /**
     * 通用失败返回
     * @param msg       错误信息
     * @return
     */
    public static Result failed(String msg){
        return new Result(Consts.FAILED_CODE,msg,null);
    }

    /**
     * 通用异常返回   用于全局异常处理器
     * @param t 异常信息
     * @return
     */
    public static Result exception(Throwable t){
        return new Result(Consts.FAILED_CODE,t.getMessage(),null);
    }

    /**
     * 构建通用返回
     * @param code      返回状态码
     * @param msg       返回信息
     * @param data      返回数据
     * @return
     */
    public static Result build(Integer code,String msg,Object data){
        return new Result(code,msg,data);
    }

    /**
     * 根据自己写的sql语句 更新结果返回    更新，新增，删除，大于0的时候返回成功，否则返回失败
     * @param effectRow     受影响的行
     * @return
     */
    public static Result buildByResult(Integer effectRow){
        return effectRow != null && effectRow > 0 ? success("操作成功") : failed("操作失败");
    }

    /**
     * 根据调用IService或者BaseMapper自带接口返回的结果构建Result
     * @param flag     调用结果
     * @return
     */
    public static Result buildByResult(Boolean flag){
        return flag ? success("操作成功") : failed("操作失败");
    }

}
