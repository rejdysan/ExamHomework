package com.example.examhomework.model;

import com.example.examhomework.model.dto.SellableListResponseDTO;
import com.example.examhomework.model.dto.SellableRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NamedNativeQueries({
    @NamedNativeQuery(name = "Sellable.findAll_ListDTO",
        query = "SELECT s.id as id," +
            "s.title as title," +
            "s.image_url as imageUrl," +
            "   (SELECT MAX(b.amount) " +
            "   FROM bid b " +
            "   WHERE b.sellable_id = s.id " +
            "   GROUP BY b.sellable_id) as lastBid " +
            "FROM sellable s " +
            "WHERE s.is_sellable = true",
        resultSetMapping = "Mapping.SellableListResponseDTO")
})
@SqlResultSetMapping(name = "Mapping.SellableListResponseDTO",
    classes = @ConstructorResult(targetClass = SellableListResponseDTO.class,
        columns = {@ColumnResult(name = "id"),
            @ColumnResult(name = "title"),
            @ColumnResult(name = "imageUrl"),
            @ColumnResult(name = "lastBid")})
)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sellable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Long startingPrice;
    private Long purchasePrice;
    private boolean isSellable = true;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "sellable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids;
    @ManyToOne
    private User buyer;

    public Sellable(SellableRequestDTO sellable, User user) {
        this.title = sellable.getTitle();
        this.description = sellable.getDescription();
        this.imageUrl = sellable.getImageUrl();
        this.startingPrice = sellable.getStartingPrice().longValue();
        this.purchasePrice = sellable.getPurchasePrice().longValue();
        this.user = user;
    }

    public void addBid(Bid bid) {
        getBids().add(bid);
        setBids(getBids());
    }
}