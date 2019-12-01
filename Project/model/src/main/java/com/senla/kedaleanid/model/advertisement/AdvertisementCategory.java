package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "AdvertisementCategory")
@Table(name = "advertisement_category", schema = "advertisements")
@NamedEntityGraph(name = "graph.AdvertisementCategory.advertisementList",
        attributeNodes = @NamedAttributeNode(value = "advertisementList", subgraph = "owner"),
        subgraphs = @NamedSubgraph(name = "owner", attributeNodes = @NamedAttributeNode(value = "owner"))
)
@Convertible
public class AdvertisementCategory implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @OneToMany(mappedBy = "adCategory", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Advertisement> advertisementList;


    public AdvertisementCategory() {
    }

    public AdvertisementCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Advertisement> getAdvertisementList() {
        if (advertisementList == null) {
            advertisementList = new ArrayList<>();
        }
        return advertisementList;
    }

    public void setAdvertisementList(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
    }

    @Override
    public String toString() {
        return "AdvertisementCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementCategory)) return false;
        AdvertisementCategory that = (AdvertisementCategory) o;
        return Objects.equals(getCategoryId(), that.getCategoryId()) &&
                getCategoryName().equals(that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId(), getCategoryName());
    }
}
