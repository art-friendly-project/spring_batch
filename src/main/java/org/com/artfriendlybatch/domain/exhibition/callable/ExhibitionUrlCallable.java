package org.com.artfriendlybatch.domain.exhibition.callable;

import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUrlUpdateDto;
import org.com.artfriendlybatch.domain.exhibition.entity.ExhibitionInfo;
import org.com.artfriendlybatch.global.utils.HtmlEntityRemover;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ExhibitionUrlCallable implements Callable<ExhibitionUrlUpdateDto> {
    private final ExhibitionInfo exhibitionInfo;

    public ExhibitionUrlCallable(ExhibitionInfo exhibitionInfo) {
        this.exhibitionInfo = exhibitionInfo;
    }

    @Override
    public ExhibitionUrlUpdateDto call() throws Exception {
        return getExhibitionUpdateDto();
    }

    private ExhibitionUrlUpdateDto getExhibitionUpdateDto() throws IOException {
        // 검색어 설정
        String searchWord = HtmlEntityRemover.removeHtmlEntities(exhibitionInfo.getTitle());
        String url = "https://www.mcst.go.kr/kor/s_culture/culture/cultureList.jsp?pSeq=&pRo=&pCurrentPage=1&pType=&pPeriod=&fromDt=&toDt=&pArea=&pSearchType=01&pSearchWord=" + searchWord;
        String exhibitionSite = "https://www.mcst.go.kr/kor/s_culture/culture/";

        // Jsoup을 이용해 웹 페이지에서 데이터 추출
        Document doc = Jsoup.connect(url).get();
        Element link = doc.select("a.go").first();

        if (link != null) {
            String href = link.attr("href");
            return ExhibitionUrlUpdateDto.builder()
                    .exhibitionInfo(exhibitionInfo)
                    .url(exhibitionSite+href).build();
        }
        return null;
    }
}
