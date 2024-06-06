package org.com.artfriendlybatch.domain.exhibition.callable;

import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUpdateDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

public class ExhibitionDescriptionCallable implements Callable<ExhibitionUpdateDto> {
    private final ExhibitionUpdateDto exhibitionUpdateDto;

    public ExhibitionDescriptionCallable(ExhibitionUpdateDto exhibitionUpdateDto) {
        this.exhibitionUpdateDto = exhibitionUpdateDto;
    }

    @Override
    public ExhibitionUpdateDto call() throws Exception {
        String exhibitionSite = "https://www.mcst.go.kr/kor/s_culture/culture/";
        String detailUrl = exhibitionSite +exhibitionUpdateDto.getUrl();

        // Jsoup을 이용해 웹 페이지에서 데이터 추출
        Document doc = Jsoup.connect(detailUrl).get();

        // 클래스가 view_con인 div 요소 선택
        Element viewConDiv = doc.select("div.view_con").first();

        Elements paragraphs = viewConDiv.select("p:not(.portallink)");


        // 각 p 태그의 텍스트를 출력
        StringBuilder description = new StringBuilder();
        for (Element paragraph : paragraphs) {
            description.append(paragraph.text()).append("\n");
        }
        if(description.length() <= 2000)
            exhibitionUpdateDto.setDescription(description.toString());

        return exhibitionUpdateDto;
    }
}
