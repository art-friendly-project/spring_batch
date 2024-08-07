package org.com.artfriendlybatch.domain.festival.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.artfriendlybatch.domain.common.BaseTimeEntity;

import java.time.LocalDate;

@Entity(name = "festival_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    @NotNull
    private int seq;

    @Column
    @NotBlank
    private String title;

    @Column(length = 1000)
    private String description;

    @Column
    private String organizer;

    @Column(name = "homepage_url", length = 500)
    private String homepageUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column
    private String place;

    @Column
    private String area;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "gpsx")
    private double gpsX;

    @Column(name = "gpsy")
    private double gpsY;

    @Column
    private String phone;

    @Column
    private String price;

    @Column(name = "place_addr")
    private String placeAddr;

    @Column(name = "progress_status", nullable = false)
    @NotNull
    private String progressStatus;

    @OneToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Builder
    public FestivalInfo(Long id, int seq, String title, String description, String organizer, String homepageUrl, LocalDate startDate, LocalDate endDate, String place, String area, String imageUrl, double gpsX, double gpsY, String phone, String price, String placeAddr, String progressStatus) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.description = description;
        this.organizer = organizer;
        this.homepageUrl = homepageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.area = area;
        this.imageUrl = imageUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.phone = phone;
        this.price = price;
        this.placeAddr = placeAddr;
        this.progressStatus = progressStatus;
    }

    public void updateProgressStatus() {
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
    }

    public void setFestival(Festival festival) { this.festival = festival; }
}
