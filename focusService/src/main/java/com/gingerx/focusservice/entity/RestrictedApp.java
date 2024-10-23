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
        name = "approvers"
)
public class RestrictedApp {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "app_name")
        private String appName;

        @Column(name = "app_id")
        private String appId;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

}
