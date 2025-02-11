package com.puxinxiaolin.weblog.web.markdown.renderer;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.ext.image.attributes.ImageAttributes;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @description: 定制化, 在添加图片描述时进行转换为 <span>图片描述</span>
 * @author: YCcLin
 * @date: 2025/2/9
 **/
public class ImageNodeRenderer implements NodeRenderer {

    private HtmlWriter htmlWriter;

    /**
     * 图片宽度
     */
    private final static String KEY_WIDTH = "width";

    /**
     * 图片高度
     */
    private final static String KEY_HEIGHT = "height";

    public ImageNodeRenderer(HtmlNodeRendererContext context) {
        this.htmlWriter = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        // 指定想要自定义渲染的节点，这里指定为图片 Image
        return Collections.singleton(Image.class);
    }

    @Override
    public void render(Node node) {
        Image image = (Image) node;
        htmlWriter.line();
        String imgUrl = image.getDestination();
        String imgTitle = image.getTitle();

        // 拼接 HTML 结构
        StringBuilder sb = new StringBuilder("<img src=\"");
        sb.append(imgUrl);
        sb.append("\"");

        // 添加 title="图片标题" alt="图片标题" 属性
        if (StringUtils.isNotBlank(imgTitle)) {
            sb.append(String.format(" title=\"%s\" alt=\"%s\"", imgTitle, imgTitle));
        }

        // 图片宽高
        Node lastChild = image.getLastChild();
        if (Objects.nonNull(lastChild) && lastChild instanceof ImageAttributes) {
            ImageAttributes imageAttributes = (ImageAttributes) lastChild;
            if (!CollectionUtils.isEmpty(imageAttributes.getAttributes())) {
                String width = imageAttributes.getAttributes().get(KEY_WIDTH);
                String height = imageAttributes.getAttributes().get(KEY_HEIGHT);
                sb.append(StringUtils.isBlank(width) ? "" : (" " + KEY_WIDTH + "=\"" + width + "\""));
                sb.append(StringUtils.isBlank(height) ? "" : (" " + KEY_HEIGHT + "=\"" + height + "\""));
            }
        }
        sb.append(">");

        if (StringUtils.isNotBlank(imgTitle)) {
            // 图文下方文字
            sb.append(String.format("<span class=\"image-caption\">%s</span>", imgTitle));
        }

        // 设置 HTML 内容
        htmlWriter.raw(sb.toString());
        htmlWriter.line();
    }

}
