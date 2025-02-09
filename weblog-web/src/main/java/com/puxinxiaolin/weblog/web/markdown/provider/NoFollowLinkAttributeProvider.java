package com.puxinxiaolin.weblog.web.markdown.provider;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * @description: 支持超链接中动态添加 rel="nofollow" 属性, 让搜索引擎不要追踪此链接
 * @author: YCcLin
 * @date: 2025/2/9
 **/
public class NoFollowLinkAttributeProvider implements AttributeProvider {

    /**
     * 网站域名
     * TODO: 上线后需要更换为自己的域名
     */
    private final static String DOMAIN = "www.puxinxiaolin.com";

    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Link) {
            Link link = (Link) node;
            // 获取链接地址
            String href = link.getDestination();
            // 如果链接不是自己域名，则添加 rel="nofollow" 属性
            if (!href.contains(DOMAIN)) {
                attributes.put("rel", "nofollow");
            }
        }
    }

}
