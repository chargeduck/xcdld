package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lesscoding.common.BaseEntity;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.vo.RedisUserCache;
import net.lesscoding.utils.IpUtils;
import net.lesscoding.utils.PasswordUtil;

/**
 * @author eleven
 * @date 2023/9/27 15:54
 * @apiNote
 */
@Data
@TableName("tb_account")
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    /**
     * 账号
     */
    private String account;

    /**
     * 网卡地址
     */
    private String mac;

    /**
     *邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码随机盐
     */
    private String salt;

    /**
     * 状态 0 小黑屋 1正常 2注销
     */
    private Integer status;

    /**
     * 昵称
     */
    private String nickname;

    private String region;

    private String ip;

    private String platform;


    public Account(RedisUserCache cache) {
        this.nickname = cache.getUsername();
        this.mac = cache.getUuid();
        this.ip = cache.getIp();
        this.platform = cache.getPlatform();
        this.region = cache.getExtraVo().getShortRegion();
        this.salt = PasswordUtil.salt();
    }

    public Account(AccountDto dto) {
        this.nickname = dto.getNickname();
        this.mac = dto.getMac();
        this.ip = IpUtils.getHostIp();
        this.platform = "WEB";
        this.region = dto.getRegion();
        this.salt = PasswordUtil.salt();
    }

}
