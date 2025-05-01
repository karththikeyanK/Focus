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
        name = "apps"
)
public class App {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "status")  // RESTRICTED, ALLOWED
        private String status;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "app_id")
        private AppDetail appDetail;

}
