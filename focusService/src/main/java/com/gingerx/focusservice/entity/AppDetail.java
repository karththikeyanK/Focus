package com.gingerx.focusservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "app_details"
)
public class AppDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "app_image_url")
    private String appImageUrl;
}
