package org.com.artfriendlybatch.domain.exhibition.dto.apiIntegrationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiRequestBuilder {
    private String realmCode;
    private String startDate;
    private String endDate;
    private int cPage;
    private int rows;
    private String sido;
    private String gugun;
    private String place;
    private String gpsxfrom;
    private String gpsyform;
    private String gpsxto;
    private String gpsyto;
    private String keyword;
    private int sortStdr;

    @Builder
    public ApiRequestBuilder(String realmCode, String startDate, String endDate, int cPage, int rows, String sido, String gugun, String place, Double gpsxfrom, Double gpsyform, Double gpsxto, Double gpsyto, String keyword, int sortStdr) {
        this.realmCode = realmCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cPage = cPage;
        this.rows = rows;
        this.sido = sido == null ? "" : sido;
        this.gugun = gugun == null ? "" : gugun;
        this.place = place == null ? "" : place;
        this.gpsxfrom = gpsxfrom == null ? "" : String.valueOf(gpsxfrom);
        this.gpsyform = gpsyform == null ? "" : String.valueOf(gpsyform);
        this.gpsxto = gpsxto == null ? "" : String.valueOf(gpsxto);
        this.gpsyto = gpsyto == null ? "" : String.valueOf(gpsyto);
        this.keyword = keyword == null ? "" : keyword;
        this.sortStdr = sortStdr;
    }
}
