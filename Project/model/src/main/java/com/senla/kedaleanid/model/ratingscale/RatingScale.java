package com.senla.kedaleanid.model.ratingscale;

import com.senla.kedaleanid.model.IModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "RatingScale")
@Table(name = "rating_scale", schema = "advertisements")
public class RatingScale implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scale_id", updatable = false, nullable = false)
    private Integer scaleId;
    @Column(name = "scale_name", nullable = false)
    private String scaleName;
    @Column(name = "scale_threshold", nullable = false)
    private Integer scaleThreshold;

    public RatingScale() {
    }

    public RatingScale(int scaleId, String scaleName, int scaleTreshold) {
        this.scaleId = scaleId;
        this.scaleName = scaleName;
        this.scaleThreshold = scaleTreshold;
    }

    public Integer getScaleId() {
        return scaleId;
    }

    public void setScaleId(int scaleId) {
        this.scaleId = scaleId;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public Integer getScaleTreshold() {
        return scaleThreshold;
    }

    public void setScaleTreshold(int scaleThreshold) {
        this.scaleThreshold = scaleThreshold;
    }

    @Override
    public String toString() {
        return "RatingScale{" +
                "scaleId=" + scaleId +
                ", scaleName='" + scaleName + '\'' +
                ", scaleThreshold=" + scaleThreshold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingScale)) return false;
        RatingScale that = (RatingScale) o;
        return getScaleId().equals(that.getScaleId()) &&
                scaleThreshold.equals(that.scaleThreshold) &&
                getScaleName().equals(that.getScaleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScaleId(), getScaleName(), scaleThreshold);
    }
}
