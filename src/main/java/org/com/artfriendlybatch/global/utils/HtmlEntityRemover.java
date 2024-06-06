package org.com.artfriendlybatch.global.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HtmlEntityRemover {
    public static String removeHtmlEntities(String input) {
        // HTML 엔티티에 해당하는 정규 표현식 패턴
        String htmlEntityPattern = "&#\\d+;";

        // 정규 표현식에 매칭되는 부분을 빈 문자열로 대체
        return input.replaceAll(htmlEntityPattern, "");
    }
}
