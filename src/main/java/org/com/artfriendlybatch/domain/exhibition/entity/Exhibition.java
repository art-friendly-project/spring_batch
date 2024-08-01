package org.com.artfriendlybatch.domain.exhibition.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Double temperature;

    @OneToOne(mappedBy = "exhibition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ExhibitionInfo exhibitionInfo;

    @Builder
    public Exhibition(Long id, Double temperature, ExhibitionInfo exhibitionInfo) {
        this.id = id;
        this.temperature = 0.0;
        this.exhibitionInfo = exhibitionInfo;
    }
}