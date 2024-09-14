package com.dlshouwen.swda.bms.platform.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.email.config.EmailConfig;
import net.maku.email.param.EmailAliyunBatchSendParam;
import net.maku.email.param.EmailAliyunSendParam;
import net.maku.email.param.EmailLocalSendParam;
import net.maku.email.service.EmailService;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.operatelog.annotations.OperateLog;
import net.maku.framework.operatelog.enums.OperateTypeEnum;
import net.maku.system.convert.SysMailConfigConvert;
import net.maku.system.entity.SysMailConfigEntity;
import net.maku.system.enums.MailFormatEnum;
import net.maku.system.enums.MailPlatformEnum;
import net.maku.system.query.SysMailConfigQuery;
import net.maku.system.service.SysMailConfigService;
import net.maku.system.vo.SysMailConfigVO;
import net.maku.system.vo.SysMailSendVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮件配置
 *
 * @author 阿沐 babamu@126.com
 */
@RestController
@RequestMapping("sys/mail/config")
@Tag(name = "邮件配置")
@AllArgsConstructor
public class EmailPlatformController {
    private final IEmailPlatformService sysMailConfigService;
    private final EmailService emailService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<PageResult<EmailPlatformVO>> page(@ParameterObject @Valid SysMailConfigQuery query) {
        PageResult<EmailPlatformVO> page = sysMailConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<EmailPlatformVO>> list(Integer platform) {
        List<EmailPlatformVO> list = sysMailConfigService.list(platform);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<EmailPlatformVO> get(@PathVariable("id") Long id) {
        SysMailConfigEntity entity = sysMailConfigService.getById(id);

        return Result.ok(EmailPlatformConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> save(@RequestBody EmailPlatformVO vo) {
        sysMailConfigService.save(vo);

        return Result.ok();
    }

    @PostMapping("send")
    @Operation(summary = "发送邮件")
    @OperateLog(type = OperateTypeEnum.OTHER)
    public Result<String> send(@RequestBody SysMailSendVO vo) {
        SysMailConfigEntity entity = sysMailConfigService.getById(vo.getId());
        EmailPlatform config = EmailPlatformConvert.INSTANCE.convert2(entity);

        // 发送本地邮件
        if (vo.getPlatform() == MailPlatformEnum.LOCAL.getValue()) {
            LocalEmailSendParam local = new LocalEmailSendParam();
            local.setTos(vo.getMailTos());
            local.setSubject(vo.getSubject());
            local.setContent(vo.getContent());
            local.setHtml(StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.HTML.name()));
            boolean flag = emailService.sendLocal(local, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }


        // 发送阿里云模板邮件
        if (vo.getPlatform() == MailPlatformEnum.ALIYUN.getValue()
                && StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.TEMPLATE.name())) {
            AliyunEmailBatchSendParam aliyun = new AliyunEmailBatchSendParam();
            aliyun.setFrom(vo.getMailFrom());
            aliyun.setReceiversName(vo.getReceiversName());
            aliyun.setTagName(vo.getTagName());
            aliyun.setTemplateName(vo.getTemplateName());
            boolean flag = emailService.batchSendAliyun(aliyun, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }

        // 发送阿里云邮件
        if (vo.getPlatform() == MailPlatformEnum.ALIYUN.getValue()) {
            AliyunEmailSendParam aliyun = new AliyunEmailSendParam();
            aliyun.setFrom(vo.getMailFrom());
            aliyun.setFormAlias(vo.getFormAlias());
            aliyun.setTos(vo.getMailTos());
            aliyun.setSubject(vo.getSubject());
            aliyun.setContent(vo.getContent());
            aliyun.setHtml(StrUtil.equalsIgnoreCase(vo.getMailFormat(), MailFormatEnum.HTML.name()));
            boolean flag = emailService.sendAliyun(aliyun, config);

            return flag ? Result.ok() : Result.error("发送失败");
        }

        return Result.error("不支持的邮件平台或邮件格式");
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> update(@RequestBody @Valid EmailPlatformVO vo) {
        sysMailConfigService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:mail:config')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysMailConfigService.delete(idList);

        return Result.ok();
    }
}