package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

// 빈(bean)으로 등록된 컴포넌트는 템플릿에서 바로 사용할 수 있다.
// https://github.com/commonmark/commonmark-java
@Component
public class CommonUtil {
  public String markdown(String markdown) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(markdown);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(document);
  }
}
