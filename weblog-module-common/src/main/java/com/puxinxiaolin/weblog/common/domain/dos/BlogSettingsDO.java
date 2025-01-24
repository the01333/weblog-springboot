package com.puxinxiaolin.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: t_blog_settings 实体类 -> BlogSettingsDO
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_blog_settings")
public class BlogSettingsDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "logo")
    private String logo;

    @TableField(value = "name")
    private String name;

    @TableField(value = "author")
    private String author;

    @TableField(value = "introduction")
    private String introduction;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "github_homepage")
    private String githubHomepage;

    @TableField(value = "csdn_homepage")
    private String csdnHomepage;

    @TableField(value = "gitee_homepage")
    private String giteeHomepage;

    @TableField(value = "zhihu_homepage")
    private String zhihuHomepage;

}
