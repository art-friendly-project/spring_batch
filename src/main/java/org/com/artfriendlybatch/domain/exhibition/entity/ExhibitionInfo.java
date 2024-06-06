package org.com.artfriendlybatch.domain.exhibition.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.artfriendlybatch.domain.common.BaseTimeEntity;
import org.com.artfriendlybatch.domain.exhibition.dto.ExhibitionUpdateDto;

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

    @Column(length = 2000)
    private String description;

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

    @Builder
    public ExhibitionInfo(Long id, int seq, String title, String description, LocalDate startDate, LocalDate endDate, String place, String realmName, String area, String imageUrl, double gpsX, double gpsY, String ticketingUrl, String phone, String price, String placeAddr, String progressStatus) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.description = description;
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

    public void updateForm(ExhibitionUpdateDto exhibitionUpdateDto) {
        this.description = exhibitionUpdateDto.getDescription();
    }
}
