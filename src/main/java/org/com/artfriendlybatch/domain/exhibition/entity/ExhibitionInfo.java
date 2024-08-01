package org.com.artfriendlybatch.domain.exhibition.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.artfriendlybatch.domain.common.BaseTimeEntity;
import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUrlUpdateDto;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "exhibition_info")
public class ExhibitionInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private int seq;

    @Column(unique = true)
    private String title;

    @Column(name = "detail_info_url", length = 500)
    private String detailInfoUrl;

    @Column(name = "start_date")
    @NotNull
    private LocalDate startDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDate endDate;

    @Column
    @NotBlank
    private String place;

    @Column(name = "realm_name")
    @NotBlank
    private String realmName;

    @Column
    @NotBlank
    private String area;

    @Column(name = "image_url", length = 1000)
    @NotBlank
    private String imageUrl;

    @Column(name = "gpsx")
    @NotNull
    private double gpsX;

    @Column(name = "gpsy")
    @NotNull
    private double gpsY;

    @Column(name = "ticketing_url", length = 1500)
    @NotNull
    private String ticketingUrl;

    @Column
    @NotBlank
    private String phone;

    @Column(nullable = false)
    @NotBlank
    private String price;

    @Column(name = "place_addr")
    @NotNull
    private String placeAddr;

    @Column(name = "progress_status", nullable = false)
    @NotNull
    private String progressStatus;

    @OneToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public ExhibitionInfo(Long id, int seq, String title, String detailInfoUrl, LocalDate startDate, LocalDate endDate, String place, String realmName, String area, String imageUrl, double gpsX, double gpsY, String ticketingUrl, String phone, String price, String placeAddr, String progressStatus) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.detailInfoUrl = detailInfoUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.realmName = realmName;
        this.area = area;
        this.imageUrl = imageUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.ticketingUrl = ticketingUrl;
        this.phone = phone;
        this.price = price;
        this.placeAddr = placeAddr;
        this.progressStatus = progressStatus;
    }

    public ExhibitionInfo updateForm(ExhibitionInfo updateExhibitionInfo) {
        this.title = updateExhibitionInfo.getTitle();
        this.startDate = updateExhibitionInfo.getStartDate();
        this.endDate = updateExhibitionInfo.getEndDate();
        this.place = updateExhibitionInfo.getPlace();
        this.realmName = updateExhibitionInfo.getRealmName();
        this.area = updateExhibitionInfo.getArea();
        this.imageUrl = updateExhibitionInfo.getImageUrl();
        this.gpsX = updateExhibitionInfo.getGpsX();
        this.gpsY = updateExhibitionInfo.getGpsY();
        this.ticketingUrl = updateExhibitionInfo.getTicketingUrl();
        this.phone = updateExhibitionInfo.getPhone();
        this.price = updateExhibitionInfo.getPrice();
        this.placeAddr = updateExhibitionInfo.getPlaceAddr();

        return this;
    }

    public ExhibitionInfo updateForm(ExhibitionUrlUpdateDto exhibitionUrlUpdateDto) {
        this.detailInfoUrl = exhibitionUrlUpdateDto.getUrl();

        return this;
    }

    public ExhibitionInfo updateProgressStatus() {
        LocalDate now = LocalDate.now();
        // 종료 날짜가 지난 경우
        if (now.isAfter(this.getEndDate())) {
            this.progressStatus = "ended";
        }
        // 현재 날짜가 시작 날짜와 같거나 이후이며, 종료 날짜 이전인 경우
        else if (!now.isBefore(this.getStartDate()) && now.isBefore(this.getEndDate())) {
            this.progressStatus = "inProgress";
        }
        // 시작 날짜가 아직 오지 않은 경우
        else if (now.isBefore(this.getStartDate())) {
            this.progressStatus = "scheduled";
        }

        return this;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }
}
