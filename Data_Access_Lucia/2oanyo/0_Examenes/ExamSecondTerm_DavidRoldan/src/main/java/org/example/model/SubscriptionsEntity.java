package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subscriptions")
@NamedQuery(name = "getSubscriptionsByClient", query = "SELECT s FROM SubscriptionsEntity s where s.clientId = :nameClient  ")
@NamedQuery(name = "getSubscriptionsAndServicesByClient", query = "SELECT s FROM SubscriptionsEntity s LEFT JOIN FETCH s.subscriptionServicesById where s.clientId = :nameClient  ")
public class SubscriptionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "paid", nullable = false)
    private int paid;

    @OneToMany(mappedBy = "subscriptionsBySubscriptionId", cascade = CascadeType.REMOVE)
    private List<SubscriptionServicesEntity> subscriptionServicesById;

}
